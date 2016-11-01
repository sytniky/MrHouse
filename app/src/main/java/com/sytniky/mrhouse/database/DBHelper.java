package com.sytniky.mrhouse.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuriy on 15.10.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase mDb;

    public DBHelper(Context context) {
        super(context, DBContracts.DB_NAME, null, DBContracts.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        mDb = db;

        db.execSQL("CREATE TABLE " + DBContracts.Cities.TABLE_NAME + " (" +
                DBContracts.Cities._ID + " INTEGER PRIMARY KEY," +
                DBContracts.Cities.COLUMN_TITLE + " TEXT)");

        db.execSQL("CREATE TABLE " + DBContracts.Services.TABLE_NAME + " (" +
                DBContracts.Services._ID + " INTEGET PRIMARY KEY, " +
                DBContracts.Services.COLUMN_TITLE + " TEXT, " +
                DBContracts.Services.COLUMN_THUMB + " INTEGER)");

        insertService(1, "Сантехник", android.R.drawable.ic_dialog_email);
        insertService(2, "Электрик", android.R.drawable.ic_dialog_map);
        insertService(3, "Плотник", android.R.drawable.ic_dialog_dialer);
        insertService(4, "Маляр", android.R.drawable.ic_dialog_alert);
    }

    private void insertService(long id, String title, int img) {
        ContentValues cv = new ContentValues();
        cv.put(DBContracts.Services._ID, id);
        cv.put(DBContracts.Services.COLUMN_TITLE, title);
        cv.put(DBContracts.Services.COLUMN_THUMB, img);
        mDb.insert(DBContracts.Services.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
