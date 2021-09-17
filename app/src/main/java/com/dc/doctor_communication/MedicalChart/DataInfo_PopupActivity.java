package com.dc.doctor_communication.MedicalChart;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.DataManagement.Person1;
import com.dc.doctor_communication.DataManagement.Symptom2;
import com.dc.doctor_communication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class DataInfo_PopupActivity extends AppCompatActivity {

    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("users");
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    static String uid = user.getUid();
    static int cnt = 0;

    static String painLevelText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("myapp","팝업 실행됨");

        //투명배경
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mc_info_popup);

        //window.setLayout((metric.widthPixels * widthInPercent).toInt(), (metric.heightPixels * heightInPercent).toInt())
        //getWindow().setLayout((int)(metrics.widthPixels*WidthInPercent),(int)(metrics.heightPixels*HeightInPercent));

        LinearLayout container =findViewById(R.id.container);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");

        cnt=0;
        Log.d("select", selectedDate);
        //for문으로 선택된 날짜에 대한 데이터 개수 구할것.
        for(int j=0; j<5; j++){
            Log.d("myapp","for1-"+j);
                myRef.child(uid).child("date").child(selectedDate).child(String.valueOf(j)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Symptom2 appointments = snapshot.getValue(Symptom2.class);
                    Log.d("getSym", appointments.getSymptom());
                    if(!(appointments.getSymptom().equals("e"))){
                        cnt++;
                        Log.d("cnt1",cnt+"");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }


        //[FIREBASE] for문으로 기록된 증상있는만큼 layout inflate
        for(int i=0;i<=cnt+1;i++) {
            Log.d("cnt2",cnt+"");
            Log.d("myapp","for2-"+i);
            View view = LayoutInflater.from(this).inflate(R.layout.mc_info_popup_item, container, false);

            //ImageView image = view.findViewById(R.id.image);
            TextView title_symp = view.findViewById(R.id.title_symp);
            TextView part = view.findViewById(R.id.part);
            TextView degree = view.findViewById(R.id.degree);
            TextView condition = view.findViewById(R.id.condition);
            TextView situation = view.findViewById(R.id.situation);
            TextView with = view.findViewById(R.id.with);
            ImageView right_arrow = view.findViewById(R.id.right_arrow);
            myRef.child(uid).child("date").child(selectedDate).child(String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(cnt==0){
                        makeToast();
                    }
                    Symptom2 popup = snapshot.getValue(Symptom2.class);
                    if(!(String.valueOf(popup.getPainLevel()).equals("e"))) {
                        switch (Integer.parseInt(popup.getPainLevel())){
                            case 0 : painLevelText = "통증이 없음"; break;
                            case 1 : painLevelText = "괜찮은 통증"; break;
                            case 2 : painLevelText = "조금 아픈 통증"; break;
                            case 3 : painLevelText = "웬만한 통증"; break;
                            case 4 : painLevelText = "괴로운 통증"; break;
                            case 5 : painLevelText = "매우 괴로운 통증"; break;
                            case 6 : painLevelText = "극심한 통증"; break;
                            case 7 : painLevelText = "매우 극심한 통증"; break;
                            case 8 : painLevelText = "끔찍한 통증"; break;
                            case 9 : painLevelText = "참을 수 없는 통증"; break;
                            case 10 : painLevelText = "상상할 수 없는 통증"; break;
                            default: painLevelText = "알 수 없음";
                        }
                        if(cnt<=1){
                            right_arrow.setVisibility(View.INVISIBLE);
                        }

                        //image.setImageResource(R.drawable.icon_logo);
                        title_symp.setText(popup.getSymptom());
                        part.setText(popup.getPart());
                        degree.setText(painLevelText);
                        condition.setText(popup.getPain_characteristics());
                        situation.setText(popup.getPain_situation());
                        if(popup.getAdditional().equals("e")) with.setText("해당없음");
                        else with.setText(popup.getAdditional());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
            container.addView(view);

        }


    }
    public void makeToast(){
        Toast.makeText(this,"기록된 증상이 없습니다.",Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            finish();
            return false;
        }
        else {
            finish();
            return true;
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }

}