package com.sytniky.mrhouse.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.sytniky.mrhouse.processor.CityProcessor;
import com.sytniky.mrhouse.processor.CityProcessorCallback;

/**
 * Created by yuriy on 09.10.16.
 */
public class MrHouseService extends IntentService {

    public static final String EXTRA_METHOD = "com.sytniky.mrhouse.service.EXTRA_METHOD";
    public static final String METHOD_GET = "GET";
    public static final String EXTRA_RESOURCE_TYPE = "com.sytniky.mrhouse.service.EXTRA_RESOURCE_TYPE";
    public static final int RESOURCE_TYPE_CITIES = 11;
    public static final String SERVICE_CALLBACK = "com.sytniky.mrhouse.service.SERVICE_CALLBACK";
    public static final String EXTRA_ORIGINAL_INTENT = "com.sytniky.mrhouse.service.EXTRA_ORIGINAL_INTENT";
    private static final int REQUEST_INVALID = -1;

    private ResultReceiver mCallback;
    private Intent mOriginalRequestIntent;

    public MrHouseService() {
        super("MrHouseService");
    }

    @Override
    protected void onHandleIntent(Intent requestIntent) {

        mOriginalRequestIntent = requestIntent;

        // Get request data from Intent
        String method = requestIntent.getStringExtra(MrHouseService.EXTRA_METHOD);
        int resourceType = requestIntent.getIntExtra(MrHouseService.EXTRA_RESOURCE_TYPE, -1);
        mCallback = requestIntent.getParcelableExtra(MrHouseService.SERVICE_CALLBACK);

        switch (resourceType) {
            case RESOURCE_TYPE_CITIES:
                if (method.equalsIgnoreCase(METHOD_GET)) {
                    CityProcessor processor = new CityProcessor(getApplicationContext());
                    processor.getCities(makeCityProcessorCallback());
                } else {
                    mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                }
                break;

            default:
                mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                break;
        }
    }

    private CityProcessorCallback makeCityProcessorCallback() {
        CityProcessorCallback callback = new CityProcessorCallback() {

            @Override
            public void send(int resultCode) {
                if (mCallback != null) {
                    mCallback.send(resultCode, getOriginalIntentBundle());
                }
            }
        };
        return callback;
    }

    protected Bundle getOriginalIntentBundle() {
        Bundle originalRequest = new Bundle();
        originalRequest.putParcelable(EXTRA_ORIGINAL_INTENT, mOriginalRequestIntent);
        return originalRequest;
    }
}
