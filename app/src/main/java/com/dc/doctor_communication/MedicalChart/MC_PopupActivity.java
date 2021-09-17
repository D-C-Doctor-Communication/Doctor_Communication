package com.dc.doctor_communication.MedicalChart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.doctorcommunication.R;

import java.util.concurrent.atomic.AtomicReference;

public class MC_PopupActivity extends AppCompatActivity {

    //저장 버튼
    Button ok_btn;
    //x 버튼
    ImageView cancel_btn;
    //TimePicker
    TimePicker time_picker;
    //일정 이름
    EditText scheduleName;
    //장소 선택
    EditText location;
    //검사|진료 선택 버튼
    Button checkup_btn,treatment_btn;
    boolean isCheckupPressed,isTreatmentPressed;
    String buttonString="e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //모서리 둥글게
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mc_register_popup);

        //저장 | X버튼
        ok_btn = findViewById(R.id.ok_btn);
        cancel_btn = findViewById(R.id.cancel_button);
        //timepicker 설정
        time_picker = findViewById(R.id.time_picker);
        //12시간 기준
        time_picker.setIs24HourView(false);
        //검사|진료 선택 버튼
        checkup_btn = findViewById(R.id.checkup_btn);
        treatment_btn = findViewById(R.id.treatment_btn);
        // '장소'
        location = findViewById(R.id.location) ;
        // '일정이름'
        scheduleName = findViewById(R.id.scheduleName) ;



        //TimePicker
        //선택된 시간 저장을 위한 String 변수(시,분,오전|오후)
        String hour,minute,AmPm;
        //AMPM계산을 위한 int형 시간
        int nHour;
        //선택된 시간값 저장 (버전마다 timepicker에서 시간을 받아오는 메소드가 다름)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = time_picker.getHour() + "";
            minute = time_picker.getMinute() + "";
            nHour = time_picker.getHour();
        } else {
            hour = time_picker.getCurrentHour() + "";
            minute = time_picker.getCurrentMinute() + "";
            nHour = time_picker.getCurrentHour();
        }
        AmPm = getAmPm(nHour);


        scheduleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                check();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }
            @Override
            public void afterTextChanged(Editable s) {
                check();
            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                check();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }
            @Override
            public void afterTextChanged(Editable s) {
                check();
            }
        });


        // '검사 | 진료 ' 버튼
        //검사 버튼
        checkup_btn.setOnClickListener( v1 -> {
            checkup_btn.setBackgroundResource(R.drawable.mc_popup_left_btn_clicked);
            checkup_btn.setTextColor(Color.WHITE);
            treatment_btn.setBackgroundResource(R.drawable.mc_popup_right_btn);
            treatment_btn.setTextColor(Color.parseColor("#29000000"));
            buttonString= "검사";
            check();
        });
        //진료 버튼
        treatment_btn.setOnClickListener( v1 -> {
            checkup_btn.setBackgroundResource(R.drawable.mc_popup_left_btn);
            checkup_btn.setTextColor(Color.parseColor("#29000000"));
            treatment_btn.setBackgroundResource(R.drawable.mc_popup_right_btn_clicked);
            treatment_btn.setTextColor(Color.WHITE);
            buttonString = "진료";
            check();
        });






        //저장버을 눌렀을 때에만 Fragment_medicalChart로 입력값 전달
        ok_btn.setOnClickListener(v->{
            if(scheduleName.getText().toString().trim().length()>0 && location.getText().toString().trim().length()>0 && !buttonString.equals("e")){
                //인텐트 객체 생성
                Intent intent = new Intent();

                //시간
                intent.putExtra("selected_time", hour + ":" + minute + " " + AmPm);
                //장소
                String locationText = location.getText().toString();
                intent.putExtra("location", locationText);
                //이름
                String scheduleNameText = scheduleName.getText().toString();
                intent.putExtra("schedule_name", scheduleNameText);
                //종류
                intent.putExtra("selected_button", buttonString);

                // Activity 종료와 동시에 값이 저장된 intent 객체 전달
                setResult(RESULT_OK, intent);
                finish();
            }
            else{
                Toast.makeText(this,"입력 양식을 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        });

        //X버튼 눌렀을 경우 데이터 저장 없이 activity 종료
        cancel_btn.setOnClickListener(v->{
            finish();
        });

    }

    public void check(){
        if(scheduleName.getText().toString().trim().length()>0 && location.getText().toString().trim().length()>0 && !buttonString.equals("e")){
            ok_btn.setBackgroundResource(R.drawable.mc_button_clicked);
            ok_btn.setTextColor(Color.WHITE);
        } else {
            ok_btn.setBackgroundResource(R.drawable.mc_button_nonclicked);
            ok_btn.setTextColor(Color.WHITE);
        }
    }

    //PM,AM값 계산 메소드
    private String getAmPm(int hour) {
        if (hour <= 12)
            return "AM";
        else
            return "PM";
    }
}