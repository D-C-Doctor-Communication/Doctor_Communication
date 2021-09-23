package com.dc.doctor_communication.DoctorMeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.doctor_communication.DataManagement.Person1;
import com.dc.doctor_communication.ConditionAnalysis.Fragment_conditionAnalysis;
import com.dc.doctor_communication.DataManagement.Symptom2;
import com.dc.doctor_communication.MainActivity;
import com.dc.doctor_communication.R;
import com.dc.doctor_communication.Recording.Recording;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MeetingDoc extends AppCompatActivity {

    //초기 가이드 text
    private TextView notice_noData_text;
    //기간선택 - 시작 날짜를 표시할 TextView
    private TextView startDateText;
    //기간선택 - 종료 날짜를 표시할 TextView
    private TextView endDateText;
    //날짜선택 버튼 위 증상 텍스트
    private TextView symptom_title;
    //버튼 - 심각도 그래프로 이동하는 버튼
    private TextView gotoGraph;
    //증상선택 버튼 키값
    private final int[] buttonKey = {R.id.btn_1_symptom,R.id.btn_2_symptom,R.id.btn_3_symptom,R.id.btn_4_symptom,R.id.btn_5_symptom
            ,R.id.btn_6_symptom,R.id.btn_7_symptom,R.id.btn_8_symptom,R.id.btn_9_symptom,R.id.btn_10_symptom
            ,R.id.btn_11_symptom,R.id.btn_12_symptom,R.id.btn_13_symptom,R.id.btn_14_symptom,R.id.btn_15_symptom
            ,R.id.btn_16_symptom,R.id.btn_17_symptom,R.id.btn_18_symptom,R.id.btn_19_symptom,R.id.btn_20_symptom};
    //버튼 - 증상선택 버튼
    private final Button[] symptomBtn = new Button[buttonKey.length];
    //증상선택 버튼 내용
    private final String[] buttonValue = {"복통", "두통", "요통","손목 통증","흉통","무릎 통증","속 쓰림","팔꿈치 통증","엉덩이 통증","발열","기침","인후통","콧물","귀 통증","이명","피로","호흡곤란","떨림","소화불량","발목 통증"};
    //기간선택 (시작날짜/끝날짜)
    private Calendar startDate, endDate;
    static FirebaseAuth firebaseAuth;
    int sizeList;
    //증상기록 리스트
    ExpandableListView expandableListView;
    CustomAdapter adapter;
    ArrayList<ParentData> groupListDatas;
    ArrayList<ArrayList<ContentData>> childListDatas;

    //심각도 그래프 이동했을 때 누른 버튼 저장용
    int btnClicked = -1;
    String fire_date;

    RelativeLayout selectedDataLayout;
    ImageView notice_noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp", "의사와의 만남탭 열림");

        //기본 세팅
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 각 프레그먼트들로 이동하기 위한 객체 생성
        //툴바 구성 - 이전버튼, 녹음버튼있
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.dc_actionbar);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_btn);
        //증상 선택 버튼
        for (int i = 0; i < buttonKey.length; i++) {
            symptomBtn[i] = findViewById(buttonKey[i]);
        }
        //datePicker에 들어갈 시작/끝날짜 calendar 객체
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        //증상기록 리스트 뷰 연결
        expandableListView = findViewById(R.id.DC_listview);



        // 증상에 대한 심각도 그래프로 이동하는 버튼
        gotoGraph = findViewById(R.id.btn_gotoGraph);
        gotoGraph.setOnClickListener(v -> {
            String graphDate = startDate.get(Calendar.YEAR)+""+startDate.get(Calendar.MONTH);
            //팝업 띄우기
            GraphDialog graphDialog = new GraphDialog(MeetingDoc.this,graphDate,buttonValue[btnClicked]);
            graphDialog.setCancelable(true);
            graphDialog.setCanceledOnTouchOutside(true);
            graphDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            graphDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            graphDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            graphDialog.show();
        });

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);
        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDateText = findViewById(R.id.startDate);
        endDateText = findViewById(R.id.endDate);
        selectedDataLayout = findViewById(R.id.selectedDataLayout);
        notice_noData = findViewById(R.id.notice_noData);
        notice_noData_text = findViewById(R.id.notice_noData_text);
// -> 날짜 선택 기능
        //기본날짜 오늘로 지정
        setDateText(startDate);
        setDateText(endDate);
        //시작날짜 DatePickerDialog 동작
        startDateText.setOnClickListener(v -> {
            //DatePickerDialog 객체 생성
            //R.style.MyDatePickerStyle,
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                            startDateText.setText(PickedDate);
                            startDate.set(year,month+1,dayOfMonth);
                        }
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    , startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH)-1, startDate.get(Calendar.DAY_OF_MONTH)
            );
            //오늘 이후 비활성화
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.init(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH)-1,startDate.get(Calendar.DAY_OF_MONTH),
                    new DatePicker.OnDateChangedListener(){
                        @Override
                        public void onDateChanged(DatePicker view,int year, int month,int dayOfMonth){
                            if(btnClicked!=-1){
                                Log.d("myapp","데이터 갱신됨");
                                startDate.set(year,month+1,dayOfMonth);
                                setData(btnClicked);
                            }
                        }
                    });
            //DatePickerDialog 표시
            datePickerDialog.show();
        });
        //종료날짜 DatePickerDialog 동작
        endDateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                            endDateText.setText(PickedDate);
                            endDate.set(year,month+1,dayOfMonth);
                        }
                    }
                    , endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)-1, endDate.get(Calendar.DAY_OF_MONTH)

            );

            //오늘 이후 비활성화
            Calendar c = Calendar.getInstance();
            c.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH)-1,startDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.init(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH)-1,endDate.get(Calendar.DAY_OF_MONTH),
                    new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker view,int year, int month,int dayOfMonth){

                    if(btnClicked!=-1){
                        Log.d("myapp","데이터 갱신됨");
                        endDate.set(year,month+1,dayOfMonth);
                        setData(btnClicked);
                    }
                }
            });
            //DatePickerDialog 표시
            datePickerDialog.show();
        });
    }



    //날짜 초기값 지정
    public void setDateText(Calendar cal){
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        startDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        endDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        cal.set(mYear,mMonth+1,mDay);
    }

    //날짜 조건 확인 (시작날짜와 끝날짜 사이)
    public boolean checkIsBetween(String date){
        Log.d("check_date", date);
        //시작 / 끝 날짜
        int start = startDate.get(Calendar.YEAR)*10000+startDate.get(Calendar.MONTH)*100+startDate.get(Calendar.DAY_OF_MONTH);
        int end = endDate.get(Calendar.YEAR)*10000+endDate.get(Calendar.MONTH)*100+endDate.get(Calendar.DAY_OF_MONTH);
        Log.d("checkIsBetween","==============start : "+start+", end : "+end);
        if(Integer.parseInt(date)>=start && Integer.parseInt(date)<=end) return true;

        return false;
    }

    // -> toolbar 기능 (뒤로가기 버튼)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //리스트 데이터 설정
    private void setData(int valudIdx){
        Log.d("myapp","setData 넘어옴");
        //부모,자식 데이터 arraylist
        groupListDatas = new ArrayList<ParentData>();
        childListDatas = new ArrayList<ArrayList<ContentData>>();
        sizeList = 0;
        //누른 버튼의 값 (증상 선택)
        String selectedSymptom = buttonValue[valudIdx];

        firebaseAuth =  FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREA);
        String today = sdf.format(date);

        //선택된 증상 데이터 선별
        for(int i=1; i<=Integer.parseInt(EndDateOfMonth()); i++){
            fire_date = String.valueOf(i);
            if((int)(Math.log10(i)+1) == 1) fire_date = "0"+fire_date;
            fire_date = today +  fire_date;
            if(checkIsBetween(fire_date)){
                Log.d("myapp22","checkIsBetween통과함");
                for(int j=0; j<5; j++){
                    String finalStringDateValue = fire_date;
                    myRef.child(uid).child("date").child(finalStringDateValue).child(String.valueOf(j)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Symptom2 symptom = snapshot.getValue(Symptom2.class);

                            if((!symptom.getSymptom().equals("e")) && symptom.getSymptom().equals(selectedSymptom)) {
                                Log.d("get_fire2", symptom.getSymptom()+","+symptom.getPart()+","+symptom.getPainLevel()+","+symptom.getPain_characteristics()+","+symptom.getPain_situation()+","+symptom.getAccompany_pain()+","+symptom.getAdditional());
                                Log.d("fire_Date 통과", symptom.getSymptom()+selectedSymptom);
                                Log.d("finalStringDateValue", finalStringDateValue);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

                                /*String dateStr = "";
                                dateStr = finalStringDateValue.substring(0,4)+"."+finalStringDateValue.substring(4,6)+"."+finalStringDateValue.substring(6,8);
                                Log.d("datestr", dateStr);*/

                                String yoil = " ";
                                try {
                                    Date date = sdf.parse(finalStringDateValue);
                                    calendar.setTime(date);
                                    yoil = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.KOREAN);
                                } catch (ParseException e) {
                                    Log.d("myapp","예외발생");
                                    e.printStackTrace();
                                }
                                Log.d("parent", yoil+symptom.getPart()+","+symptom.getPainLevel());

                                groupListDatas.add(new ParentData(
                                        finalStringDateValue + " ("+yoil+")",
                                        symptom.getPart(),
                                        symptom.getPainLevel())
                                );

                                childListDatas.add(new ArrayList<ContentData>());
                                Log.d("additional",symptom.getAdditional()+"");
                                childListDatas.get(sizeList).add(new ContentData(
                                        symptom.getPart() ,
                                        symptom.getPainLevel(),
                                        symptom.getPain_characteristics() ,
                                        symptom.getPain_situation(),
                                        symptom.getAccompany_pain(),
                                        symptom.getAdditional())
                                );

                                sizeList++;
                                Log.d("myapp","sizeList : "+sizeList+"");
                            }
                            if(sizeList==0){
                                selectedDataLayout.setVisibility(View.INVISIBLE);
                                notice_noData.setVisibility(View.VISIBLE);
                            }else{
                                selectedDataLayout.setVisibility(View.VISIBLE);
                                notice_noData.setVisibility(View.INVISIBLE);
                            }
                            //Log.d("list", childListDatas.get(0).get(0).getPart());
                            adapter = new CustomAdapter(MeetingDoc.this,groupListDatas,childListDatas);
                            //리스트 생성
                            expandableListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }

            }
        }

    }
    public static String EndDateOfMonth(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.KOREA);
        cal.set(year, month, end);
        String endDate = dateFormat.format(cal.getTime());
        return endDate;
    }
    //증상별로 데이터 넘겨주기
    public void sympOnClick(View view){
        notice_noData_text.setVisibility(View.GONE);
        for(int i=0;i<buttonKey.length;i++){
            symptomBtn[i].setTextColor(Color.BLACK);
            symptomBtn[i].setBackgroundResource(R.drawable.dc_button_nonclicked);
            if(view.getId()==buttonKey[i]){
                symptomBtn[i].setTextColor(Color.WHITE);
                symptomBtn[i].setBackgroundResource(R.drawable.dc_button_clicked);
                Log.d("myapp", buttonValue[i] + " 버튼 눌림");
                //텍스트 지정
                symptom_title.setText(buttonValue[i]);
                //선택한 증상에 맞는 데이터 처리 (리스트 데이터 준비)
                setData(i);
                Log.d("myapp","어댑터 변경사항 적용됨");
                btnClicked = i;
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}