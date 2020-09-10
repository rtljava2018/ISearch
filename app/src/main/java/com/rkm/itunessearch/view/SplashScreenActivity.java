package com.rkm.itunessearch.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rkm.itunessearch.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static int splashTimeOut = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this,SearchResultActivity.class));

            }

        }, splashTimeOut);
    }
}
