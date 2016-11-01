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

public class PasswordActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_ID = "cityId";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_CITY_POSITION = "cityPosition";
    private Context mContext;
    private int mCityPosition;
    private long mCityId;
    private String mPhone;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCityPosition = extras.getInt(EXTRA_CITY_POSITION);
            mCityId = extras.getLong(EXTRA_CITY_ID);
            mPhone = extras.getString(EXTRA_PHONE);
        }

        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etRepeatPassword = (EditText) findViewById(R.id.etRepeatPassword);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        Button btnBack = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPassword = String.valueOf(etPassword.getText());
                String repeatPassword = String.valueOf(etRepeatPassword.getText());

                if (mPassword.equals("")) {
                    Toast.makeText(mContext, R.string.toast_enter_password, Toast.LENGTH_SHORT)
                            .show();
                } else if (!mPassword.equals(repeatPassword)) {
                    Toast.makeText(mContext,  R.string.toast_passwords_are_not_equal, Toast.LENGTH_SHORT)
                            .show();
                } else if (mPassword.length() < 3 || mPassword.length() > 32) {
                    Toast.makeText(mContext, R.string.toast_available_password_length_is_from_3_to_32, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Intent intent = new Intent(mContext, CodeActivity.class);
                    intent.putExtra(CodeActivity.EXTRA_CITY_POSITION, mCityPosition);
                    intent.putExtra(CodeActivity.EXTRA_CITY_ID, mCityId);
                    intent.putExtra(CodeActivity.EXTRA_PHONE, mPhone);
                    intent.putExtra(CodeActivity.EXTRA_PASSWORD, mPassword);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhoneActivity.class);
                intent.putExtra(PhoneActivity.EXTRA_CITY_POSITION, mCityPosition);
                intent.putExtra(PhoneActivity.EXTRA_CITY_ID, mCityId);
                intent.putExtra(PhoneActivity.EXTRA_PHONE, mPhone);
                startActivity(intent);
                finish();
            }
        });
    }
}
