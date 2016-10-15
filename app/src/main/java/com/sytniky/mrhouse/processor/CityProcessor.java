package com.sytniky.mrhouse.processor;

import android.content.ContentValues;
import android.content.Context;

import com.sytniky.mrhouse.database.DBContracts;
import com.sytniky.mrhouse.model.City;
import com.sytniky.mrhouse.network.MrHouseClient;
import com.sytniky.mrhouse.network.ServiceGenerator;
import com.sytniky.mrhouse.utility.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuriy on 09.10.16.
 */
public class CityProcessor {

    public static final String TAG = CityProcessor.class.getSimpleName();
    private Context mContext;

    public CityProcessor(Context context) {
        mContext = context;
    }

    public void getCities(final CityProcessorCallback callback) {

        // (4) Insert-Update the ContentProvider with a status column and
        // results column
        // Look at ContentProvider example, and build a content provider
        // that tracks the necessary data.

        // (5) Call the REST method
        Call<List<City>> call = ServiceGenerator.createService(MrHouseClient.class).getCities();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful()) {

                    // (8) Insert-Update the ContentProvider status, and insert the result
                    // on success Parsing the JSON response (on success) and inserting into
                    // the content provider
                    updateContentProvider(response);

                    // (9) Operation complete callback to Service
                    callback.send(response.code());

                } else {
                    // error response, no access to resource?
                    Logger.warn(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Logger.warn(TAG, t.getMessage(), t);
            }
        });
    }

    private void updateContentProvider(Response<List<City>> response) {
        mContext.getContentResolver().delete(DBContracts.Cities.CONTENT_URI, null, null);

        for (City city : response.body()) {
            Logger.debug(TAG, "Received city: " + city.getTitle());
            ContentValues cv = new ContentValues();
            cv.put(DBContracts.Cities._ID, city.getId());
            cv.put(DBContracts.Cities.COLUMN_TITLE, city.getTitle());
            mContext.getContentResolver().insert(DBContracts.Cities.CONTENT_URI, cv);
        }
    }
}
