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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.database.DBContracts;

public class CityActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String EXTRA_CITY_ID = "cityId";
    private static final int LOADER_ID = 101;
    private Context mContext;
    private SimpleCursorAdapter mCityAdapter;
    private long mCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCityId = extras.getLong(EXTRA_CITY_ID);
        }

        Spinner spCity = (Spinner) findViewById(R.id.spCity);
        Button btnNext = (Button) findViewById(R.id.btnNext);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCityId = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityId > 0) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra(PhoneActivity.EXTRA_CITY_ID, mCityId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_select_city, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        mCityAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                null,
                new String[]{DBContracts.Cities.COLUMN_TITLE},
                new int[]{android.R.id.text1},
                0
        );

        spCity.setAdapter( mCityAdapter);
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
         mCityAdapter.changeCursor(mergedCursor);
         mCityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
         mCityAdapter.changeCursor(null);
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
