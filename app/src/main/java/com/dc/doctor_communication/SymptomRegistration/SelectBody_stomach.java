package com.dc.doctor_communication.SymptomRegistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_stomach extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_STOMACH =new int[9];
    String [] STOMACH ={"오른쪽 위 복부","명치","왼쪽 위 복부","오른쪽 복부","배꼽 부근","왼쪽 복부","오른쪽 아래 복부","왼쪽 아래 복부","아래쪽 복부"}; //복부 세부 부위
    List<String> BODY = new ArrayList<String>();
    String [] select_stomach; //선택한 복부 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_stomach);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton stomach01 = (ImageButton)findViewById(R.id.stomach01) ;
        ImageButton stomach02 = (ImageButton)findViewById(R.id.stomach02) ;
        ImageButton stomach03 = (ImageButton)findViewById(R.id.stomach03) ;
        ImageButton stomach04 = (ImageButton)findViewById(R.id.stomach04) ;
        ImageButton stomach05 = (ImageButton)findViewById(R.id.stomach05) ;
        ImageButton stomach06 = (ImageButton)findViewById(R.id.stomach06) ;
        ImageButton stomach07 = (ImageButton)findViewById(R.id.stomach07) ;
        ImageButton stomach08 = (ImageButton)findViewById(R.id.stomach08) ;
        ImageButton stomach09 = (ImageButton)findViewById(R.id.stomach09);

        //복부 세부 부위 select 유무 확인
        stomach01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[0]==0){
                    stomach01.setSelected(true);
                    CHECK_STOMACH[0]=1;
                }else {
                    stomach01.setSelected(false);
                    CHECK_STOMACH[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        stomach02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[1]==0){
                    stomach02.setSelected(true);
                    CHECK_STOMACH[1]=1;
                }else {
                    stomach02.setSelected(false);
                    CHECK_STOMACH[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        stomach03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[2]==0){
                    stomach03.setSelected(true);
                    CHECK_STOMACH[2]=1;
                }else {
                    stomach03.setSelected(false);
                    CHECK_STOMACH[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        stomach04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[3]==0){
                    stomach04.setSelected(true);
                    CHECK_STOMACH[3]=1;
                }else {
                    stomach04.setSelected(false);
                    CHECK_STOMACH[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        stomach05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[4]==0){
                    stomach05.setSelected(true);
                    CHECK_STOMACH[4]=1;
                }else {
                    stomach05.setSelected(false);
                    CHECK_STOMACH[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        stomach06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[5]==0){
                    stomach06.setSelected(true);
                    CHECK_STOMACH[5]=1;
                }else {
                    stomach06.setSelected(false);
                    CHECK_STOMACH[5]=0;
                }
                Log.e("ji", "click2");
            }
        });
        stomach07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[6]==0){
                    stomach07.setSelected(true);
                    CHECK_STOMACH[6]=1;
                }else {
                    stomach07.setSelected(false);
                    CHECK_STOMACH[6]=0;
                }
                Log.e("ji", "click3");
            }
        });
        stomach08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[7]==0){
                    stomach08.setSelected(true);
                    CHECK_STOMACH[7]=1;
                }else {
                    stomach08.setSelected(false);
                    CHECK_STOMACH[7]=0;
                }
                Log.e("ji", "click4");
            }
        });
        stomach09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[8]==0){
                    stomach09.setSelected(true);
                    CHECK_STOMACH[8]=1;
                }else {
                    stomach09.setSelected(false);
                    CHECK_STOMACH[8]=0;
                }
                Log.e("ji", "click5");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_stomach.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //복부 세부 부위가 선택되어 있으면 BODY에 넣기
                if(stomach01.isSelected()){
                    BODY.add(STOMACH[0]);
                }
                if(stomach02.isSelected()){
                    BODY.add(STOMACH[1]);
                }
                if(stomach03.isSelected()){
                    BODY.add(STOMACH[2]);
                }
                if(stomach04.isSelected()){
                    BODY.add(STOMACH[3]);
                }
                if(stomach05.isSelected()){
                    BODY.add(STOMACH[4]);
                }
                if(stomach06.isSelected()){
                    BODY.add(STOMACH[5]);
                }
                if(stomach07.isSelected()){
                    BODY.add(STOMACH[6]);
                }
                if(stomach08.isSelected()){
                    BODY.add(STOMACH[7]);
                }
                if(stomach09.isSelected()){
                    BODY.add(STOMACH[8]);
                }

                //선택한 복부 세부부위 string 변환해서 select_stomach 넣기
                select_stomach = BODY.toArray(new String[BODY.size()]);
                //선택 X 팝업 처리
                if(select_stomach.length==0){
                    new AlertDialog.Builder(SelectBody_stomach.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_stomach);
                for(int i = 0; i< select_stomach.length; i++)
                    Log.e("jj", select_stomach[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
         //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_stomach.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
