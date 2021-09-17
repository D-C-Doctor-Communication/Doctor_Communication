package com.dc.doctor_communication.SymptomRegistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_buttock extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_BUTTOCK =new int[4];
    String [] BUTTOCK ={"오른쪽 중둔근","오른쪽 대둔근","왼쪽 중둔근","왼쪽 대둔근"}; //엉덩이 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_buttock; //선택한 엉덩이 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_buttock);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton buttock01 = findViewById(R.id.buttock01) ;
        ImageButton buttock02 = findViewById(R.id.buttock02) ;
        ImageButton buttock03 = findViewById(R.id.buttock03) ;
        ImageButton buttock04 = findViewById(R.id.buttock04) ;
        
        

        //엉덩이 세부 부위 select 유무 확인
        buttock01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BUTTOCK[0]==0){
                    buttock01.setSelected(true);
                    CHECK_BUTTOCK[0]=1;
                }else {
                    buttock01.setSelected(false);
                    CHECK_BUTTOCK[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        buttock02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BUTTOCK[1]==0){
                    buttock02.setSelected(true);
                    CHECK_BUTTOCK[1]=1;
                }else {
                    buttock02.setSelected(false);
                    CHECK_BUTTOCK[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        buttock03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BUTTOCK[2]==0){
                    buttock03.setSelected(true);
                    CHECK_BUTTOCK[2]=1;
                }else {
                    buttock03.setSelected(false);
                    CHECK_BUTTOCK[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        buttock04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_BUTTOCK[3]==0){
                    buttock04.setSelected(true);
                    CHECK_BUTTOCK[3]=1;
                }else {
                    buttock04.setSelected(false);
                    CHECK_BUTTOCK[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
       

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_buttock.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //엉덩이 세부 부위가 선택되어 있으면 BODY에 넣기
                if(buttock01.isSelected()){
                    BODY.add(BUTTOCK[0]);
                }
                if(buttock02.isSelected()){
                    BODY.add(BUTTOCK[1]);
                }
                if(buttock03.isSelected()){
                    BODY.add(BUTTOCK[2]);
                }
                if(buttock04.isSelected()){
                    BODY.add(BUTTOCK[3]);
                }


                //선택한 엉덩이 세부부위 string 변환해서 select_head에 넣기
                select_buttock = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_buttock.length==0){
                    new AlertDialog.Builder(SelectBody_buttock.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_buttock);
                for(int i = 0; i< select_buttock.length; i++)
                    Log.e("jj", select_buttock[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_buttock.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
