package com.sytniky.mrhouse.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.database.DBContracts;

public class CityActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 101;
    private Context mContext;
    private Spinner mSpCities;
    private SimpleCursorAdapter mCitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mContext = this;

        mSpCities = (Spinner) findViewById(R.id.spCities);

        mSpCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id > 0) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCitiesAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                null,
                new String[]{DBContracts.Cities.COLUMN_TITLE},
                new int[]{android.R.id.text1},
                0
        );

        mSpCities.setAdapter(mCitiesAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                DBContracts.Cities.CONTENT_URI,
                DBContracts.Cities.DEFAULT_PROJECTION,
                null,
                null,
                DBContracts.Cities.DEFAULT_SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Cursor mergedCursor = addCityHeaderToCursor(data);
        mCitiesAdapter.changeCursor(mergedCursor);
        mCitiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCitiesAdapter.changeCursor(null);
    }

    @NonNull
    private static Cursor addCityHeaderToCursor(Cursor citiesCursor) {
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        DBContracts.Cities._ID,
                        DBContracts.Cities.COLUMN_TITLE
                });
        matrixCursor.addRow(new String[]{
                "0",
                ""
        });

        Cursor[] cursorToMerge = new Cursor[2];
        cursorToMerge[0] = matrixCursor;
        cursorToMerge[1] = citiesCursor;
        return new MergeCursor(cursorToMerge);
    }
}
