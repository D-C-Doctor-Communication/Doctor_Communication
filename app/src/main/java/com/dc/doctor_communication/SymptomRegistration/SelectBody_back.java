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

public class SelectBody_back extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_BACK =new int[7];
    String [] BACK ={"왼쪽 승모근","오른쪽 승모근","왼쪽 어깨","오른쪽 어깨","등 전체","왼쪽 허리","오른쪽 허리"}; //등 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_back; //선택한 머리 부위
    int repeat;
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_hand);
        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton back01 = findViewById(R.id.back01) ;
        ImageButton back02 = findViewById(R.id.back02) ;
        ImageButton back03 = findViewById(R.id.back03) ;
        ImageButton back04 = findViewById(R.id.back04) ;
        ImageButton back05 = findViewById(R.id.back05) ;
        ImageButton back06 = findViewById(R.id.back06) ;
        ImageButton back07 = findViewById(R.id.back07) ;


        //손 세부 부위 select 유무 확인
        back01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[0]==0){
                    back01.setSelected(true);
                    CHECK_BACK[0]=1;
                }else {
                    back01.setSelected(false);
                    CHECK_BACK[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        back02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[1]==0){
                    back02.setSelected(true);
                    CHECK_BACK[1]=1;
                }else {
                    back02.setSelected(false);
                    CHECK_BACK[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        back03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[2]==0){
                    back03.setSelected(true);
                    CHECK_BACK[2]=1;
                }else {
                    back03.setSelected(false);
                    CHECK_BACK[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        back04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[3]==0){
                    back04.setSelected(true);
                    CHECK_BACK[3]=1;
                }else {
                    back04.setSelected(false);
                    CHECK_BACK[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        back05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[4]==0){
                    back05.setSelected(true);
                    CHECK_BACK[4]=1;
                }else {
                    back05.setSelected(false);
                    CHECK_BACK[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        back06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[5]==0){
                    back06.setSelected(true);
                    CHECK_BACK[5]=1;
                }else {
                    back06.setSelected(false);
                    CHECK_BACK[5]=0;
                }
                Log.e("ji", "click6");
            }
        });
        back07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BACK[6]==0){
                    back07.setSelected(true);
                    CHECK_BACK[6]=1;
                }else {
                    back07.setSelected(false);
                    CHECK_BACK[6]=0;
                }
                Log.e("ji", "click7");
            }
        });


        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_back.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //손 세부 부위가 선택되어 있으면 BODY에 넣기
                if(back01.isSelected()){
                    BODY.add(BACK[0]);
                }
                if(back02.isSelected()){
                    BODY.add(BACK[1]);
                }
                if(back03.isSelected()){
                    BODY.add(BACK[2]);
                }
                if(back04.isSelected()){
                    BODY.add(BACK[3]);
                }
                if(back05.isSelected()){
                    BODY.add(BACK[4]);
                }
                if(back06.isSelected()){
                    BODY.add(BACK[5]);
                }
                if(back07.isSelected()){
                    BODY.add(BACK[6]);
                }

                //선택한 손 세부부위 string 변환해서 select_head에 넣기
                select_back = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_back.length==0){
                    new AlertDialog.Builder(SelectBody_back.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_back);
                for(int i = 0; i< select_back.length; i++)
                    Log.e("jj", select_back[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_back.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
