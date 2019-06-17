package com.example.reviv.security;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reviv.security.database.myDbAdapter;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.net.URL;

//import retrofit2.http.Url;


public class TabA extends Fragment {

    private class createFolder extends AsyncTask<String,Void,String>
    {
        private String fileName="";

        protected String doInBackground(String... params){
            String response="nada";


            try{
                URL url = new URL("http://192.168.1.66/Security/createFolder.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String data="lastId="+params[0]+"&idU="+idUsuario;

                String line;
                StringBuilder sb= new StringBuilder();
                conn.getOutputStream().write(data.getBytes("UTF-8"));

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                    response =sb.toString();
                }
                br.close();
            }catch (IOException ie){
                ie.printStackTrace();
            }

            //Log.d("Respuesta del server",response);


            this.fileName=response.toString();
            return response.toString();
        }

        public String getFile(){return this.fileName;}
    }



    private class sendDatos extends AsyncTask<String,Void,String>
    {
        protected String doInBackground(String... params) {
            String fileName = params[0];
            String idU=params[2];
            String nombre="";

            switch (params[1])
            {
                case "vehiculo":
                    nombre="vehi";
                    break;
                case "placa":
                    nombre="plac";
                    break;
                case "ident":
                    nombre="ident";
                    break;

            }
            try{
                String uploadId= UUID.randomUUID().toString();

                new MultipartUploadRequest(getContext(),uploadId,"http://192.168.1.66/Security/upload.php")
                        .addFileToUpload(fileName,"image")
                        .addParameter("name",nombre)
                        .addParameter("idUsuario",idU)
                        .addParameter("folder",params[3])
                        .setNotificationConfig(new UploadNotificationConfig()
                                .setCompletedMessage("Imagen subida exitosamente")
                                .setTitle("Subida de imagen")
                                .setErrorMessage("Error al subir imagen")
                        )
                        .setMaxRetries(2)
                        .startUpload();

            }catch (Exception ex){
                //Log.d("Error al subir",ex.getMessage());
            }

            return null;
        }
    }



    Spinner spnTipo;
    myDbAdapter helper;
    AutoCompleteTextView txtNombre;
    AutoCompleteTextView txtaPaterno;
    AutoCompleteTextView txtaMaterno;
    AutoCompleteTextView txtMotivo;
    AutoCompleteTextView txtPlaca;
    AutoCompleteTextView txtNombreResidente;


    LinearLayout fields;
    LinearLayout parent;
    ScrollView sc;

    Uri uriPlaca;
    Uri uriVehiculo;
    Uri uriIdent;
    String vehiculoPath;
    String placaPath;
    String identPath;

    ImageView vehiculo;
    ImageView placa;
    ImageView identif;
    Button btnNuevaVisita;
    View ido;
    View ido2;
    View ido3;
    View ido4;
    View ido5;
    String idUsuario;
    final int CAMERA_PIC_REQUEST=1337;
    final int CAMERA_PIC_REQUEST_P=1336;
    final int CAMERA_PIC_REQUEST_I=1335;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        idUsuario=getArguments().getString("idUsuario");
        //Log.d("pruebaaausuario",idUsuario);


        return inflater.inflate(R.layout.fragment_tab, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {

        int parentHeight=1;

        sc=(ScrollView)getView().findViewById(R.id.ScrollV);

        parent=(LinearLayout)getView().findViewById(R.id.LLparent);

        fields=(LinearLayout)getView().findViewById(R.id.LLfields);

       parentHeight=getTotalHeight(fields);

        helper=new myDbAdapter(getContext());

        placa=(ImageView)getView().findViewById(R.id.imgPlaca);
        vehiculo=(ImageView)getView().findViewById(R.id.imgVehiculo);
        identif=(ImageView)getView().findViewById(R.id.imgIdent);

        vehiculo.setEnabled(false);
        vehiculo.setAlpha(0.5f);
        identif.setEnabled(false);
        identif.setAlpha(0.5f);


        btnNuevaVisita=(Button)getView().findViewById(R.id.btnNV);
        txtNombre=(AutoCompleteTextView)getView().findViewById(R.id.txtNombreV);
        txtaPaterno=(AutoCompleteTextView)getView().findViewById(R.id.txtAPaternoV);
        txtaMaterno=(AutoCompleteTextView)getView().findViewById(R.id.txtAMaternoV);
        txtMotivo=(AutoCompleteTextView)getView().findViewById(R.id.txtMotivoV);
        txtPlaca=(AutoCompleteTextView)getView().findViewById(R.id.txtPlacaV);
        txtNombreResidente=(AutoCompleteTextView)getView().findViewById(R.id.atvNombreV);
        ido=getView().findViewById(R.id.ido);
        ido2=getView().findViewById(R.id.ido2);
        ido3=getView().findViewById(R.id.ido3);
        ido4=getView().findViewById(R.id.ido4);
        ido5=getView().findViewById(R.id.ido5);


        spnTipo=(Spinner)getView().findViewById(R.id.rgvRoles);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.tipos,android.R.layout.simple_spinner_item);
        spnTipo.setAdapter(adapter);


        String[] lista=new String[10];
        lista[0]="Miguel Alejandro Pérez Castañeda";
        lista[1]="Alma Gissele Galindo Bonilla";
        lista[2]="Brenda Terry Miranda";
        lista[3]="Cesar López Reyes";
        lista[4]="Luis Ángel Mena Martinez";
        lista[5]="Lucía Valencia López";
        lista[6]="Alexis Sánchez Cervantez";
        lista[7]="Jesús Pacheco Jerónimo";
        lista[8]="Albert Romero Hernández";
        lista[9]="Christina Bonilla Vázquez";

        ArrayAdapter<String> nombreResAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,lista);

        txtNombreResidente.setAdapter(nombreResAdapter);

        placa.setClickable(true);
        vehiculo.setClickable(true);
        identif.setClickable(true);
        btnNuevaVisita.setClickable(true);

        btnNuevaVisita.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                LinearLayout layout=getView().findViewById(R.id.LLfields);
                Context context=getContext();



                createFolder crea=new createFolder();
                String nombre,aPaterno,aMaterno,motivo,placaI,tipo,folderName;
                long lastid=0;

                nombre=txtNombre.getText().toString();
                aPaterno=txtaPaterno.getText().toString();
                aMaterno=txtaMaterno.getText().toString();
                motivo=txtMotivo.getText().toString();
                placaI=txtPlaca.getText().toString();
                tipo=spnTipo.getSelectedItem().toString();

                setSecondInputs(view);










                //lastid=helper.insertVisitor(idUsuario,nombre,aPaterno,aMaterno,motivo,placaI,tipo);

                //crea.execute(String.valueOf(lastid));
                //folderName=crea.getFile();




                //Toast.makeText(getContext(),folderName,Toast.LENGTH_LONG).show();

                //new sendDatos().execute(vehiculoPath,"vehiculo",idUsuario,folderName);
                //new sendDatos().execute(placaPath,"placa",idUsuario,folderName);
                //new sendDatos().execute(identPath,"ident",idUsuario,folderName);



                //Toast.makeText(getContext(),"holi",Toast.LENGTH_LONG).show();

            }
        });

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,CAMERA_PIC_REQUEST_P);
            }
        });
        vehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"holi",Toast.LENGTH_LONG).show();
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,CAMERA_PIC_REQUEST);
            }
        });
        identif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"holi",Toast.LENGTH_LONG).show();
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,CAMERA_PIC_REQUEST_I);
            }
        });


    }

    public void setSecondInputs(View view)
    {


        ido.setVisibility(view.VISIBLE);
        ido.setAlpha(0f);
        ido2.setVisibility(view.VISIBLE);
        ido2.setAlpha(0f);
        ido3.setVisibility(view.VISIBLE);
        ido3.setAlpha(0f);
        ido4.setVisibility(view.VISIBLE);
        ido4.setAlpha(0f);
        ido5.setVisibility(view.VISIBLE);
        ido5.setAlpha(0f);

        final ArrayList<View> arreglo=new ArrayList<>();
        final ArrayList<ObjectAnimator> oArray=new ArrayList<>();

        arreglo.add(ido);
        arreglo.add(ido2);
        arreglo.add(ido3);
        arreglo.add(ido4);
        arreglo.add(ido5);



        new CountDownTimer(150, 1) {
            public void onTick(long millisUntilFinished) {
                //sc.smoothScrollTo(0,sc.getBottom());
                ObjectAnimator translate=ObjectAnimator.ofInt(sc,"ScrollY",sc.getBottom());
                //ObjectAnimator fade=ObjectAnimator.ofFloat(ido,"alpha",0,1f);
                //translate.setDuration(2000);

                for(int i=0;i<arreglo.size();i++)
                {
                    oArray.add(ObjectAnimator.ofFloat(arreglo.get(i),"alpha",0f,1f));
                    oArray.get(i).setDuration(2000);
                }

                AnimatorSet animator=new AnimatorSet();
                animator.setDuration(250L);
                animator.playTogether(translate,oArray.get(0),oArray.get(1),oArray.get(2),oArray.get(3),oArray.get(4));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();

            }
            public void onFinish() {
            }
        }.start();

    }

    public int getTotalHeight(LinearLayout linearLayout)
    {
        int count=linearLayout.getChildCount();
        int heightPixels=0;
        View v=null;

        for(int i=0;i<count;i++)
        {
            v=linearLayout.getChildAt(i);
            heightPixels+=v.getHeight();
        }

        return heightPixels;
    }

    public void createNewInputs()
    {
        Toast.makeText(getContext(),"chi",Toast.LENGTH_LONG).show();
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }




    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==CAMERA_PIC_REQUEST)
        {
            identif.setAlpha(1.0f);

            Bitmap image=(Bitmap)data.getExtras().get("data");
            uriVehiculo=data.getData();
            vehiculoPath=getPath(uriVehiculo);

            ImageView imageView=(ImageView)getView().findViewById(R.id.imgVehiculo);
            imageView.setImageBitmap(image);
            identif.setEnabled(true);

        }
        else if(requestCode==CAMERA_PIC_REQUEST_I)
        {
            Bitmap image=(Bitmap)data.getExtras().get("data");
            uriIdent=data.getData();
            identPath=getPath(uriIdent);

            ImageView imageView=(ImageView)getView().findViewById(R.id.imgIdent);
            imageView.setImageBitmap(image);
        }
        else if(requestCode==CAMERA_PIC_REQUEST_P)
        {
            vehiculo.setAlpha(1.0f);

            Bitmap image=(Bitmap)data.getExtras().get("data");
            uriPlaca=data.getData();
            placaPath=getPath(uriPlaca);

            ImageView imageView=(ImageView)getView().findViewById(R.id.imgPlaca);
            imageView.setImageBitmap(image);
            vehiculo.setEnabled(true);

        }
    }


}
