package com.example.reviv.security;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reviv.security.database.myDbAdapter;

public class MainActivity extends Activity {
    Button btnIngresar;
    AutoCompleteTextView txtUsuario;
    EditText txtpass;
    myDbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.adapter=new myDbAdapter(this);

        txtUsuario=(AutoCompleteTextView)findViewById(R.id.txtUsuario);
        txtpass=(EditText)findViewById(R.id.txtPass);

        btnIngresar=(Button)findViewById(R.id.BtnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Iniciar();
            }
        });
    }

    public void Iniciar()
    {
        String usu=txtUsuario.getText().toString();
        String pa=txtpass.getText().toString();
        String check=adapter.checkSession(usu,pa);
        Log.d("datooos",check);
        //Log.d("application chida",String.valueOf(check.length()));
        if(!(usu.equals("")||pa.equals("")))
        {
            String[] datos=check.split(",");
            if(check.length()>0)
            {
                if(datos[1].equals("1")||datos[1].equals("2"))
                {
                    Intent intent=new Intent(MainActivity.this,Admin.class);
                    intent.putExtra("LoginId",datos[0]);
                    intent.putExtra("userType",datos[1]);
                    MainActivity.this.startActivity(intent);
                    txtUsuario.setText("");
                    txtpass.setText("");
                    //finish();
                }
                else if(datos[1].equals("3"))
                {
                    Intent intent=new Intent(MainActivity.this,Monitoreo.class);
                    intent.putExtra("LoginId",datos[0]);
                    intent.putExtra("userType",datos[1]);
                    MainActivity.this.startActivity(intent);
                    txtUsuario.setText("");
                    txtpass.setText("");
                    //finish();
                }

            }
            else
            {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT);

                toast1.show();
            }
        }
        else
        {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Rellena los campos", Toast.LENGTH_SHORT);
            toast1.show();
        }


    }
}
