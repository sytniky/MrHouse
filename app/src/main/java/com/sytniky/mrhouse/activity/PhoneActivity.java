package com.sytniky.mrhouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sytniky.mrhouse.R;

public class PhoneActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_ID = "cityId";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_CITY_POSITION = "cityPosition";
    private Context mContext;
    private int mCityPosition;
    private long mCityId;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        mContext = this;

        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        Button btnBack = (Button) findViewById(R.id.btnBack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCityPosition = extras.getInt(EXTRA_CITY_POSITION);
            mCityId = extras.getLong(EXTRA_CITY_ID);
            mPhone = extras.getString(EXTRA_PHONE);
            etPhone.setText(mPhone);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = String.valueOf(etPhone.getText());
                if (!mPhone.equals("")) {
                    Intent intent = new Intent(mContext, PasswordActivity.class);
                    intent.putExtra(PasswordActivity.EXTRA_CITY_POSITION, mCityPosition);
                    intent.putExtra(PasswordActivity.EXTRA_CITY_ID, mCityId);
                    intent.putExtra(PasswordActivity.EXTRA_PHONE, mPhone);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_enter_phone, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityActivity.class);
                intent.putExtra(CityActivity.EXTRA_CITY_POSITION, mCityPosition);
                intent.putExtra(CityActivity.EXTRA_CITY_ID, mCityId);
                startActivity(intent);
                finish();
            }
        });
    }
}
