package com.example.shatur.livefree.userDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shatur on 15/2/17.
 */

public class createDB extends SQLiteOpenHelper {

    private static String LOGTAG = "DATABASE";
    public static final String DB_NAME = "LIVE_FREE";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE = "CREATE TABLE "+ DBConstants.DB_TABLE_NAME +" ("
            +DBConstants.USER_NAME+ " TEXT,"+DBConstants.USER_DEVICE_ID+" TEXT,"+DBConstants.USER_EMAIL+ " TEXT)";
    public static final String DELETE_TABLE = "DROP TABLE"+ DBConstants.DB_TABLE_NAME ;


    public createDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOGTAG, "DATABASE CREATED");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d(LOGTAG, "TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insertEntries(ContentValues contentValues, SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.insert(DBConstants.DB_TABLE_NAME, null, contentValues);
    }

    public Cursor readEntries(SQLiteDatabase sqLiteDatabase){
        return sqLiteDatabase.query(
                DBConstants.DB_TABLE_NAME,  // The table to query
                null,                       // The coloumns to read
                null,                       // The coloumns WHERE clause
                null,                       // The values of WHERE clause
                null,                       // Groping
                null,                       // Filter on group
                null                        // Sort Order
        );
    }
}
