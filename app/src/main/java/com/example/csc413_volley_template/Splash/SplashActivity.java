package com.example.csc413_volley_template.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.csc413_volley_template.MainActivity;
import com.example.csc413_volley_template.R;

/**
 * Created by NoLimitProduction on 12/13/16.
 */

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
