package com.dc.doctor_communication.SplashScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.SignActivity;
import com.dc.doctor_communication.R;
import com.dc.doctor_communication.SignActivity;

public class SplashActivity extends Activity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(in);

                finish();
            }
        }, 3000);
        // 3초 뒤에 Runner 객체 실행
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}