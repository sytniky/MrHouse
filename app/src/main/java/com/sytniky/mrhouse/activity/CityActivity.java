package com.sytniky.mrhouse.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.database.DBContracts;

public class CityActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private SimpleCursorAdapter mCitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mContext = this;

        Spinner spCities = (Spinner) findViewById(R.id.spCities);
        spCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);
//                finish();
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

        spCities.setAdapter(mCitiesAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                DBContracts.Cities.CONTENT_URI,
                DBContracts.Cities.DEFAULT_PROJECTION,
                null,
                null,
                DBContracts.Cities.COLUMN_TITLE + " COLLATE LOCALIZED ASC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCitiesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCitiesAdapter.swapCursor(null);
    }
}
