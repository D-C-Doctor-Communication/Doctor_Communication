package com.dc.doctor_communication.Recording;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Recording extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Toast.makeText(this.getApplicationContext(),"녹음 눌림", Toast.LENGTH_SHORT).show();
    }
}
