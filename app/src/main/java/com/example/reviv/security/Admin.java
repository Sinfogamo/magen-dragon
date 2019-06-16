package com.example.reviv.security;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.example.reviv.security.database.myDbAdapter;
import com.example.reviv.security.help.AdapterCategory;
import com.example.reviv.security.help.Category;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lista;
    String[] elementos;
    TextDrawable[] drawables;
    ColorGenerator generator;
    static myDbAdapter helper;
    FloatingActionButton fab;
    String idUsuario;
    String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.helper=new myDbAdapter(this);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                click();
            }
        });
        //Check
        this.helper.checkDataBase();
        idUsuario=getIntent().getStringExtra("LoginId");
        rol=getIntent().getStringExtra("userType");
        //Log.d("Admiiin",rol);



        //ListView
        String usuarios=this.helper.ObtenerDatosPersonales(idUsuario);
        //.d("Usuariooos",usuarios);
        if(usuarios.length()<=0)
        {
            lista=(ListView)findViewById(R.id.ListaUsuarios);
            makeNoUsersMessage(lista);
        }
        else
        {
            lista=(ListView)findViewById(R.id.ListaUsuarios);
            populateList(lista,usuarios);
        }



        //Drawer stuff
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void populateList(ListView li,String usuarios)
    {
        String usuariosArray[]=usuarios.split("\n");

        generator=ColorGenerator.MATERIAL;
        elementos=new String[usuariosArray.length];
        drawables=new TextDrawable[elementos.length];
        lista=(ListView)findViewById(R.id.ListaUsuarios);
        ArrayList<Category> category=new ArrayList();
        /*
        elementos[0]="Miguel Alejandro Pérez Castañeda";
        elementos[1]="Alma Gissele Galindo Bonilla";
        elementos[2]="Brenda Terry Miranda";
        elementos[3]="Cesar López Reyes";
        elementos[4]="Luis Ángel Mena Martinez";
        elementos[5]="Lucía Valencia López";
        elementos[6]="Alexis Sánchez Cervantez";
        elementos[7]="Jesús Pacheco Jerónimo";
        elementos[8]="Albert Romero Hernández";
        elementos[9]="Christina Bonilla Vázquez";
        */
        for(int i=0;i<elementos.length;i++)
        {
            String[] auxiliar=usuariosArray[i].split(",");
            Log.d("Auxiliar",auxiliar[5]);
            String role="";

            if(auxiliar[5].equals("6 "))
            {
                role="Gerente";
                Log.d("Role",role);
            }
            else if(auxiliar[5].equals("7 "))
            {
                role="Vigilante";
                Log.d("Role",role);
            }
            else
            {
                role="Nada";
            }


            drawables[i]=TextDrawable.builder().buildRound(auxiliar[1].substring(0,2).toUpperCase(),generator.getRandomColor());
            category.add(new Category(String.valueOf(auxiliar[0]),auxiliar[1]+" "+auxiliar[2]+" "+auxiliar[3],role,drawables[i]));
        }
        final AdapterCategory adaptador=new AdapterCategory(this,category);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info=new Intent(getBaseContext(),Informacion.class);
                startActivity(info);
            }
        });
    }



    public void makeNoUsersMessage(ListView li)
    {
        ArrayList<String> elementos=new ArrayList<String>();
        ArrayAdapter<String> adapter;

        elementos.add("Aún no hay usuarios");
        adapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,elementos);

        li.setAdapter(adapter);
    }

    public void click()
    {
        Intent agregar=new Intent(Admin.this,Nuevo_Usuario.class);
        agregar.putExtra("idUsuario",idUsuario);
        Admin.this.startActivity(agregar);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public myDbAdapter getHelper()
    {
        return this.helper;
    }
}
