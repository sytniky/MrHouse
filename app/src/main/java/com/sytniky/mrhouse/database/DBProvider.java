package com.sytniky.mrhouse.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by yuriy on 15.10.16.
 */
public class DBProvider extends ContentProvider {
    public static final int CITIES = 11;
    public static final int CITIES_ID = 12;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(DBContracts.AUTHORITY, DBContracts.Cities.TABLE_NAME, CITIES);
        sUriMatcher.addURI(DBContracts.AUTHORITY, DBContracts.Cities.TABLE_NAME + "/#", CITIES_ID);
    }

    private DBHelper dbHelper;

    public DBProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case CITIES_ID:
                String id = uri.getPathSegments().get(DBContracts.Cities.CITIES_ID_PATH_POSITION);
                qb.appendWhere(DBContracts.Cities._ID + " = " + id);
            case CITIES:
                qb.setTables(DBContracts.Cities.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv) {
        Uri resUri = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = cv != null ? new ContentValues(cv) : new ContentValues();
        switch (sUriMatcher.match(uri)) {
            case CITIES:
                if (!values.containsKey(DBContracts.Cities._ID)
                        || !values.containsKey(DBContracts.Cities.COLUMN_TITLE))
                    throw new IllegalArgumentException("Wrong values");

                long rowId = db.insert(DBContracts.Cities.TABLE_NAME, null, values);
                if (rowId > 0) {
                    resUri = ContentUris.withAppendedId(DBContracts.Cities.CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(resUri, null);
                }
                break;
        }
        return resUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowId;
        switch (sUriMatcher.match(uri)) {
            case CITIES_ID:
                String id = uri.getPathSegments().get(DBContracts.Cities.CITIES_ID_PATH_POSITION);
                selection = DBContracts.Cities._ID + "=" + id;
            case CITIES:
                rowId = db.delete(DBContracts.Cities.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return rowId;
    }
}
