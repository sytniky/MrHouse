package com.sytniky.mrhouse.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.fragment.ServiceFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, ServiceFragment.newInstance(), "service_fr")
                .commit();
    }
}
