package com.dc.doctor_communication.SymptomRegistration;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.DataManagement.Symptom2;
import com.dc.doctor_communication.FireBaseManagement.FireData;
import com.dc.doctor_communication.FireBaseManagement.Symptom;
import com.dc.doctor_communication.MainActivity;
import com.dc.doctor_communication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OtherSymptom extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    EditText add_pattern;
    Button osymptom_btn;

    FirebaseAuth firebaseAuth;
    // 날짜 받기
    long now = System.currentTimeMillis();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    String date_txt = sdf.format(date);
    int repeat;

    String selected_symptom;
    String[] select_osymptom; // 선택한 동반 증상
    String[] selected_body;
    String[] selected_pattern;
    String[] selected_worse;
    String selected_levelNm;
    int part;
    List<String> osymptom = new ArrayList<String>();
    int cnt=0;
    String p;
    AlertDialog Dialog;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_symptom);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        selected_symptom = intent.getExtras().getString("symptom");
        selected_body = intent.getStringArrayExtra("bparts");
        part =intent.getExtras().getInt("part");
        selected_levelNm = intent.getExtras().getString("levelNm");
        selected_pattern = intent.getStringArrayExtra("pattern");
        selected_worse = intent.getStringArrayExtra("worse");
        repeat = intent.getExtras().getInt("repeat");

        firebaseAuth =  FirebaseAuth.getInstance();

        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.osymptom_list);
        listView.setAdapter(adapter);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;

        add_pattern = findViewById(R.id.add_osymptom);
        osymptom_btn =findViewById(R.id.osymptom_btn);

        //다음 페이지 버튼
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 리스트 확인
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count1 = adapter.getCount();
                for(int i=0; i<=count1-1; i++){
                    if(checkedItems.get(i)){
                        cnt++;
                    }
                }
                select_osymptom= new String[cnt];
                cnt=0;
                //선택되어 있다면 select_osymptom 배열에 넣기
                for(int i=0; i<=count1-1; i++){
                    if(checkedItems.get(i)){
                        select_osymptom[cnt++]=osymptom.get(i);
                    }
                }

                Intent intent = new Intent(OtherSymptom.this, AddDetails.class);

                //선택 X 팝업 처리
                if(select_osymptom.length==0){
                    new AlertDialog.Builder(OtherSymptom.this)
                            .setMessage("동반증상을 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("users");

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();


                //해당없음 팝업 처리(뒤 페이지인 추가증상 등록으로 넘어가지 않음)
                if(select_osymptom[0].equals("해당없음")) {
                    Log.d("hedag", selected_symptom);
                    Log.d("hedag", selected_body[0]);
                    Log.d("hedag", selected_levelNm);
                    Log.d("hedag", selected_pattern[0]);
                    Log.d("hedag", selected_worse[0]);

                    Log.d("sick", repeat+"");
                    myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("symptom").setValue(selected_symptom);
                    myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("part").setValue(selected_body[0]);
                    myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("painLevel").setValue(selected_levelNm);
                    myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("pain_characteristics").setValue(selected_pattern[0]);
                    myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("pain_situation").setValue(selected_worse[0]);



                    /* SharedPreference 사용한 객체 정보 저장 */

                    //Shared 준비
                    SharedPreferences gsonIndexSp = getSharedPreferences("gsonIndexFile",MODE_PRIVATE);
                    SharedPreferences gsonSharedPreferences = getSharedPreferences("gsonDataFile",MODE_PRIVATE);
                    SharedPreferences.Editor gsonEditor = gsonSharedPreferences.edit();
                    SharedPreferences.Editor gsonIndexEditor = gsonIndexSp.edit();

                    //인덱스값
                    int gsonIndex = gsonIndexSp.getInt("gsonIndex",0);
                    Log.d("myapp","인덱스값 : "+gsonIndex);

                    //데이터 객체 생성 + 저장
                    Date today = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Symptom sympObj = new Symptom(sdf.format(today),selected_symptom,selected_body[0],selected_levelNm,selected_pattern[0],selected_worse[0]);
                    String symptomGson = "";
                    Gson gson = new GsonBuilder().create();
                    symptomGson= gson.toJson(sympObj,Symptom.class);

                    //키값에 value 저장
                    gsonEditor.putString(Integer.toString(gsonIndex),symptomGson);
                    //키값 ++
                    gsonIndexEditor.putInt("gsonIndex",gsonIndex+1);
                    //변경정보 저장
                    gsonEditor.commit();
                    gsonIndexEditor.commit();





                    AlertDialog.Builder Dialog_bd = new AlertDialog.Builder(OtherSymptom.this);
                    Dialog_bd.setMessage("증상이 입력되었습니다.");

                    Dialog = Dialog_bd.create();
                    Dialog.show();

                    intent = new Intent(OtherSymptom.this, MainActivity.class);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if(Dialog != null){
                                if(Dialog.isShowing()){
                                    Dialog.dismiss();
                                }
                            }
                        }
                    }, 1000);

                }

                SharedPreferences sharedPreferences= getSharedPreferences("symptom", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("inputText",selected_symptom); // key,value 형식으로 저장
                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                Log.e("증상", "증상저장1");

                intent.putExtra("symptom", selected_symptom);
                intent.putExtra("part",part);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern", selected_pattern);
                intent.putExtra("worse",selected_worse);
                intent.putExtra("osymptom", select_osymptom);
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
                Intent intent = new Intent(OtherSymptom.this, SelectWorse.class);
                intent.putExtra("symptom", selected_symptom);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("part",part);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern",selected_pattern);
                intent.putExtra("repeat",repeat);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);

                finish();
            }
        });

        //동반 증상 추가 버튼
        osymptom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(add_pattern.getText().toString());
                osymptom.add(add_pattern.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        //증상정리된 symptom.json 파일에서 선택한 증상의 동반 증상 가져오기
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
                JSONArray jsonArray2 = jo.getJSONArray("other_symptom");

                if(name.equals(selected_symptom)){
                    for(int j=0; j<jsonArray2.length(); j++){
                        JSONObject jo2=jsonArray2.getJSONObject(j);
                        p = jo2.getString("symptom");
                        osymptom.add(p);
                        adapter.addItem(p);
                    }

                }
                //textView.setText(p);

                adapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        if(Dialog != null){
            if(Dialog.isShowing()){
                Dialog.dismiss();
            }
        }
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}
