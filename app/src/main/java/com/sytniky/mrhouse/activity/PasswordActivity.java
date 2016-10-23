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
    private Context mContext;
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
                if (!etPassword.getText().equals("")
                        && etPassword.getText().equals(etRepeatPassword.getText())) {
                    mPassword = String.valueOf(etPassword.getText());
                    Intent intent = new Intent(mContext, CodeActivity.class);
                    intent.putExtra(CodeActivity.EXTRA_CITY_ID, mCityId);
                    intent.putExtra(CodeActivity.EXTRA_PHONE, mPhone);
                    intent.putExtra(CodeActivity.EXTRA_PASSWORD, mPassword);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_enter_password_and_repeat_password, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhoneActivity.class);
                intent.putExtra(PhoneActivity.EXTRA_CITY_ID, mCityId);
                intent.putExtra(PhoneActivity.EXTRA_PHONE, mPhone);
                startActivity(intent);
                finish();
            }
        });
    }
}