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

public class SelectBody_face extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_FACE =new int[6];
    String [] FACE ={"오른쪽 귀","왼쪽 귀","오른쪽 눈","왼쪽 눈","코","목"}; //얼굴 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_face; //선택한 팔 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_face);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton face01 = findViewById(R.id.face01) ;
        ImageButton face02 = findViewById(R.id.face02) ;
        ImageButton face03 = findViewById(R.id.face03) ;
        ImageButton face04 = findViewById(R.id.face04) ;
        ImageButton face05 = findViewById(R.id.face05) ;
        ImageButton face06 = findViewById(R.id.face06) ;





        //팔 세부 부위 select 유무 확인
        face01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[0]==0){
                    face01.setSelected(true);
                    CHECK_FACE[0]=1;
                }else {
                    face01.setSelected(false);
                    CHECK_FACE[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        face02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[1]==0){
                    face02.setSelected(true);
                    CHECK_FACE[1]=1;
                }else {
                    face02.setSelected(false);
                    CHECK_FACE[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        face03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[2]==0){
                    face03.setSelected(true);
                    CHECK_FACE[2]=1;
                }else {
                    face03.setSelected(false);
                    CHECK_FACE[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        face04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[3]==0){
                    face04.setSelected(true);
                    CHECK_FACE[3]=1;
                }else {
                    face04.setSelected(false);
                    CHECK_FACE[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        face05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[4]==0){
                    face05.setSelected(true);
                    CHECK_FACE[4]=1;
                }else {
                    face05.setSelected(false);
                    CHECK_FACE[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        face06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_FACE[5]==0){
                    face06.setSelected(true);
                    CHECK_FACE[5]=1;
                }else {
                    face06.setSelected(false);
                    CHECK_FACE[5]=0;
                }
                Log.e("ji", "click6");
            }
        });


        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_face.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //세부 부위가 선택되어 있으면 BODY에 넣기
                if(face01.isSelected()){
                    BODY.add(FACE[0]);
                }
                if(face02.isSelected()){
                    BODY.add(FACE[1]);
                }
                if(face03.isSelected()){
                    BODY.add(FACE[2]);
                }
                if(face04.isSelected()){
                    BODY.add(FACE[3]);
                }
                if(face05.isSelected()){
                    BODY.add(FACE[4]);
                }
                if(face06.isSelected()){
                    BODY.add(FACE[5]);
                }
                //세부부위 string 변환해서 select_head에 넣기
                select_face = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_face.length==0){
                    new AlertDialog.Builder(SelectBody_face.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts", select_face);
                for(int i = 0; i< select_face.length; i++)
                    Log.e("jj", select_face[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_face.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
