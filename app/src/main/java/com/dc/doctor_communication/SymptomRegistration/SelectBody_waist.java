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

public class SelectBody_waist extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_WAIST =new int[5];
    String []WAIST={"왼쪽 옆구리","허리 중앙","오른쪽 옆구리","허리 아래","꼬리뼈"}; //허리 세부 부위
    List<String> BODY = new ArrayList<String>();
    String [] select_waist; //선택한 허리 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_waist);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton waist01 = (ImageButton)findViewById(R.id.waist01) ;
        ImageButton waist02 = (ImageButton)findViewById(R.id.waist02) ;
        ImageButton waist03 = (ImageButton)findViewById(R.id.waist03) ;
        ImageButton waist04 = (ImageButton)findViewById(R.id.waist04) ;
        ImageButton waist05 = (ImageButton)findViewById(R.id.waist05) ;

        //허리 세부 부위 select 유무 확인
        waist01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[0]==0){
                    waist01.setSelected(true);
                    CHECK_WAIST[0]=1;
                }else {
                    waist01.setSelected(false);
                    CHECK_WAIST[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        waist02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[1]==0){
                    waist02.setSelected(true);
                    CHECK_WAIST[1]=1;
                }else {
                    waist02.setSelected(false);
                    CHECK_WAIST[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        waist03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[2]==0){
                    waist03.setSelected(true);
                    CHECK_WAIST[2]=1;
                }else {
                    waist03.setSelected(false);
                    CHECK_WAIST[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        waist04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[3]==0){
                    waist04.setSelected(true);
                    CHECK_WAIST[3]=1;
                }else {
                    waist04.setSelected(false);
                    CHECK_WAIST[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        waist05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[4]==0){
                    waist05.setSelected(true);
                    CHECK_WAIST[4]=1;
                }else {
                    waist05.setSelected(false);
                    CHECK_WAIST[4]=0;
                }
                Log.e("ji", "click5");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_waist.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //허리 세부 부위가 선택되어 있으면 BODY에 넣기
                if(waist01.isSelected()){
                    BODY.add(WAIST[0]);
                }
                if(waist02.isSelected()){
                    BODY.add(WAIST[1]);
                }
                if(waist03.isSelected()){
                    BODY.add(WAIST[2]);
                }
                if(waist04.isSelected()){
                    BODY.add(WAIST[3]);
                }
                if(waist05.isSelected()){
                    BODY.add(WAIST[4]);
                }

                //선택한 허리 세부부위 string 변환해서 select_waist 넣기
                select_waist = BODY.toArray(new String[BODY.size()]);
                //선택 X 팝업 처리
                if(select_waist.length==0){
                    new AlertDialog.Builder(SelectBody_waist.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_waist);
                for(int i = 0; i< select_waist.length; i++)
                    Log.e("jj", select_waist[i]);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_waist.this,SearchList.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
