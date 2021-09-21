package com.dc.doctor_communication.GuideScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.dc.doctor_communication.R;

public class GuideActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);
    }


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //손가락을 화면에서 뗄 때 할 일
                Intent intent = new Intent(this, GuideActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
        }
        return true;
    }
}