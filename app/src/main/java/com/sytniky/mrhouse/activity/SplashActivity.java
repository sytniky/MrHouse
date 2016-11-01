package com.sytniky.mrhouse.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.service.MrHouseServiceHelper;
import com.sytniky.mrhouse.utility.Constants;
import com.sytniky.mrhouse.utility.Logger;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private BroadcastReceiver mBroadcastReceiver;
    private Long mRequestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(MrHouseServiceHelper.ACTION_REQUEST_RESULT);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                long resultRequestId = intent.
                        getLongExtra(MrHouseServiceHelper.EXTRA_REQUEST_ID, 0);

                Logger.debug(TAG, "Received intent: " + intent.getAction() + ", request ID: "
                        + resultRequestId);

                if (resultRequestId == mRequestId) {

                    Logger.debug(TAG, "Result is for our request ID");

                    int resultCode = intent.getIntExtra(MrHouseServiceHelper.EXTRA_RESULT_CODE, 0);

                    Logger.debug(TAG, "Result code = " + resultCode);

                    if (resultCode == 200) {

                        Logger.debug(TAG, "Updating synchronization date...");
                        updateCitySynchDate();
                        startNextActivity();

                    } else {
                        showToast("An error has occurred.");
                    }

                } else {
                    Logger.debug(TAG, "Result is NOT for our request ID");
                }
            }
        };
        registerReceiver(mBroadcastReceiver, intentFilter);

        MrHouseServiceHelper serviceHelper = MrHouseServiceHelper.getInstance(this);

        long savedCitySynchDate = getSavedCitySynchDate();
        Logger.debug(TAG, "Saved synch city date = " + String.valueOf(savedCitySynchDate));

        if (savedCitySynchDate == 0) {

            // make request and wait results...
            mRequestId = serviceHelper.getCities();
        } else if (isExpiredCitySynchDate(savedCitySynchDate)) {

            // make request and go to next step without waiting of results
            mRequestId = serviceHelper.getCities();
            startNextActivity();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // go to next step
            startNextActivity();
        }


//        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!! DEBUG !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        ContentValues cv = new ContentValues();
//        cv.put(DBContracts.Cities._ID, 1);
//        cv.put(DBContracts.Cities.COLUMN_TITLE, "Одесса");
//        getContentResolver().insert(DBContracts.Cities.CONTENT_URI, cv);
//        // go to next step
//        startNextActivity();
//        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!! DEBUG !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    private void updateCitySynchDate() {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.Preferences.GENERAL, MODE_PRIVATE).edit();
        editor.putLong(Constants.Preferences.CITY_SYNCH_DATE, System.currentTimeMillis())
                .apply();

    }

    private long getSavedCitySynchDate() {
        SharedPreferences preferences
                = getSharedPreferences(Constants.Preferences.GENERAL, MODE_PRIVATE);
        return preferences.getLong(Constants.Preferences.CITY_SYNCH_DATE, 0);
    }

    private boolean isExpiredCitySynchDate(long savedCitySynchDate) {
        return (savedCitySynchDate + (1000 * 60 * 60 * 24)) < System.currentTimeMillis();
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
        finish();
    }
}
