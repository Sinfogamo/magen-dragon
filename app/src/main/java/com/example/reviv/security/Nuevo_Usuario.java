package com.example.reviv.security;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reviv.security.database.myDbAdapter;

public class Nuevo_Usuario extends AppCompatActivity {

    Spinner spinner;
    String idUsuario;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo__usuario);

        idUsuario=getIntent().getStringExtra("idUsuario");
        Log.d("Nuevo Usuario id",idUsuario);
        spinner=(Spinner)findViewById(R.id.spnRoles);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        helper=Admin.helper;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent admin=new Intent(Nuevo_Usuario.this,Admin.class);
        admin.putExtra("LoginId",idUsuario);
        Nuevo_Usuario.this.startActivity(admin);
    }

    public void newUser(View view)
    {
        AutoCompleteTextView txtnombre,txtaPaterno,txtaMaterno,
        txtMatricula,txtTelefono,txtDireccion,txtContra;
        Spinner spnRoles;
        String nombre,aPaterno,aMaterno,matricula,telefono,direccion,pass,rol;

        txtnombre=(AutoCompleteTextView)findViewById(R.id.txtNombreR);
        txtaPaterno=(AutoCompleteTextView)findViewById(R.id.txtAPaternoR);
        txtaMaterno=(AutoCompleteTextView)findViewById(R.id.txtAMaternoR);
        txtMatricula=(AutoCompleteTextView)findViewById(R.id.txtMatriculaR);
        txtTelefono=(AutoCompleteTextView)findViewById(R.id.txtTelefonoR);
        txtDireccion=(AutoCompleteTextView)findViewById(R.id.txtDireccionR);
        txtContra=(AutoCompleteTextView)findViewById(R.id.txtPassR);
        spnRoles=(Spinner)findViewById(R.id.spnRoles);


        nombre=txtnombre.getText().toString();
        aPaterno=txtaPaterno.getText().toString();
        aMaterno=txtaMaterno.getText().toString();
        matricula=txtMatricula.getText().toString();
        telefono=txtTelefono.getText().toString();
        direccion=txtDireccion.getText().toString();
        pass=txtContra.getText().toString();
        rol=spnRoles.getSelectedItem().toString();

        if(nombre.equals("")||aPaterno.equals("")||aMaterno.equals("")||matricula.equals("")||telefono.equals("")||direccion.equals("")||pass.equals("")||rol.equals(""))
        {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT);
            toast1.show();
        }
        else
        {
            String idRol="";
            if(rol.equals("Gerente"))
            {
                idRol="6";
            }
            else if(rol.equals("Vigilante"))
            {
                idRol="7";
            }
            Log.d("idROOOL",idRol);
            helper.insertUser(nombre,pass,nombre,aPaterno,aMaterno,matricula,telefono,direccion,
                    idRol,idUsuario);
            Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario Agregado", Toast.LENGTH_SHORT);
            toast1.show();

            Intent admin=new Intent(Nuevo_Usuario.this,Admin.class);
            admin.putExtra("LoginId",idUsuario);
            Nuevo_Usuario.this.startActivity(admin);
            finish();
        }



    }
}
