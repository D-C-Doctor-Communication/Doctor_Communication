package com.dc.doctor_communication.SymptomRegistration;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectWorse extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    EditText add_worse;
    Button worse_btn;

    String symptom;//선택한 증상
    String[] select_worse; //선택한 악화상황
    String[] selected_body;
    String[] selected_pattern;
    String selected_level;
    String selected_levelNm;
    int part;
    List<String> worse = new ArrayList<String>();
    int cnt=0;
    String p;
    int repeat;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        selected_body = intent.getStringArrayExtra("bparts");
        part =intent.getExtras().getInt("part");
        selected_levelNm =intent.getExtras().getString("levelNm");
        selected_pattern = intent.getStringArrayExtra("pattern");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_worse);

        adapter = new ListViewAdapter();

        listView = (ListView)findViewById(R.id.osymptom_list);
        listView.setAdapter(adapter);
        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;

        add_worse = findViewById(R.id.add_osymptom);
        worse_btn =findViewById(R.id.osymptom_btn);

        //다음페이지 버튼
       nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWorse.this, OtherSymptom.class);

                //선택된 리스트 확인하기
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount();
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        cnt++;
                    }
                }
                select_worse= new String[cnt];
                cnt=0;

                //선택된 리스트 select_worse 에 넣기
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        select_worse[cnt++]=worse.get(i);
                    }
                }
                //선택 X 팝업 처리
                if(select_worse.length==0){
                    new AlertDialog.Builder(SelectWorse.this)
                            .setMessage("악화상황을 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern", selected_pattern);
                intent.putExtra("worse",select_worse);
                intent.putExtra("repeat",repeat);

                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });

       //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWorse.this, SelectPattern.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("part",part);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("repeat",repeat);

                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });

        //악화상황 추가 버튼
        worse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(add_worse.getText().toString());
                worse.add(add_worse.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        //symptom.json 파일에서 선택한 증상의 악화상황 읽기
        AssetManager assetManager = getAssets();
        try {
            InputStream is =assetManager.open("symptom.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();

            String line = reader.readLine();

            while(line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONArray jsonArray= new JSONArray(jsonData);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jo=jsonArray.getJSONObject(i);
                String name= jo.getString("symptom");
                JSONArray jsonArray2 = jo.getJSONArray("worse");

                //선택한 증상이라면 악화상황 가져오기
                if(name.equals(symptom)){
                    for(int j=0; j<jsonArray2.length(); j++){
                        JSONObject jo2=jsonArray2.getJSONObject(j);
                        p = jo2.getString("w");
                        worse.add(p);
                        adapter.addItem(p);
                    }

                }

                adapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}
