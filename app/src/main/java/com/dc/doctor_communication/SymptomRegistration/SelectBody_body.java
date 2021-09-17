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

public class SelectBody_body extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_WBODY =new int[1];
    String [] WBODY ={"전신"}; //세부 부위
    List<String> BODY = new ArrayList<>();
    String [] select_body; //선택한 세부 부위
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
        setContentView(R.layout.select_body_body);

        ImageButton nextpage = findViewById(R.id.nextpage) ;
        ImageButton backpage = findViewById(R.id.backpage) ;
        ImageButton body01 = findViewById(R.id.body01) ;

        //머리 세부 부위 select 유무 확인
        body01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WBODY[0]==0){
                    body01.setSelected(true);
                    CHECK_WBODY[0]=1;
                }else {
                    body01.setSelected(false);
                    CHECK_WBODY[0]=0;
                }
                Log.e("ji", "click1");
            }
        });


        //다음 페이지 버튼
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_body.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("repeat",repeat);

                //가슴슴 세부 부위가 선택되 있으면 BODY에 넣기
                if(body01.isSelected()){
                    BODY.add(WBODY[0]);
                }

                //선택한 가슴 세부부위 string 변환해서 select_head에 넣기
                select_body = BODY.toArray(new String[BODY.size()]);

                //선택 X 팝업 처리
                if(select_body.length==0){
                    new AlertDialog.Builder(SelectBody_body.this)
                            .setMessage("부위를 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
                intent.putExtra("bparts", select_body);
                for(int i = 0; i< select_body.length; i++)
                    Log.e("jj", select_body[i]);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });
        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_body.this, SearchList.class);
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
