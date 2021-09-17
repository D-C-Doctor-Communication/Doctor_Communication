package com.dc.doctor_communication.HomeScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dc.doctor_communication.ConditionAnalysis.Fragment_conditionAnalysis;
import com.dc.doctor_communication.DataManagement.Person1;
import com.dc.doctor_communication.DataManagement.Symptom2;
import com.dc.doctor_communication.DoctorMeeting.MeetingDoc;
import com.dc.doctor_communication.HomeScreen.HomeListViewAdapter;
import com.dc.doctor_communication.MainActivity;
import com.dc.doctor_communication.R;
import com.dc.doctor_communication.Settings.SettingActivity;
import com.dc.doctor_communication.SymptomRegistration.Search;
import com.dc.doctor_communication.SymptomRegistration.SearchList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;


public class Fragment_home extends Fragment {
    int count = -1;

    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("users");
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    static String uid = user.getUid();

    //데이터가 있는 날짜에 점찍기
    static boolean isDataExist = false;
    static String fire_date="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","home탭 열림");
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String get_name = snapshot.child("name").getValue(String.class);
                TextView helloUser = view.findViewById(R.id.user_name);
                helloUser.setText(get_name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //사용자 이름 받아오기


//세팅


        //카드 - 증상등록 버튼
        Button btn_addSymptom = (Button)view.findViewById(R.id.btn_addSymptom);
        //카드 - 의사와의 만남 버튼
        Button btn_meetingDoc = (Button)view.findViewById(R.id.btn_meetingDoc);
        //카드 - 녹음하기 버튼
        Button btn_recording = (Button)view.findViewById(R.id.btn_recording);
        //주간캘린더 - 각 요일별 날짜 카드뷰
        CardView[] wCalender = new CardView[7];
        wCalender[0] = view.findViewById(R.id.wCalender_sun); //일요일
        wCalender[1] = view.findViewById(R.id.wCalender_mon); //월요일
        wCalender[2] = view.findViewById(R.id.wCalender_tue); //화요일
        wCalender[3] = view.findViewById(R.id.wCalender_wed); //수요일
        wCalender[4] = view.findViewById(R.id.wCalender_thu); //목요일
        wCalender[5] = view.findViewById(R.id.wCalender_fri); //금요일
        wCalender[6] = view.findViewById(R.id.wCalender_sat); //토요일

//카드1 - 증상등록으로 이동
        btn_addSymptom.setOnClickListener(v -> { //람다형식 사용 ~ new Button.OnClickListener()와 같은 기능
            Intent addSymptom = new Intent(getContext(), Search.class);
            addSymptom.putExtra("count",count);
            startActivity(addSymptom);
        });

//카드2 - 의사와의 만남으로 이동
        btn_meetingDoc.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MeetingDoc.class);
            startActivity(intent);
        });

//카드3 - 녹음하기 팝업 띄움
        btn_recording.setOnClickListener(v -> {

            InfoDialog infoDialog = new InfoDialog();
            infoDialog.showDialog(getActivity());

        });




//주간 캘린더 - 각 날짜에 맞도록 텍스트 주마다 변경

        TextView ymTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        WeekCalendar weekCalendar = new WeekCalendar();
        Date todayDate = new Date();
        //점찍기
        weekCalendar.setWeekCalenderDate(view,todayDate,ymTextView,wDate);
        //오늘날짜 색깔지정 (클릭한 날짜 색깔지정)
        WeekCalendar.setCardColor(todayDate.getDay(),wCalender);

//ListView
        ListView listView = (ListView)view.findViewById(R.id.home_listView);
        //오늘로 기본 리스트 보여짐
        WeekCalendar.createDataListToday(ymTextView,wDate,listView);

        //각 날짜를 클릭했을 때 날짜와 일치하는 데이터 불러오기
        wCalender[0].setOnClickListener(v -> { //일요일
            Log.d("myapp","일요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,0,listView);
            WeekCalendar.setCardColor(0,wCalender);
        });
        wCalender[1].setOnClickListener(v -> {
            Log.d("myapp","월요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,1,listView);
            WeekCalendar.setCardColor(1,wCalender);
        });
        wCalender[2].setOnClickListener(v -> {
            Log.d("myapp","화요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,2,listView);
            wCalender[2].setCardBackgroundColor(Color.parseColor("#0078ff"));
            WeekCalendar.setCardColor(2,wCalender);
        });
        wCalender[3].setOnClickListener(v -> {
            Log.d("myapp","수요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,3,listView);
            wCalender[3].setCardBackgroundColor(Color.parseColor("#0078ff"));
            WeekCalendar.setCardColor(3,wCalender);
        });
        wCalender[4].setOnClickListener(v -> {
            Log.d("myapp","목요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,4,listView);
            wCalender[4].setCardBackgroundColor(Color.parseColor("#0078ff"));
            WeekCalendar.setCardColor(4,wCalender);
        });
        wCalender[5].setOnClickListener(v -> {
            Log.d("myapp","금요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,5,listView);
            wCalender[5].setCardBackgroundColor(Color.parseColor("#0078ff"));
            WeekCalendar.setCardColor(5,wCalender);
        });
        wCalender[6].setOnClickListener(v -> {
            Log.d("myapp","토요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,6,listView);
            wCalender[6].setCardBackgroundColor(Color.parseColor("#0078ff"));
            WeekCalendar.setCardColor(6,wCalender);
        });

        return view;
    }



    static class WeekCalendar{
        static void createDataListToday(TextView ymTextView, TextView[] wDate, ListView listView){
            Calendar calendar = Calendar.getInstance();
            createDataList(ymTextView,wDate,calendar.get(Calendar.DAY_OF_WEEK)-1,listView);
        }
        static void setCardColor(int index, CardView[] wCalender){
            wCalender[index].setCardBackgroundColor(Color.parseColor("#A1C5EE"));
            for(int i=0;i<7;i++){
                if(i==index) continue;
                wCalender[i].setCardBackgroundColor(Color.parseColor("#0a000000"));
            }
        }
        static void createDataList(TextView ymTextView, TextView[] wDate, int index, ListView listView){
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+""+ymTextView.getText().toString().substring(6,8)+""+wDate[index].getText().toString();

            //listView 참조 및 Adapter 연결
            HomeListViewAdapter adapter2 = new HomeListViewAdapter();
            //Adapter 지정
            listView.setAdapter(adapter2);

            for(int i=0; i<5; i++){
                Log.d("fire_j", String.valueOf(i));
                myRef.child(uid).child("date").child(clickedDate).child(String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String get_symptom = snapshot.child("symptom").getValue(String.class);
                        String get_part = snapshot.child("part").getValue(String.class);
                        String get_painLevel = snapshot.child("painLevel").getValue(String.class);
                        String get_characteristics = snapshot.child("pain_characteristics").getValue(String.class);
                        String get_situation = snapshot.child("pain_situation").getValue(String.class);

                        Log.d("get_fire", get_symptom+","+get_part+","+get_painLevel+","+get_characteristics+","+get_situation);
                        if(!get_part.equals("e")) {
                            Log.d("fire_in", get_symptom);
                            switch (get_symptom){
                                case "두통":
                                    Log.d("ee",get_part);
                                    //   adapter2.addItem(Person1.symptom[0].getPart(),R.drawable.img_pain_sym1,Integer.parseInt(Person1.symptom[0].getPain_level()),Person1.symptom[0].getPain_characteristics(),Person1.symptom[0].getPain_situation());

                                    adapter2.addItem(get_part, R.drawable.area_head_line, Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "기침": case "인후통": case "콧물": case "귀 통증": case "이명": case "눈물": case "코피": case "객혈": case "가래":
                                    adapter2.addItem(get_part, R.drawable.area_face_line_off, Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "팔꿈치 통증":
                                    adapter2.addItem(get_part, R.drawable.area_arm_line_off , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "무릎 통증":
                                    adapter2.addItem(get_part, R.drawable.area_leg_line_off, Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "요통":
                                    adapter2.addItem(get_part, R.drawable.area_waist_line_off , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "흉통":
                                    adapter2.addItem(get_part, R.drawable.area_chest_line_off , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "복통": case "속 쓰림": case "소화불량":
                                    adapter2.addItem(get_part, R.drawable.area_stomach_line_on_01 , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "엉덩이 통증":
                                    adapter2.addItem(get_part, R.drawable.area_buttock_line_off , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "발열": case "피로": case "호흡곤란": case "떨림": case "근육 경련": case "부종": case "가려움":
                                    adapter2.addItem(get_part, R.drawable.area_head_line, Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "손목 통증":
                                    adapter2.addItem(get_part, R.drawable.area_hand_line_01_off , Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                case "발목 통증":
                                    adapter2.addItem(get_part, R.drawable.area_foot_line_off, Integer.parseInt(get_painLevel),get_characteristics, get_situation);
                                    break;
                                default:
                            }
                            adapter2.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }

            adapter2.notifyDataSetChanged();
            Log.d("myapp","Adapter added");
        }

        @SuppressLint("SetTextI18n")
        void setWeekCalenderDate(View view, Date date, TextView ymTextView, TextView[] wDate){ //주간캘린더 날짜변경 메소드
            firebaseAuth =  FirebaseAuth.getInstance();
            //각 요일의 점
            ImageView[] weekCalendarDot = new ImageView[]{
                    view.findViewById(R.id.sun_dot),
                    view.findViewById(R.id.mon_dot),
                    view.findViewById(R.id.tue_dot),
                    view.findViewById(R.id.wed_dot),
                    view.findViewById(R.id.thu_dot),
                    view.findViewById(R.id.fri_dot),
                    view.findViewById(R.id.sat_dot),
            };

            Log.d("mytag","setWeekCalenderDate 과정 통과");
            //날짜 형식 지정
            SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //한국 기준 시간 사용
            todaySdf.format(date); //한국 시간 적용

            //시작 날짜를 일요일로 고정
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1)));

            //0000년 00월 텍스트 적용
            ymTextView.setText(todaySdf.format(cal.getTime()).substring(0,4)+"년 "+todaySdf.format(cal.getTime()).substring(5,7)+"월");

            for ( int i = 0; i < 7; i++ ) {
                //00일 텍스트 적용
                wDate[i].setText(todaySdf.format(cal.getTime()).substring(8));
                cal.add(Calendar.DAY_OF_MONTH, 1);

                String monthValue;
                if(cal.get(Calendar.MONTH)<10) monthValue = "0"+cal.get(Calendar.MONTH);
                else monthValue = cal.get(Calendar.MONTH)+"";
                String dayValue;
                if(cal.get(Calendar.DAY_OF_MONTH)<10) dayValue = "0"+cal.get(Calendar.DAY_OF_MONTH);
                else dayValue = cal.get(Calendar.DAY_OF_MONTH)+"";
                //밑에 로그부분대로 파이어베이스에 넣으면 됩니다~ 특수문자없음
                Log.d("myapp","캘린더 : "+cal.get(Calendar.YEAR)+monthValue+dayValue);
            }

            //일 ~ 토
            for(int i=0;i<=6;i++){
                isDataExist = false;
                String checkDate = todaySdf.format(cal.getTime()).substring(0,4)+""+todaySdf.format(cal.getTime()).substring(5,7)+""+wDate[i].getText();
                for(int j = 1; j <= 30; j++){
                    fire_date = String.valueOf(j);
                    if((int)(Math.log10(j)+1) == 1) fire_date = "0"+fire_date;
                    fire_date = "202109" +  fire_date;
                    Log.d("myapp","fire_date : "+fire_date);
                    for(int k=0; k<5; k++){
                        myRef.child(uid).child("date").child(fire_date).child(String.valueOf(k)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String get_symptom = snapshot.child("symptom").getValue(String.class);
                                Log.d("myappp",fire_date+" 와 "+checkDate+" 비교");

                                if(!get_symptom.equals("e") && fire_date.equals(checkDate)){
                                    isDataExist = true;
                                    Log.d("trrr", String.valueOf(isDataExist));
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                }
                if(isDataExist) weekCalendarDot[i].setVisibility(View.VISIBLE);
            }
        }
    }
    //이용불가 팝업
    public class InfoDialog{
        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //dialog.setContentView(R.layout.info_popup);
            dialog.setContentView(R.layout.info_popup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog.show();
        }
    }
}

