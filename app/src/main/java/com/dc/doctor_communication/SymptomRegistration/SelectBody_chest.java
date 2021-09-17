package com.dc.doctor_communication.SymptomRegistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.R;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_chest extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_CHEST =new int[5];
    String [] CHEST ={"오른쪽 가슴","가슴 중앙","왼쪽 가슴","오른쪽 겨드랑이","왼쪽 겨드랑이"}; //가슴 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_chest; //선택한 가슴 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_chest);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton chest01 = findViewById(R.id.chest01) ;
        ImageButton chest02 = findViewById(R.id.chest02) ;
        ImageButton chest03 = findViewById(R.id.chest03) ;
        ImageButton chest04 = findViewById(R.id.chest04) ;
        ImageButton chest05 = findViewById(R.id.chest05) ;





        //머리 세부 부위 select 유무 확인
        chest01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_CHEST[0]==0){
                    chest01.setSelected(true);
                    CHECK_CHEST[0]=1;
                }else {
                    chest01.setSelected(false);
                    CHECK_CHEST[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        chest02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_CHEST[1]==0){
                    chest02.setSelected(true);
                    CHECK_CHEST[1]=1;
                }else {
                    chest02.setSelected(false);
                    CHECK_CHEST[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        chest03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_CHEST[2]==0){
                    chest03.setSelected(true);
                    CHECK_CHEST[2]=1;
                }else {
                    chest03.setSelected(false);
                    CHECK_CHEST[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        chest04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_CHEST[3]==0){
                    chest04.setSelected(true);
                    CHECK_CHEST[3]=1;
                }else {
                    chest04.setSelected(false);
                    CHECK_CHEST[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        chest05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_CHEST[4]==0){
                    chest05.setSelected(true);
                    CHECK_CHEST[4]=1;
                }else {
                    chest05.setSelected(false);
                    CHECK_CHEST[4]=0;
                }
                Log.e("ji", "click5");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_chest.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //가슴슴 세부 부위가 선택되 있으면 BODY에 넣기
                if(chest01.isSelected()){
                    BODY.add(CHEST[0]);
                }
                if(chest02.isSelected()){
                    BODY.add(CHEST[1]);
                }
                if(chest03.isSelected()){
                    BODY.add(CHEST[2]);
                }
                if(chest04.isSelected()){
                    BODY.add(CHEST[3]);
                }
                if(chest05.isSelected()){
                    BODY.add(CHEST[4]);
                }
               

                //선택한 가슴 세부부위 string 변환해서 select_head에 넣기
                select_chest = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_chest.length==0){
                    new AlertDialog.Builder(SelectBody_chest.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_chest);
                for(int i = 0; i< select_chest.length; i++)
                    Log.e("jj", select_chest[i]);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_chest.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}
