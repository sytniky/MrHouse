package com.sytniky.mrhouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuriy on 15.10.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBContracts.DB_NAME, null, DBContracts.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBContracts.Cities.TABLE_NAME + " (" +
                DBContracts.Cities._ID + " INTEGER PRIMARY KEY," +
                DBContracts.Cities.COLUMN_TITLE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
