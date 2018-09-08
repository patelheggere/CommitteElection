package com.patelheggere.committeelection.activitites;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.util.SharedPrefsHelper;

import static com.patelheggere.committeelection.util.AppConstants.FIRST_TIME;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPrefsHelper.getInstance().get(FIRST_TIME, true)) {
                    startActivity(new Intent(SplashScreenActivity.this, InstructionActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, chiefPatronActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}
