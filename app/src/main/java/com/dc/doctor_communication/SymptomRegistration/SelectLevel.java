package com.dc.doctor_communication.SymptomRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.dc.doctor_communication.R;

public class SelectLevel extends AppCompatActivity {
    String symptom;
    int part;
    SeekBar level;
    TextView level_text1;
    TextView level_text2;
    int num=0; //선택한 통증 레벨(숫자)
    String []level_text={"통증이 없음","괜찮은 통증","조금 아픈 통증","웬만한 통증","괴로운 통증",
            "매우 괴로운 통증","극심한 통증","매우 극심한 통증","끔찍한 통증","참을 수 없는 통증","상상할 수 없는 통증"}; // 통증 레벨 설명
    StringBuilder change_level;
    String select_level;//선택한 통증 레벨(설명)
    String selected_body[]; //전 페이지 선택한 부위
    int repeat;

    public void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom"); //선택한 증상 받아오기
        part =intent.getExtras().getInt("part");
        selected_body = intent.getStringArrayExtra("bparts");
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        Log.e("backpart", String.valueOf(part));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);



        level_text1 = (TextView) findViewById(R.id.level_text1);
        level_text2 = (TextView) findViewById(R.id.level_text2);
        level = (SeekBar) findViewById(R.id.level_seekbar);

        ImageButton nextpage = (ImageButton) findViewById(R.id.nextpage);
        ImageButton backpage = (ImageButton) findViewById(R.id.backpage);

        //통증 정도 선택
        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                num = level.getProgress();
                update();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                num=level.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                num=level.getProgress();
            }
        });

        //다음페이지 이동
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLevel.this, SelectPattern.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("levelNm",num);
                intent.putExtra("repeat",repeat);

                startActivity(intent);
                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                finish();
            }
        });
        //전 페이지로 이동
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;


                switch (part)  //선택한 증상 부위 번호
                {
                    case 1: {  //머리
                        Log.e("intentL", "1번");
                        intent = new Intent(SelectLevel.this, SelectBody_head.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 2: {  //얼굴
                        Log.e("intentL", "2번");
                        intent = new Intent(SelectLevel.this, SelectBody_face.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 3: {  //팔
                        Log.e("intentL", "3번");
                        intent = new Intent(SelectLevel.this, SelectBody_arm.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 4: {  //다리
                        Log.e("intentL", "4번");
                        intent = new Intent(SelectLevel.this, SelectBody_leg.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 5: {  //등
                        Log.e("intentL", "5번");
                        intent = new Intent(SelectLevel.this, SelectBody_back.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 6: {  //허리
                        Log.e("intentL", "6번");
                        intent = new Intent(SelectLevel.this, SelectBody_waist.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 7: {  //가슴
                        Log.e("intentL", "7번");
                        intent = new Intent(SelectLevel.this, SelectBody_chest.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 8: {  //복부
                        Log.e("intentL", "8번");
                        intent = new Intent(SelectLevel.this, SelectBody_stomach.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 9: {  //엉덩이
                        Log.e("intentL", "9번");
                        intent = new Intent(SelectLevel.this, SelectBody_buttock.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 11: {  //전신
                        Log.e("intentL", "11번");
                        intent = new Intent(SelectLevel.this, SelectBody_body.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 12: {  //손
                        Log.e("intentL", "12번");
                        intent = new Intent(SelectLevel.this, SelectBody_hand.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                    case 13: {  //발
                        Log.e("intentL", "13번");
                        intent = new Intent(SelectLevel.this, SelectBody_foot.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        intent.putExtra("repeat",repeat);

                        startActivity(intent);
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                        break;
                    }
                }
                finish();
            }
        });

    }

    //선택한 통증 정도 따른 텍스트
    private void update() {
        change_level = new StringBuilder().append(level_text[num]);
        select_level=change_level.toString();
        level_text1.setText(select_level);
        level_text2.setText(new StringBuilder().append(num));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }

}
