package com.example.reviv.security.database;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by reviv on 17/04/2018.
 */

public class myDbHelper extends SQLiteAssetHelper {
    static final String DATABASE_NAME="dbSec.db";
    private Context context;
    private SQLiteDatabase myDataBase;
    static final int DATABASE_VERSION=1;
    static final String DB_PATH="/data/data/com.example.reviv.attornery/databases";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS Usuarios";

    public myDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
        this.context=context;
    }

    public void createDataBase() throws IOException
    {
        if(!checkDataBase())
        {
            getReadableDatabase();
            try{
                copyDataBase();
            }catch (IOException e)
            {
                //throw new Error("Error copying database");
                Log.d("--MYAPP--","Cayó pedo en la matrix, cayó pedo");
            }
        }
    }
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB=null;
        try{
            checkDB=SQLiteDatabase.openDatabase("/data/data/com.example.android.materialdesigncodelab/databases/DbAtt",null,1);
        }catch (SQLiteException e)
        {

        }
        if(checkDB!=null)
        {
            checkDB.close();
        }
        if(checkDB!=null)
        {
            return true;
        }
        return  false;
    }
private void copyDataBase() throws IOException {
    InputStream myInput = this.context.getAssets().open(DATABASE_NAME);
    OutputStream myOutput = new FileOutputStream("/data/data/com.example.android.materialdesigncodelab/databases/DbAtt");
    byte[] buffer = new byte[1024];
    while (true) {
        int length = myInput.read(buffer);
        if (length > 0) {
            myOutput.write(buffer, 0, length);
        } else {
            myOutput.flush();
            myOutput.close();
            myInput.close();
            return;
        }
    }
}
public void openDataBase() throws SQLException
{
    this.myDataBase=SQLiteDatabase.openDatabase("/data/data/com.example.android.materialdesigncodelab/databases/DbAtt",null,1);
}
public synchronized void close()
{
    if(this.myDataBase!=null)
    {
        this.myDataBase.close();
    }
    super.close();
}

}
