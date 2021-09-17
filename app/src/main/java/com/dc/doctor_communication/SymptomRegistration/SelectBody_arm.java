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

public class SelectBody_arm extends AppCompatActivity {
    String symptom;
    int part;
    int []CHECK_ARM=new int[10];
    String [] ARM ={"왼팔 상박(앞)","오른팔 팔꿈치","왼팔 하박(앞)","왼팔 상박(뒤)","왼팔 팔꿈치","왼팔 하박(뒤)","오른팔 상박(앞)","오른팔 하박(앞)","오른팔 상박(뒤)","오른팔 하박(뒤)"}; //팔 세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_arm; //선택한 팔 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_arm);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton arm01 = findViewById(R.id.arm01) ;
        ImageButton arm02 = findViewById(R.id.arm02) ;
        ImageButton arm03 = findViewById(R.id.arm03) ;
        ImageButton arm04 = findViewById(R.id.arm04) ;
        ImageButton arm05 = findViewById(R.id.arm05) ;
        ImageButton arm06 = findViewById(R.id.arm06) ;
        ImageButton arm07 = findViewById(R.id.arm07) ;
        ImageButton arm08 = findViewById(R.id.arm08) ;
        ImageButton arm09 = findViewById(R.id.arm09) ;
        ImageButton arm10 = findViewById(R.id.arm10) ;




        //팔 세부 부위 select 유무 확인
        arm01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[0]==0){
                    arm01.setSelected(true);
                    CHECK_ARM[0]=1;
                }else {
                    arm01.setSelected(false);
                    CHECK_ARM[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        arm02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[1]==0){
                    arm02.setSelected(true);
                    CHECK_ARM[1]=1;
                }else {
                    arm02.setSelected(false);
                    CHECK_ARM[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        arm03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[2]==0){
                    arm03.setSelected(true);
                    CHECK_ARM[2]=1;
                }else {
                    arm03.setSelected(false);
                    CHECK_ARM[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        arm04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[3]==0){
                    arm04.setSelected(true);
                    CHECK_ARM[3]=1;
                }else {
                    arm04.setSelected(false);
                    CHECK_ARM[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        arm05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[4]==0){
                    arm05.setSelected(true);
                    CHECK_ARM[4]=1;
                }else {
                    arm05.setSelected(false);
                    CHECK_ARM[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        arm06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[5]==0){
                    arm06.setSelected(true);
                    CHECK_ARM[5]=1;
                }else {
                    arm06.setSelected(false);
                    CHECK_ARM[5]=0;
                }
                Log.e("ji", "click6");
            }
        });
        arm07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[6]==0){
                    arm07.setSelected(true);
                    CHECK_ARM[6]=1;
                }else {
                    arm07.setSelected(false);
                    CHECK_ARM[6]=0;
                }
                Log.e("ji", "click7");
            }
        });
        arm08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[7]==0){
                    arm08.setSelected(true);
                    CHECK_ARM[7]=1;
                }else {
                    arm08.setSelected(false);
                    CHECK_ARM[7]=0;
                }
                Log.e("ji", "click8");
            }
        });
        arm09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[8]==0){
                    arm09.setSelected(true);
                    CHECK_ARM[8]=1;
                }else {
                    arm09.setSelected(false);
                    CHECK_ARM[8]=0;
                }
                Log.e("ji", "click9");
            }
        });
        arm10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_ARM[9]==0){
                    arm10.setSelected(true);
                    CHECK_ARM[9]=1;
                }else {
                    arm10.setSelected(false);
                    CHECK_ARM[9]=0;
                }
                Log.e("ji", "click10");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_arm.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //세부 부위가 선택되어 있으면 BODY에 넣기
                if(arm01.isSelected()){
                    BODY.add(ARM[0]);
                }
                if(arm02.isSelected()){
                    BODY.add(ARM[1]);
                }
                if(arm03.isSelected()){
                    BODY.add(ARM[2]);
                }
                if(arm04.isSelected()){
                    BODY.add(ARM[3]);
                }
                if(arm05.isSelected()){
                    BODY.add(ARM[4]);
                }
                if(arm06.isSelected()){
                    BODY.add(ARM[5]);
                }
                if(arm07.isSelected()){
                    BODY.add(ARM[6]);
                }
                if(arm08.isSelected()){
                    BODY.add(ARM[7]);
                }
                if(arm09.isSelected()){
                    BODY.add(ARM[8]);
                }
                if(arm10.isSelected()){
                    BODY.add(ARM[9]);
                }


                //세부부위 string 변환해서 select_head에 넣기
                select_arm = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_arm.length==0){
                    new AlertDialog.Builder(SelectBody_arm.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_arm);
                for(int i = 0; i< select_arm.length; i++)
                    Log.e("jj", select_arm[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_arm.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
