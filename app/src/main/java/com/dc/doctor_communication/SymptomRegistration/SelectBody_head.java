package com.dc.doctor_communication.SymptomRegistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.MainActivity;
import com.dc.doctor_communication.R;
import com.dc.doctor_communication.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectBody_head extends AppCompatActivity {
    String symptom;
    int part;
    int []CHECK_HEAD=new int[5];
    String []HEAD={"눈주위","이마","관자놀이","머리 전체","뒷머리"}; //머리 세부 부위
    List<String> BODY = new ArrayList<>();
    String []select_head; //선택한 머리 부위
    private FirebaseAuth firebaseAuth;
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
        setContentView(R.layout.select_body_head);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton head01 = findViewById(R.id.head01) ;
        ImageButton head02 = findViewById(R.id.head02) ;
        ImageButton head03 = findViewById(R.id.head03) ;
        ImageButton head04 = findViewById(R.id.head04) ;
        ImageButton head05 = findViewById(R.id.head05) ;

        //머리 세부 부위 select 유무 확인
        head01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[0]==0){
                    head01.setSelected(true);
                    CHECK_HEAD[0]=1;
                }else {
                    head01.setSelected(false);
                    CHECK_HEAD[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        head02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[1]==0){
                    head02.setSelected(true);
                    CHECK_HEAD[1]=1;
                }else {
                    head02.setSelected(false);
                    CHECK_HEAD[1]=0;
                }
                Log.e("ji", "click2");
            }
        });

        head03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[2]==0){
                    head03.setSelected(true);
                    CHECK_HEAD[2]=1;
                }else {
                    head03.setSelected(false);
                    CHECK_HEAD[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        head04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[3]==0){
                    head04.setSelected(true);
                    CHECK_HEAD[3]=1;
                }else {
                    head04.setSelected(false);
                    CHECK_HEAD[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        head05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[4]==0){
                    head05.setSelected(true);
                    CHECK_HEAD[4]=1;
                }else {
                    head05.setSelected(false);
                    CHECK_HEAD[4]=0;
                }
                Log.e("ji", "click5");
            }
        });

        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_head.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //머리 세부 부위가 선택되어 있으면 BODY에 넣기
                if(head01.isSelected()){
                    BODY.add(HEAD[0]);
                }
                if(head02.isSelected()){
                    BODY.add(HEAD[1]);
                }
                if(head03.isSelected()){
                    BODY.add(HEAD[2]);
                }
                if(head04.isSelected()){
                    BODY.add(HEAD[3]);
                }
                if(head05.isSelected()){
                    BODY.add(HEAD[4]);
                }

                //선택한 머리 세부부위 string 변환해서 select_head에 넣기
                select_head = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_head.length==0){
                    new AlertDialog.Builder(SelectBody_head.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts",select_head);
                for(int i=0; i<select_head.length; i++)
                    Log.e("jj", select_head[i]);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_head.this, SearchList.class);
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
