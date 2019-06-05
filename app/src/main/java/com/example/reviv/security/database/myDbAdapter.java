package com.example.reviv.security.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by reviv on 17/04/2018.
 */

public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context)
    {
        this.myhelper=new myDbHelper(context);
    }

    public void checkDataBase()
    {
        String QUERY="SELECT * FROM usuarios usr";
        Cursor cursor=this.myhelper.getWritableDatabase().rawQuery(QUERY,new String[]{});
        cursor.close();
    }

    public String ObtenerDatosPersonales(String idUsuario)
    {
        String Query="SELECT us.idUsuario,us.nombreUsuario,us.idRol," +
                "dp.nombre,dp.aPaterno,dp.aMaterno FROM usuarios as us INNER JOIN datosPersonales as dp ON us.idUsuario=dp.idUsuario where idUI=?";
        Cursor cursor=this.myhelper.getWritableDatabase().rawQuery(Query,new String[]{idUsuario});
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {
            int cid=cursor.getInt(cursor.getColumnIndex("idUsuario"));
            int idRol=cursor.getInt(cursor.getColumnIndex("idRol"));
            String nombreUsuario=cursor.getString(cursor.getColumnIndex("nombreUsuario"));
            String nombre=cursor.getString(cursor.getColumnIndex("nombre"));
            String aPaterno=cursor.getString(cursor.getColumnIndex("aPaterno"));
            String aMaterno=cursor.getString(cursor.getColumnIndex("aMaterno"));
            buffer.append(cid+","+nombre+","+aPaterno+","+aMaterno+","+nombreUsuario+","+idRol+" "+"\n");
        }
        Log.d("BUFFER",buffer.toString());
        cursor.close();
        return buffer.toString();
    }

    public String checkSession(String nombreUsuario,String pass)
    {
        String query="SELECT us.idUsuario,us.idRol,ro.numRol from usuarios as us Inner join roles as ro on us.idRol=ro.idRol where nombreUsuario=? and pass=?";
        Cursor cursor=this.myhelper.getWritableDatabase().rawQuery(query,new String[]{nombreUsuario,pass});
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {
            int cid=cursor.getInt(cursor.getColumnIndex("idUsuario"));
            String rolNum=cursor.getString(cursor.getColumnIndex("numRol"));
            buffer.append(cid+","+rolNum);
        }
        cursor.close();
        return buffer.toString();
    }
    public void deleteUser(String idU)
    {
        SQLiteDatabase db=this.myhelper.getWritableDatabase();
        try{
            db.delete("datosPersonales","idUsuario=?",new String[]{idU});
            db.delete("usuarios","idUsuario=?",new String[]{idU});
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public long insertVisitor(String idUsuario,String nombre,String aPaterno,String aMaterno,String motivo,
                              String placa,String tipoV)
    {
        SQLiteDatabase db=this.myhelper.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("nombre",nombre);
        values.put("aPaterno",aPaterno);
        values.put("aMaterno",aMaterno);
        values.put("motivoVisita",motivo);
        values.put("placa",placa);
        values.put("tipoVisita",tipoV);
        values.put("idUsuario",idUsuario);

        try{
            return db.insert("datosIngresos",null,values);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;

    }

    public void insertUser(String nombreUsuario,String pass,String nombre,String aPaterno,
                           String aMaterno,String matricula,String telefono,String direccion,String idRol,String idUI)
    {
        SQLiteDatabase db=this.myhelper.getWritableDatabase();

        ContentValues values=new ContentValues();
        ContentValues values1=new ContentValues();

        values.put("nombreUsuario",nombre);
        values.put("idRol",idRol);
        values.put("pass",pass);
        values.put("idUI",idUI);


        try{
            Long lId=db.insert("usuarios",null,values);

            Log.d("Idee",String.valueOf(lId));

            values1.put("nombre",nombre);
            values1.put("aPaterno",aPaterno);
            values1.put("aMaterno",aMaterno);
            values1.put("matricula",matricula);
            values1.put("telefono",telefono);
            values1.put("direccion",direccion);
            values1.put("idRol",idRol);
            values1.put("aPaterno",aPaterno);
            values1.put("aMaterno",aMaterno);
            values1.put("matricula",matricula);
            values1.put("telefono",telefono);
            values1.put("direccion",direccion);
            values1.put("idRol",idRol);
            values1.put("idUsuario",lId);

            db.insert("datosPersonales",null,values1);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
