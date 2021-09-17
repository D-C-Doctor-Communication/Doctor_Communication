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

public class SelectBody_foot extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_FOOT =new int[10];
    String [] FOOT ={"왼쪽 발목","왼쪽 발등","왼쪽 발가락","왼쪽 발꿈치","왼쪽 발바닥","오른쪽 발목","오른쪽 발등","오른쪽 발가락","오른쪽 발바닥","오른쪽 발꿈치"}; //발 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_foot; //선택한 발 부위
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
        setContentView(R.layout.select_body_foot);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton foot01 = findViewById(R.id.foot01) ;
        ImageButton foot02 = findViewById(R.id.foot02) ;
        ImageButton foot03 = findViewById(R.id.foot03) ;
        ImageButton foot04 = findViewById(R.id.foot04) ;
        ImageButton foot05 = findViewById(R.id.foot05) ;
        ImageButton foot06= findViewById(R.id.foot06) ;
        ImageButton foot07 = findViewById(R.id.foot07) ;
        ImageButton foot08 = findViewById(R.id.foot08) ;
        ImageButton foot09 = findViewById(R.id.foot09) ;
        ImageButton foot10 = findViewById(R.id.foot10) ;




        //팔 세부 부위 select 유무 확인
        foot01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[0]==0){
                    foot01.setSelected(true);
                    CHECK_FOOT[0]=1;
                }else {
                    foot01.setSelected(false);
                    CHECK_FOOT[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        foot02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[1]==0){
                    foot02.setSelected(true);
                    CHECK_FOOT[1]=1;
                }else {
                    foot02.setSelected(false);
                    CHECK_FOOT[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        foot03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[2]==0){
                    foot03.setSelected(true);
                    CHECK_FOOT[2]=1;
                }else {
                    foot03.setSelected(false);
                    CHECK_FOOT[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        foot04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[3]==0){
                    foot04.setSelected(true);
                    CHECK_FOOT[3]=1;
                }else {
                    foot04.setSelected(false);
                    CHECK_FOOT[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        foot05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[4]==0){
                    foot05.setSelected(true);
                    CHECK_FOOT[4]=1;
                }else {
                    foot05.setSelected(false);
                    CHECK_FOOT[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        foot06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[5]==0){
                    foot06.setSelected(true);
                    CHECK_FOOT[5]=1;
                }else {
                    foot06.setSelected(false);
                    CHECK_FOOT[5]=0;
                }
                Log.e("ji", "click6");
            }
        });
        foot07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[6]==0){
                    foot07.setSelected(true);
                    CHECK_FOOT[6]=1;
                }else {
                    foot07.setSelected(false);
                    CHECK_FOOT[6]=0;
                }
                Log.e("ji", "click7");
            }
        });
        foot08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[7]==0){
                    foot08.setSelected(true);
                    CHECK_FOOT[7]=1;
                }else {
                    foot08.setSelected(false);
                    CHECK_FOOT[7]=0;
                }
                Log.e("ji", "click8");
            }
        });
        foot09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[8]==0){
                    foot09.setSelected(true);
                    CHECK_FOOT[8]=1;
                }else {
                    foot09.setSelected(false);
                    CHECK_FOOT[8]=0;
                }
                Log.e("ji", "click9");
            }
        });
        foot10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FOOT[9]==0){
                    foot10.setSelected(true);
                    CHECK_FOOT[9]=1;
                }else {
                    foot10.setSelected(false);
                    CHECK_FOOT[9]=0;
                }
                Log.e("ji", "click10");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_foot.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //세부 부위가 선택되어 있으면 BODY에 넣기
                if(foot01.isSelected()){
                    BODY.add(FOOT[0]);
                }
                if(foot02.isSelected()){
                    BODY.add(FOOT[1]);
                }
                if(foot03.isSelected()){
                    BODY.add(FOOT[2]);
                }
                if(foot04.isSelected()){
                    BODY.add(FOOT[3]);
                }
                if(foot05.isSelected()){
                    BODY.add(FOOT[4]);
                }
                if(foot06.isSelected()){
                    BODY.add(FOOT[5]);
                }
                if(foot07.isSelected()){
                    BODY.add(FOOT[6]);
                }
                if(foot08.isSelected()){
                    BODY.add(FOOT[7]);
                }
                if(foot09.isSelected()){
                    BODY.add(FOOT[8]);
                }
                if(foot10.isSelected()){
                    BODY.add(FOOT[9]);
                }


                //세부부위 string 변환해서 select_head에 넣기
                select_foot = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_foot.length==0){
                    new AlertDialog.Builder(SelectBody_foot.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_foot);
                for(int i = 0; i< select_foot.length; i++)
                    Log.e("jj", select_foot[i]);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);

                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_foot.this, SearchList.class);
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
