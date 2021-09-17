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

public class SelectBody_hand extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_HAND =new int[6];
    String [] HAND ={"왼손 손등","왼손 손목","왼손 손가락","오른손 손등","오른손 손목","오른손 손가락"}; //손 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_hand; //선택한 머리 부위
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
        setContentView(R.layout.select_body_hand);
        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton hand01 = findViewById(R.id.hand03) ;
        ImageButton hand02 = findViewById(R.id.hand04) ;
        ImageButton hand03 = findViewById(R.id.hand05) ;
        ImageButton hand04 = findViewById(R.id.hand08) ;
        ImageButton hand05 = findViewById(R.id.hand09) ;
        ImageButton hand06 = findViewById(R.id.hand10) ;

        //손 세부 부위 select 유무 확인
        hand01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[0]==0){
                    hand01.setSelected(true);
                    CHECK_HAND[0]=1;
                }else {
                    hand01.setSelected(false);
                    CHECK_HAND[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        hand02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[1]==0){
                    hand02.setSelected(true);
                    CHECK_HAND[1]=1;
                }else {
                    hand02.setSelected(false);
                    CHECK_HAND[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        hand03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[2]==0){
                    hand03.setSelected(true);
                    CHECK_HAND[2]=1;
                }else {
                    hand03.setSelected(false);
                    CHECK_HAND[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        hand04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[3]==0){
                    hand04.setSelected(true);
                    CHECK_HAND[3]=1;
                }else {
                    hand04.setSelected(false);
                    CHECK_HAND[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        hand05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[4]==0){
                    hand05.setSelected(true);
                    CHECK_HAND[4]=1;
                }else {
                    hand05.setSelected(false);
                    CHECK_HAND[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        hand06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HAND[5]==0){
                    hand06.setSelected(true);
                    CHECK_HAND[5]=1;
                }else {
                    hand06.setSelected(false);
                    CHECK_HAND[5]=0;
                }
                Log.e("ji", "click6");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_hand.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //손 세부 부위가 선택되어 있으면 BODY에 넣기
                if(hand01.isSelected()){
                    BODY.add(HAND[0]);
                }
                if(hand02.isSelected()){
                    BODY.add(HAND[1]);
                }
                if(hand03.isSelected()){
                    BODY.add(HAND[2]);
                }
                if(hand04.isSelected()){
                    BODY.add(HAND[3]);
                }
                if(hand05.isSelected()){
                    BODY.add(HAND[4]);
                }
                if(hand06.isSelected()){
                    BODY.add(HAND[5]);
                }


                //선택한 손 세부부위 string 변환해서 select_head에 넣기
                select_hand = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_hand.length==0){
                    new AlertDialog.Builder(SelectBody_hand.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_hand);
                for(int i = 0; i< select_hand.length; i++)
                    Log.e("jj", select_hand[i]);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });

        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_hand.this, SearchList.class);
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
