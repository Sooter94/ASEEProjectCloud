package com.example.edu_s.infocarmadrid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.edu_s.infocarmadrid.CocheItem;
import com.example.edu_s.infocarmadrid.database.DBContract.cocheItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgonzalez on 14/11/2018.
 */

public class cochesItemCRUD {

    private cochesDBhelper mDbHelper;
    private static cochesItemCRUD mInstance;

    private cochesItemCRUD(Context context) {
        mDbHelper = new cochesDBhelper(context);
    }

    public static cochesItemCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new cochesItemCRUD(context);

        return mInstance;
    }

    public List<CocheItem> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                cocheItem._ID,
                cocheItem.COLUMN_NAME_NAME,
                cocheItem.COLUMN_NAME_MATRICULA,
                cocheItem.COLUMN_NAME_DISTINTIVO
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                cocheItem.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<CocheItem> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getCocheItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(CocheItem item){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.cocheItem.COLUMN_NAME_NAME, item.getName());
        values.put(DBContract.cocheItem.COLUMN_NAME_MATRICULA, item.getMatricula());
        values.put(cocheItem.COLUMN_NAME_DISTINTIVO, item.getmDistintivo().name());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.cocheItem.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteAll() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.cocheItem.TABLE_NAME, selection, selectionArgs);
    }

    public int updateStatus(long ID, CocheItem.Distintivo distintivo) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("cochesItemCRUD","Item ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(cocheItem.COLUMN_NAME_DISTINTIVO, distintivo.name());

        // Which row to update, based on the ID
        String selection = DBContract.cocheItem._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.cocheItem.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static CocheItem getCocheItemFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.cocheItem._ID));
        String name = cursor.getString(cursor.getColumnIndex(cocheItem.COLUMN_NAME_NAME));
        String matricula = cursor.getString(cursor.getColumnIndex(cocheItem.COLUMN_NAME_MATRICULA));
        String distintivo = cursor.getString(cursor.getColumnIndex(cocheItem.COLUMN_NAME_DISTINTIVO));

        CocheItem item = new CocheItem(ID,name,matricula,distintivo);

        Log.d("cochesItemCRUD",item.toLog());

        return item;
    }
}
