package com.deepak.letsservicetask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Deepak on 27-Feb-18.
 */

public class LocationDB {
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    public LocationDB(Context context){
        myHelper = new MyHelper(context, "pgdetails.db", null, 1);
    }

    public void open(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    public void insertLocation(String latitude, String longitude, String speed, String time){
        sqLiteDatabase = myHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("speed", speed);
        contentValues.put("time", time);
        sqLiteDatabase.insert("locationdb", null,contentValues);
    }

    public Cursor queryLocationDetails(){
        Cursor cursor = sqLiteDatabase.query("locationdb", null, null, null, null, null, null);

        //ArrayList<LocationDetails> arrayList = new ArrayList<>();

        return cursor;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public class MyHelper extends SQLiteOpenHelper {

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table locationdb(_id integer primary key, latitude text, longitude text, speed text, time text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
