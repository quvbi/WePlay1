package com.example.weplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String BD_ARCHIVO = "MiBaseDeDatos.db";
    private static final String TABLA = "Gatitos";
    private static final String ENTRADA_ID = "id";
    private static final String ENTRADA_NOMBRE = "nombre";
    private static final String ENTRADA_EDAD = "edad";

    public DBHelper(Context context) {
        super(context, BD_ARCHIVO, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLA + "(" +
                ENTRADA_ID + " INTEGER PRIMARY KEY, " +
                ENTRADA_NOMBRE + " TEXT, " +
                ENTRADA_EDAD + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS ?";
        String[] paramtrs = {TABLA};
        sqLiteDatabase.execSQL(query, paramtrs);
        onCreate(sqLiteDatabase);

    }

    public void save(String nombre, int edad){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ENTRADA_NOMBRE, nombre);
        valores.put(ENTRADA_EDAD, edad);
        db.insert(TABLA,null,valores);
    }

    public String[] find(String ident) {

        SQLiteDatabase db = getReadableDatabase();
        String clause = ENTRADA_ID + " = ?";
        String[] args = {ident};
        Cursor c = db.query(TABLA, null, clause, args,null,null, null);
        String[] result = new String[2];
        if(c.moveToFirst()){
            result[0] = c.getString(1);
            result[1] = c.getString(2);
        }
        return result;
    }

    public String[] pos(int e){
        SQLiteDatabase db = getReadableDatabase();
        String clause = ENTRADA_ID + " = ?";
        Cursor c = db.query(TABLA, null, clause, null,null,null, null);
        int i = -1;
        String[] s = new String[3];
        if(c.moveToNext()){
            i++;
            if(i==e){
                s[0] = c.getString(0);
                s[1] = c.getString(1);
                s[2] = c.getString(2);
            }
        }
        return s;
    }

    public String older(){
        String query = "SELECT ENTRADA_ID, ENTRADA_NOMBRE, ENTRADA_EDAD FROM TABLA WHERE ENTRADA_EDAD = (SELECT MAX(";
        return query;
    }



}
