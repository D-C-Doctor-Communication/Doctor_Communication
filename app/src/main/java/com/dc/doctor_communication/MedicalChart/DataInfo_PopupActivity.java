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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.w3c.dom.Text;

import java.util.List;

public class DataInfo_PopupActivity extends AppCompatActivity {

    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("users");
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    static String uid = user.getUid();

    static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("myapp", "팝업 실행됨");

        //투명배경
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mc_info_popup);

        //window.setLayout((metric.widthPixels * widthInPercent).toInt(), (metric.heightPixels * heightInPercent).toInt())
        //getWindow().setLayout((int)(metrics.widthPixels*WidthInPercent),(int)(metrics.heightPixels*HeightInPercent));

        LinearLayout container = findViewById(R.id.container);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");
        //for문으로 선택된 날짜에 대한 데이터 개수 구할것.

        View view = LayoutInflater.from(this).inflate(R.layout.mc_info_popup_item, container, false);

        ImageView image = view.findViewById(R.id.image);
        TextView title_symp = view.findViewById(R.id.title_symp);
        TextView part = view.findViewById(R.id.part);
        TextView degree = view.findViewById(R.id.degree);
        TextView condition = view.findViewById(R.id.condition);
        TextView situation = view.findViewById(R.id.situation);
        TextView with = view.findViewById(R.id.with);

        for (int j = 0; j < 5; j++) {

            myRef.child(uid).child("date").child(selectedDate).child(String.valueOf(j)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String get_symptom = snapshot.child("symptom").getValue(String.class);
                    String get_part = snapshot.child("part").getValue(String.class);
                    String get_painLevel = snapshot.child("painLevel").getValue(String.class);
                    String get_characteristics = snapshot.child("pain_characteristics").getValue(String.class);
                    String get_situation = snapshot.child("pain_situation").getValue(String.class);
                    String get_add = snapshot.child("additional").getValue(String.class);

                    if (!(get_symptom.equals("e"))) {
                        Log.d("cnt_symptom", get_symptom);
                        String painLevelText = "";

                        switch (Integer.parseInt(get_painLevel)) {
                            case 0:
                                painLevelText = "통증이 없음";
                                break;
                            case 1:
                                painLevelText = "괜찮은 통증";
                                break;
                            case 2:
                                painLevelText = "조금 아픈 통증";
                                break;
                            case 3:
                                painLevelText = "웬만한 통증";
                                break;
                            case 4:
                                painLevelText = "괴로운 통증";
                                break;
                            case 5:
                                painLevelText = "매우 괴로운 통증";
                                break;
                            case 6:
                                painLevelText = "극심한 통증";
                                break;
                            case 7:
                                painLevelText = "매우 극심한 통증";
                                break;
                            case 8:
                                painLevelText = "끔찍한 통증";
                                break;
                            case 9:
                                painLevelText = "참을 수 없는 통증";
                                break;
                            case 10:
                                painLevelText = "상상할 수 없는 통증";
                                break;
                            default:
                                painLevelText = "알 수 없음";
                        }
                        switch (get_symptom) {
                            case "두통":
                                image.setImageResource(R.drawable.area_head_line);
                                break;
                            case "기침": case "인후통": case "콧물": case "귀 통증": case "이명": case "눈물": case "코피": case "객혈": case "가래":
                                image.setImageResource(R.drawable.area_face_line_off);
                                break;
                            case "팔꿈치 통증":
                                image.setImageResource(R.drawable.area_arm_line_off);
                                break;
                            case "무릎 통증":
                                image.setImageResource(R.drawable.area_leg_line_off);
                                break;
                            case "요통":
                                image.setImageResource(R.drawable.area_waist_line_off);
                                break;
                            case "흉통":
                                image.setImageResource(R.drawable.area_chest_line_off);
                                break;
                            case "복통": case "속 쓰림": case "소화불량":
                                image.setImageResource(R.drawable.area_stomach_line_on_01);
                                break;
                            case "엉덩이 통증":
                                image.setImageResource(R.drawable.area_buttock_line_off);
                                break;
                            case "발열": case "피로": case "호흡곤란": case "떨림": case "근육 경련": case "부종": case "가려움":
                                image.setImageResource(R.drawable.area_face_body_on);
                                break;
                            case "손목 통증":
                                image.setImageResource(R.drawable.area_hand_line_01_off);
                                break;
                            case "발목 통증":
                                image.setImageResource(R.drawable.area_foot_line_off);
                                break;
                            default:
                        }
                        title_symp.setText(get_symptom);
                        part.setText(get_part);
                        degree.setText(painLevelText);
                        condition.setText(get_characteristics);
                        situation.setText(get_situation);
                        if (get_add.equals("e")) with.setText("해당없음");
                        else with.setText(get_add);

                        if (view.getParent() != null)
                            ((ViewGroup) view.getParent()).removeView(view);
                        container.addView(view);

                        //container.addView(view);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
        //[FIREBASE] for문으로 기록된 증상있는만큼 layout inflate
//        Log.d("cnt++out", String.valueOf(Symptom2.getCnt()));
//        for(int i=0;i<Symptom2.getCnt();i++) {
//            Log.d("cnt++out", String.valueOf(Symptom2.getCnt()));
//            View view = LayoutInflater.from(this).inflate(R.layout.mc_info_popup_item, container, false);
//
//            ImageView image = view.findViewById(R.id.image);
//            TextView title_symp = view.findViewById(R.id.title_symp);
//            TextView part = view.findViewById(R.id.part);
//            TextView degree = view.findViewById(R.id.degree);
//            TextView condition = view.findViewById(R.id.condition);
//            TextView situation = view.findViewById(R.id.situation);
//            TextView with = view.findViewById(R.id.with);
//
//            myRef.child(uid).child("date").child(selectedDate).child(String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String get_symptom = snapshot.child("symptom").getValue(String.class);
//                    String get_part = snapshot.child("part").getValue(String.class);
//                    String get_painLevel = snapshot.child("painLevel").getValue(String.class);
//                    String get_characteristics = snapshot.child("pain_characteristics").getValue(String.class);
//                    String get_situation = snapshot.child("pain_situation").getValue(String.class);
//                    String get_add = snapshot.child("additional").getValue(String.class);
//
//                    String painLevelText ="";
//                    if(!(get_painLevel.equals("e")) && get_painLevel != null) {
//                            switch (Integer.parseInt(get_painLevel)) {
//                                case 0:
//                                    painLevelText = "통증이 없음";
//                                    break;
//                                case 1:
//                                    painLevelText = "괜찮은 통증";
//                                    break;
//                                case 2:
//                                    painLevelText = "조금 아픈 통증";
//                                    break;
//                                case 3:
//                                    painLevelText = "웬만한 통증";
//                                    break;
//                                case 4:
//                                    painLevelText = "괴로운 통증";
//                                    break;
//                                case 5:
//                                    painLevelText = "매우 괴로운 통증";
//                                    break;
//                                case 6:
//                                    painLevelText = "극심한 통증";
//                                    break;
//                                case 7:
//                                    painLevelText = "매우 극심한 통증";
//                                    break;
//                                case 8:
//                                    painLevelText = "끔찍한 통증";
//                                    break;
//                                case 9:
//                                    painLevelText = "참을 수 없는 통증";
//                                    break;
//                                case 10:
//                                    painLevelText = "상상할 수 없는 통증";
//                                    break;
//                                default:
//                                    painLevelText = "알 수 없음";
//                            }
//
//                    }
//
//
//
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) { }
//            });
//
//
//        }
//    }

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