package com.example.edu_s.infocarmadrid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fgonzalez on 14/11/2018.
 */

public class cochesDBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "infoCarMadrid.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TODOS =
            "CREATE TABLE " + DBContract.cocheItem.TABLE_NAME + " (" +
                    DBContract.cocheItem._ID + " INTEGER PRIMARY KEY," +
                    DBContract.cocheItem.COLUMN_NAME_MATRICULA + TEXT_TYPE + COMMA_SEP +
                    DBContract.cocheItem.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.cocheItem.COLUMN_NAME_DISTINTIVO + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_COCHES =
            "DROP TABLE IF EXISTS " + DBContract.cocheItem.TABLE_NAME;

    public cochesDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_COCHES);
        onCreate(db);
    }

}
