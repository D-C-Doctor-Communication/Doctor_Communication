package com.dc.doctor_communication.SymptomRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.LinkedList;


public class Search extends AppCompatActivity {
    JSONArray lately_symptom;
    String l_symptom;
    LinkedList<String> LATELY = new LinkedList<String>();
    String symptom;
    int repeat;
    SharedPreferences sharedPreferences;
    SharedPreferences list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout View = (LinearLayout) findViewById(R.id.lately_layout);
        TextView Search_click = (TextView) findViewById(R.id.search_text02);

        Intent intent = getIntent();
        repeat = intent.getExtras().getInt("repeat");

        /*
        sharedPreferences = getSharedPreferences("symptom", MODE_PRIVATE);
        if (sharedPreferences != null) {
            symptom = sharedPreferences.getString("inputText", "");
            Log.e("증상", "저장완료 - " + symptom);    // SharedPreferences에 저장되어있던 값 찍기.



            list= getSharedPreferences("symptomlist", MODE_PRIVATE);
            l_symptom = list.getString("array",null);

            try {
                lately_symptom = new JSONArray(l_symptom);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }

            lately_symptom.put(symptom);

            for (int i = 0; i < lately_symptom.length(); i++) {
                Button btn = new Button(this);
                try {
                    btn.setText(lately_symptom.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    e.printStackTrace();
                }
                View.addView(btn);
            }


        }

         */


        //textview 클릭되면 다음 페이지로 넘기기
        Search_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("시작", "서치 페이지");
                Intent intent = new Intent(Search.this, SearchList.class);
                intent.putExtra("repeat", repeat);

                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        list = getSharedPreferences("symptomlist", MODE_PRIVATE);
        SharedPreferences.Editor editor = list.edit();
        editor.putString("array", lately_symptom.toString());
        //Log.e("리스트",lately_symptom.toString());
        editor.commit();
    }

 */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}