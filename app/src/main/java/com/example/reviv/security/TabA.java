package com.example.reviv.security;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reviv.security.database.myDbAdapter;


public class TabA extends Fragment {

    Spinner spnTipo;
    myDbAdapter helper;
    AutoCompleteTextView txtNombre;
    AutoCompleteTextView txtaPaterno;
    AutoCompleteTextView txtaMaterno;
    AutoCompleteTextView txtMotivo;
    AutoCompleteTextView txtPlaca;

    ImageView vehiculo;
    ImageView placa;
    ImageView identif;
    Button btnNuevaVisita;
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
        Log.d("pruebaaausuario",idUsuario);
        return inflater.inflate(R.layout.fragment_tab, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {


        helper=new myDbAdapter(getContext());

        placa=(ImageView)getView().findViewById(R.id.imgPlaca);
        vehiculo=(ImageView)getView().findViewById(R.id.imgVehiculo);
        identif=(ImageView)getView().findViewById(R.id.imgIdent);
        btnNuevaVisita=(Button)getView().findViewById(R.id.btnNV);
        txtNombre=(AutoCompleteTextView)getView().findViewById(R.id.txtNombreV);
        txtaPaterno=(AutoCompleteTextView)getView().findViewById(R.id.txtAPaternoV);
        txtaMaterno=(AutoCompleteTextView)getView().findViewById(R.id.txtAMaternoV);
        txtMotivo=(AutoCompleteTextView)getView().findViewById(R.id.txtMotivoV);
        txtPlaca=(AutoCompleteTextView)getView().findViewById(R.id.txtPlacaV);

        spnTipo=(Spinner)getView().findViewById(R.id.rgvRoles);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.tipos,android.R.layout.simple_spinner_item);
        spnTipo.setAdapter(adapter);

        placa.setClickable(true);
        vehiculo.setClickable(true);
        identif.setClickable(true);
        btnNuevaVisita.setClickable(true);

        btnNuevaVisita.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String nombre,aPaterno,aMaterno,motivo,placaI,tipo;

                nombre=txtNombre.getText().toString();
                aPaterno=txtaPaterno.getText().toString();
                aMaterno=txtaMaterno.getText().toString();
                motivo=txtMotivo.getText().toString();
                placaI=txtPlaca.getText().toString();
                tipo=spnTipo.getSelectedItem().toString();


                Toast.makeText(getContext(),"holi",Toast.LENGTH_LONG).show();
                helper.insertVisitor(idUsuario,nombre,aPaterno,aMaterno,motivo,placaI,tipo,
                        );
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

    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==CAMERA_PIC_REQUEST)
        {
            Bitmap image=(Bitmap)data.getExtras().get("data");
            ImageView imageView=(ImageView)getView().findViewById(R.id.imgVehiculo);
            imageView.setImageBitmap(image);
        }
        else if(requestCode==CAMERA_PIC_REQUEST_I)
        {
            Bitmap image=(Bitmap)data.getExtras().get("data");
            ImageView imageView=(ImageView)getView().findViewById(R.id.imgIdent);
            imageView.setImageBitmap(image);
        }
        else if(requestCode==CAMERA_PIC_REQUEST_P)
        {
            Bitmap image=(Bitmap)data.getExtras().get("data");
            ImageView imageView=(ImageView)getView().findViewById(R.id.imgPlaca);
            imageView.setImageBitmap(image);
        }
    }


}
