package com.dc.doctor_communication.SymptomRegistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.R;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_leg extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_LEG =new int[8];
    String [] LEG ={"왼쪽 허벅지","오른쪽 허벅지","왼쪽 종아리","오른쪽 종아리","왼쪽 무릎","오른쪽 무릎","왼쪽 다리 전체","오른쪽 다리 전체"}; //다리 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_leg; //선택한 다리 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_leg);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton leg01 = findViewById(R.id.leg01) ;
        ImageButton leg02 = findViewById(R.id.leg02) ;
        ImageButton leg03 = findViewById(R.id.leg03) ;
        ImageButton leg04 = findViewById(R.id.leg04) ;
        ImageButton leg05 = findViewById(R.id.leg05) ;
        ImageButton leg06 = findViewById(R.id.leg06) ;
        ImageButton leg07 = findViewById(R.id.leg07) ;
        ImageButton leg08 = findViewById(R.id.leg08) ;
        

        //다리 세부 부위 select 유무 확인
        leg01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[0]==0){
                    leg01.setSelected(true);
                    CHECK_LEG[0]=1;
                }else {
                    leg01.setSelected(false);
                    CHECK_LEG[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        leg02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[1]==0){
                    leg02.setSelected(true);
                    CHECK_LEG[1]=1;
                }else {
                    leg02.setSelected(false);
                    CHECK_LEG[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        leg03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[2]==0){
                    leg03.setSelected(true);
                    CHECK_LEG[2]=1;
                }else {
                    leg03.setSelected(false);
                    CHECK_LEG[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        leg04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[3]==0){
                    leg04.setSelected(true);
                    CHECK_LEG[3]=1;
                }else {
                    leg04.setSelected(false);
                    CHECK_LEG[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        leg05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[4]==0){
                    leg05.setSelected(true);
                    CHECK_LEG[4]=1;
                }else {
                    leg05.setSelected(false);
                    CHECK_LEG[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        leg06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[5]==0){
                    leg06.setSelected(true);
                    CHECK_LEG[5]=1;
                }else {
                    leg06.setSelected(false);
                    CHECK_LEG[5]=0;
                }
                Log.e("ji", "click6");
            }
        });
        leg07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[6]==0){
                    leg07.setSelected(true);
                    CHECK_LEG[6]=1;
                }else {
                    leg07.setSelected(false);
                    CHECK_LEG[6]=0;
                }
                Log.e("ji", "click7");
            }
        });
        leg08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_LEG[7]==0){
                    leg08.setSelected(true);
                    CHECK_LEG[7]=1;
                }else {
                    leg08.setSelected(false);
                    CHECK_LEG[7]=0;
                }
                Log.e("ji", "click8");
            }
        });
       
        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_leg.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //머리 세부 부위가 선택되어 있으면 BODY에 넣기
                if(leg01.isSelected()){
                    BODY.add(LEG[0]);
                }
                if(leg02.isSelected()){
                    BODY.add(LEG[1]);
                }
                if(leg03.isSelected()){
                    BODY.add(LEG[2]);
                }
                if(leg04.isSelected()){
                    BODY.add(LEG[3]);
                }
                if(leg05.isSelected()){
                    BODY.add(LEG[4]);
                }
                if(leg06.isSelected()){
                    BODY.add(LEG[5]);
                }
                if(leg07.isSelected()){
                    BODY.add(LEG[6]);
                }
                if(leg08.isSelected()){
                    BODY.add(LEG[7]);
                }

                //선택한 다리 세부부위 string 변환해서 select_head에 넣기
                select_leg = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_leg.length==0){
                    new AlertDialog.Builder(SelectBody_leg.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_leg);
                for(int i = 0; i< select_leg.length; i++)
                    Log.e("jj", select_leg[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_leg.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
