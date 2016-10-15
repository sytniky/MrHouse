package com.sytniky.mrhouse.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yuriy on 09.10.16.
 */
public class MrHouseServiceHelper {

    public static final String ACTION_REQUEST_RESULT = "actionRequestResult";
    public static final String EXTRA_REQUEST_ID = "extraRequestId";
    public static final String EXTRA_RESULT_CODE = "extraResultCode";
    private static final String HASH_KEY_CITY = "hashKeyCity";
    private static final String REQUEST_ID = "requestId";

    private static Object sLock = new Object();
    private static MrHouseServiceHelper sInstance;
    private Context mContext;
    private Map<String, Long> mPendingRequests = new HashMap<>();

    public static MrHouseServiceHelper getInstance(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new MrHouseServiceHelper(context);
            }
        }
        return sInstance;
    }

    private MrHouseServiceHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    public Long getCities() {

        if (mPendingRequests.containsKey(HASH_KEY_CITY)) {
            return mPendingRequests.get(HASH_KEY_CITY);
        }

        long requestId = generateRequestId();
        mPendingRequests.put(HASH_KEY_CITY, requestId);

        ResultReceiver serviceCallback = new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                handleGetCitiesResponse(resultCode, resultData);
            }
        };

        Intent intent = new Intent(mContext, MrHouseService.class);
        intent.putExtra(MrHouseService.EXTRA_METHOD, MrHouseService.METHOD_GET);
        intent.putExtra(MrHouseService.EXTRA_RESOURCE_TYPE, MrHouseService.RESOURCE_TYPE_CITIES);
        intent.putExtra(MrHouseService.SERVICE_CALLBACK, serviceCallback);
        intent.putExtra(REQUEST_ID, requestId);

        mContext.startService(intent);

        return requestId;
    }

    private void handleGetCitiesResponse(int resultCode, Bundle resultData) {
        Intent originalIntent
                = (Intent) resultData.getParcelable(MrHouseService.EXTRA_ORIGINAL_INTENT);

        if (originalIntent != null) {
            long requestId = originalIntent.getLongExtra(REQUEST_ID, 0);

            mPendingRequests.remove(HASH_KEY_CITY);

            Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
            resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
            resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

            mContext.sendBroadcast(resultBroadcast);
        }
    }

    private long generateRequestId() {
        long requestId = UUID.randomUUID().getLeastSignificantBits();
        return requestId;
    }
}
