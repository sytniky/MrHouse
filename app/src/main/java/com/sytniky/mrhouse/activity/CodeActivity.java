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

public class CodeActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_ID = "cityId";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_CITY_POSITION = "cityPosition";
    private Context mContext;
    private long mCityPosition;
    private long mCityId;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCityPosition = extras.getInt(EXTRA_CITY_POSITION);
            mCityId = extras.getLong(EXTRA_CITY_ID);
            mPhone = extras.getString(EXTRA_PHONE);
        }

        final EditText etCode = (EditText) findViewById(R.id.etCode);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        Button btnBack = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().equals("")) {
                    Toast.makeText(mContext, R.string.toast_enter_code, Toast.LENGTH_SHORT)
                            .show();
                } else if (etCode.getText().length() > 4) {
                    Toast.makeText(mContext, R.string.toast_code_length_must_be_not_more_4_numbers, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // send on server
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PasswordActivity.class);
                intent.putExtra(PasswordActivity.EXTRA_CITY_POSITION, mCityPosition);
                intent.putExtra(PasswordActivity.EXTRA_CITY_ID, mCityId);
                intent.putExtra(PasswordActivity.EXTRA_PHONE, mPhone);
                startActivity(intent);
                finish();
            }
        });
    }
}
