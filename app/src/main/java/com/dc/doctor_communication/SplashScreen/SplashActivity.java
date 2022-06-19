package com.dc.doctor_communication.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dc.doctor_communication.SignActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent); //2초 후 MainActivity를 실행해주자

                finish();
            }
        }, 2000);
    }
}