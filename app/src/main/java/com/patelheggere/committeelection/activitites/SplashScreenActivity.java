package com.patelheggere.committeelection.activitites;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.util.SharedPrefsHelper;

import static com.patelheggere.committeelection.util.AppConstants.FIRST_TIME;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPrefsHelper.getInstance().get(FIRST_TIME, true)) {
                    startActivity(new Intent(SplashScreenActivity.this, RegisterMobActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}
