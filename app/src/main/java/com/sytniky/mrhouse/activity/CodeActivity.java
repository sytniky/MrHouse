package com.sytniky.mrhouse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sytniky.mrhouse.R;

public class CodeActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_ID = "cityId";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);


    }
}
