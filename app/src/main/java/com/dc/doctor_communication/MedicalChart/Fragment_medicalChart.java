package com.dc.doctor_communication.MedicalChart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

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
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class Fragment_medicalChart extends Fragment {

    //캘린더
    MaterialCalendarView materialCalendarView;
    //선택한 날짜
    TextView selectedDate;
    String selectedDateString;
    //선택한 날짜 formatter
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd (E)");
    //진료 일정 추가하기
    TextView btn_addAppointDoctor;
    //진료 일정이 없습니다 텍스트
    ImageView noneData;
    //진료 후기 수정 버튼
    ImageButton MC_editBtn;
    //진료 후기 텍스트 - 입력란
    EditText MC_LineEditText;
    TextView MC_LineTextView;
    //(팝업창 이동) activity 실행 요청 확인을 위한 요청코드
    static final int REQ_ADD_CONTACT = 1;
    static String fire_date="";

    //리스트
    ListView listView;
    //각 날짜를 클릭했을 때 사용할 어댑터
    MCListViewAdapter adapter = new MCListViewAdapter();
    //기록 데이터 확인 버튼
    Button show_data;
    static int listDataCount = 0;

    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("users");
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    static String uid = user.getUid();

    static String memotext1="";
    static ArrayList<CalendarDay> datess = new ArrayList<>(); //점을 찍을 날짜를 저장,반환
    static Calendar calendar = Calendar.getInstance();

    static int Year, Month, Day;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myapp", "진료기록탭 열림");
        View view = inflater.inflate(R.layout.fragment_medical_chart, container, false);
        AndroidThreeTen.init(getActivity());

        //캘린더
        materialCalendarView = view.findViewById(R.id.calendarView);
        //선택한 날짜
        selectedDate = view.findViewById(R.id.selectedDate);
        //진료 후기 수정 버튼
        MC_editBtn = view.findViewById(R.id.MC_editBtn);
        //진료 후기 텍스트 - 입력란
        MC_LineEditText = view.findViewById(R.id.MC_LineEditText);
        MC_LineTextView = view.findViewById(R.id.MC_LineTextView);
        //병원 일정 추가하기 버튼
        btn_addAppointDoctor = view.findViewById(R.id.btn_addAppointDoctor);
        //진료 일정이 없습니다 ImageView
        noneData = view.findViewById(R.id.noneData);
        //기록 데이터 확인 버튼
        show_data = view.findViewById(R.id.show_data);

        //진료 일정 ListView
        listView = (ListView) view.findViewById(R.id.MC_listView);
        //진료 후기 작성할때 키보드가 UI 가리는것 방지
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //기본 표시 날짜(오늘)
        CalendarDay date = CalendarDay.today();
        int basicYear = date.getYear();
        int basicMonth = date.getMonth()+1;
        int basicDay = date.getDay();
        if(basicMonth<10){
            if(basicDay<10) selectedDateString = basicYear+"0"+basicMonth+"0"+basicDay;
            else selectedDateString = basicYear+"0"+basicMonth+""+basicDay;
        }
        else{
            if(basicDay<10) selectedDateString = basicYear+""+basicMonth+"0"+basicDay;
            else selectedDateString = basicYear+""+basicMonth+""+basicDay;
        }
        Year = basicYear;
        Month = basicMonth;
        Day = basicDay;
        Log.d("날짜", Year+""+Month+""+Day);
        //00.00 (월) 텍스트 지정
        monthCalendar.setDateText(basicYear,basicMonth,basicDay,selectedDate);
        //캘린더 기본 선택된 날짜 지정
        materialCalendarView.setDateSelected(date,true);
        //캘린더 점찍기
        Log.d("End", EndDateOfMonth()+"");
        Log.d("End", Month+"");
        for(int i = 1; i <= EndDateOfMonth(); i++){
            fire_date = String.valueOf(i);
            if((int)(Math.log10(i)+1) == 1) fire_date = "0" + fire_date;
            if(Month<10) fire_date = Year +"0"+ Month +""+ fire_date;
            else fire_date = Year +""+ Month +""+ fire_date;
            for(int j=0; j<5; j++){
                String finalStringDateValue = fire_date;
                myRef.child(uid).child("date").child(finalStringDateValue).child(String.valueOf(j)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String get_symptom = snapshot.child("symptom").getValue(String.class);
                        if(!(Objects.equals(get_symptom, "e")) && get_symptom != null){
                            String dateValue = finalStringDateValue;
                            Log.d("get_dateValue", dateValue);

                            int year = Integer.parseInt(dateValue.substring(0,4));
                            int month = Integer.parseInt(dateValue.substring(4,6));
                            int dayy = Integer.parseInt(dateValue.substring(6));

                            calendar.set(year,month-1,dayy);
                            CalendarDay day = CalendarDay.from(calendar);
                            Log.d("점찍기","데이터 add됨,"+day.getDate());
                            datess.add(day);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        }
        Log.d("점찍기","접근");

        ArrayList<CalendarDay> dates = datess;
        MC_DotEventDecorator dotEventDecorator = new MC_DotEventDecorator(dates);
        materialCalendarView.addDecorator(dotEventDecorator);

        Calendar maxDate = Calendar.getInstance();
        //9월까지 제한
        maxDate.set(2022,9,30);
        materialCalendarView.state().edit().setMaximumDate(maxDate).commit();

        //진료 일정 조회 + 리스트 생성
        checkAppointment();
        Log.d("memo","selectedDateString : "+selectedDateString);
        //진료 후기 작성
        myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String get_memo = snapshot.child("memo").getValue(String.class);
                Log.d("memo_selecteddate", selectedDateString);
                if(get_memo.equals("e")){
                    get_memo = "진료 후기를 작성해주세요.";
                }
                MC_LineTextView.setText(get_memo);
                MC_LineEditText.setText(get_memo);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //수정버튼을 눌렀을때 텍스트뷰,에디트뷰 상태에 따라 수정기능 on off
        MC_editBtn.setOnClickListener(v -> {
            changeTextEdit(selectedDateString);
        });

        //캘린더 날짜 변경시 -
        materialCalendarView.setOnDateChangedListener((widget, date1, selected) -> {

            //선택된 날짜 저장
            Year = date1.getYear();
            Month = date1.getMonth()+1;
            Day = date1.getDay();
            Log.d("날짜변경",Year+"년 "+Month+"월 "+Day+"일");
            //선택된 날짜 텍스트 변경 00.00(월)
            monthCalendar.setDateText(Year,Month,Day,selectedDate);
            //00000000형식의 날짜 저장
            if(Month<10){
                if(Day<10) selectedDateString = Year+"0"+Month+"0"+Day;
                else selectedDateString = Year+"0"+Month+""+Day;
            }
            else{
                if(Day<10) selectedDateString = Year+""+Month+"0"+Day;
                else selectedDateString = Year+""+Month+""+Day;
            }
            //어댑터 초기화
            adapter.clearData();
            //진료 일정 조회 + 리스트 생성
            checkAppointment();

            //진료 후기
            myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("memo_selecteddate", selectedDateString);
                    String get_memo = snapshot.child("memo").getValue(String.class);
                    try {
                        Log.d("memo",get_memo);
                        if(!(get_memo.equals("e"))){
                            MC_LineTextView.setText(get_memo);
                            MC_LineEditText.setText(get_memo);
                        }else {
                            MC_LineTextView.setText("진료 후기를 작성해주세요.");
                            MC_LineEditText.setText("");
                        }
                    }catch (NullPointerException e){
                        MC_LineTextView.setText("진료 후기를 작성해주세요.");
                        MC_LineEditText.setText("");
                        Log.d("memo","null");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

            //수정버튼을 눌렀을때 텍스트뷰,에디트뷰 상태에 따라 수정기능 on off
            MC_editBtn.setOnClickListener(v -> {
                changeTextEdit(selectedDateString);
            });
        });

        //데이터 확인 팝업 띄움
        show_data.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),DataInfo_PopupActivity.class);
            Log.d("myapp","selectedDateString : "+selectedDateString+"");
            intent.putExtra("selectedDate",selectedDateString);
            startActivity(intent);
        });

        //진료 일정 추가
        btn_addAppointDoctor.setOnClickListener(v->{
            //팝업 호출
            Intent intent = new Intent(getActivity(), MC_PopupActivity.class);
            startActivityForResult(intent,REQ_ADD_CONTACT);
        });
        return view;
    }
    //데이터 관련 메소드
    //진료 일정 조회 + 리스트 생성
    public void checkAppointment(){
        //listview 참조 및 adapter 연결
        MCListViewAdapter listViewAdapter = new MCListViewAdapter();

        for(int i = 1; i <= EndDateOfMonth(); i++){
            fire_date = String.valueOf(i);
            listDataCount = 0;
            if((int)(Math.log10(i)+1) == 1) fire_date = "0"+fire_date;
            if(Month<10) fire_date = Year +"0"+ Month +""+ fire_date;
            else fire_date = Year +""+ Month +""+ fire_date;
            Log.d("진료기록","onDataChange 들어옴,"+fire_date);

            String finalStringDateValue = fire_date;
            if (finalStringDateValue.equals(selectedDateString)) {
                myRef.child(uid).child("date").child(finalStringDateValue).child(String.valueOf(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Symptom2 appointments = snapshot.getValue(Symptom2.class);
                        try {
                            if(appointments.getClinic_type().equals("검사")){
                                listViewAdapter.addItem(R.drawable.clinic_checkup,appointments.getScheduleName(),appointments.getPlace(),appointments.getTime());
                                listView.setVisibility(View.VISIBLE);
                                noneData.setVisibility(View.INVISIBLE);
                                btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_nonclicked);
                                btn_addAppointDoctor.setTextColor(Color.BLACK);
                                btn_addAppointDoctor.setText("진료 일정 변경하기");
                                listDataCount++;
                            }
                            else if(appointments.getClinic_type().equals("진료")){
                                listViewAdapter.addItem(R.drawable.clinic_clinic,appointments.getScheduleName(),appointments.getPlace(),appointments.getTime());
                                listView.setVisibility(View.VISIBLE);
                                noneData.setVisibility(View.INVISIBLE);
                                btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_nonclicked);
                                btn_addAppointDoctor.setTextColor(Color.BLACK);
                                btn_addAppointDoctor.setText("진료 일정 변경하기");
                                listDataCount++;
                            }
                            Log.d("myapp","checkAppointment 데이터 추가됨");

                            listView.setAdapter(listViewAdapter);
                            //setListViewHeightBasedOnChildren(listView);
                            listViewAdapter.notifyDataSetChanged();

                        }catch (NullPointerException e){
                            Log.d("null","--");
                        }

                        if (listDataCount ==0){
                            noneData.setVisibility(View.VISIBLE);
                            btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_clicked);
                            btn_addAppointDoctor.setTextColor(Color.WHITE);
                            btn_addAppointDoctor.setText("진료 일정 추가하기");
                        }else{
                            noneData.setVisibility(View.INVISIBLE);
                            btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_nonclicked);
                            btn_addAppointDoctor.setTextColor(Color.BLACK);
                            btn_addAppointDoctor.setText("진료 일정 변경하기");
                        }
                        Log.d("myapp","Adapter added, count : "+ listDataCount);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

            }
        }
        listView.setAdapter(listViewAdapter);
        //setListViewHeightBasedOnChildren(listView);
        listViewAdapter.notifyDataSetChanged();

        if (listDataCount ==0){
            noneData.setVisibility(View.VISIBLE);
            btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_clicked);
            btn_addAppointDoctor.setTextColor(Color.WHITE);
            btn_addAppointDoctor.setText("진료 일정 추가하기");
        }
        Log.d("myapp","Adapter added, count : "+ listDataCount);
    }

    //진료 후기 작성
    public void changeTextEdit(String selectedDateString){
        //텍스트뷰가 활성화중일 때
        if(MC_LineTextView.getVisibility()==View.VISIBLE){
            String temp1 = MC_LineTextView.getText().toString();
            MC_LineTextView.setVisibility(View.INVISIBLE);
            MC_LineEditText.setVisibility(View.VISIBLE);
            MC_LineEditText.setText(temp1);
        }
        //editText가 활성화중일 때
        //[FIREBASE] 메모내용 갱신|저장
        else if(MC_LineEditText.getVisibility()==View.VISIBLE){
            MC_LineEditText.setVisibility(View.INVISIBLE);
            MC_LineTextView.setVisibility(View.VISIBLE);
            String temp = MC_LineEditText.getText().toString();
            MC_LineTextView.setText(temp);
            MC_LineEditText.setText(temp);
            // 메모 기록 수정
            String finalStringDateValue = selectedDateString;
            myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("get_memo_selecteddate", selectedDateString);
                    myRef.child(uid).child("date").child(finalStringDateValue).child(String.valueOf(0)).child("memo").setValue(temp);
                    Toast.makeText(getContext(),"진료 후기가 변경되었습니다.",Toast.LENGTH_SHORT);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }
    //팝업창으로부터 입력받은 정보 저장하는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {

                Log.d("onActivityResult",selectedDateString+"");
                //SharedPreference 해당 날짜(년,월,일) 에 대한 예약 여부 확인
                SharedPreferences detailSharedPreferences = getActivity().getSharedPreferences("detailFile",Context.MODE_PRIVATE);

                Log.d("마지막 확인","detailSharedPreferences : "+detailSharedPreferences.getBoolean(selectedDateString,false));
                if(!detailSharedPreferences.getBoolean(selectedDateString,false)){
                    //SharedPreference 해당 날짜(년,월) 에 대한 예약 횟수 증가
                    Log.d("마지막 확인","detailSharedPreferences2 : "+detailSharedPreferences.getBoolean(selectedDateString,false));
                    SharedPreferences reservationSharedPreferences = getActivity().getSharedPreferences("reservationFile", Context.MODE_PRIVATE);
                    int count = reservationSharedPreferences.getInt(selectedDateString.substring(0,6),0);
                    SharedPreferences.Editor editor = reservationSharedPreferences.edit();
                    editor.putInt(selectedDateString.substring(0,6),count+1);
                    editor.commit();
                }

                SharedPreferences.Editor detailEditor = detailSharedPreferences.edit();
                detailEditor.putBoolean(selectedDateString,true);
                detailEditor.commit();
                Log.d("마지막 확인","add됨");




                //일정이 존재하면 listView visible로 바꿈
                noneData.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                //일정 이름을 MC_PopupActivity로부터 받아옴
                String scheduleName = intent.getStringExtra("schedule_name");
                //장소를 MC_PopupActivity로부터 받아옴
                String location = intent.getStringExtra("location");
                //선택된 시간(timePicker) MC_PopupActivity로부터 받아옴
                String selectedTime = intent.getStringExtra("selected_time");
                //진료일정인지, 검사일정인지를 MC_PopupActivity로부터 받아옴 (진료일정이라면 "진료"값 저장)
                String typeOfSchedule = intent.getStringExtra("selected_button");

                //listview 참조 및 adapter 연결
                listView.setAdapter(adapter);
                if(typeOfSchedule.equals("검사")){
                    adapter.addItem(R.drawable.clinic_checkup,scheduleName,location,selectedTime);
                }
                else if(typeOfSchedule.equals("진료")){
                    adapter.addItem(R.drawable.clinic_clinic,scheduleName,location,selectedTime);
                }
                //setListViewHeightBasedOnChildren(listView);
                adapter.notifyDataSetChanged();
                btn_addAppointDoctor.setBackgroundResource(R.drawable.mc_button_nonclicked);
                btn_addAppointDoctor.setTextColor(Color.BLACK);
                btn_addAppointDoctor.setText("진료 일정 변경하기");

                Log.d("어댑터갯수",adapter.getCount()+"");
                //파이어베이스에 등록정보 저장
                myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Symptom2 appointments = snapshot.getValue(Symptom2.class);
                        Symptom2 ap = new Symptom2(scheduleName, location, selectedTime, typeOfSchedule);
                        myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).child("scheduleName").setValue(scheduleName);
                        myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).child("place").setValue(location);
                        myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).child("time").setValue(selectedTime);
                        myRef.child(uid).child("date").child(selectedDateString).child(String.valueOf(0)).child("clinic_type").setValue(typeOfSchedule);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        }
    }
    // 해당 달의 말일 구하기
    public static int EndDateOfMonth(){
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.KOREA);
        cal.set(Year, Month, Day);

        int endDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return endDate;
    }
    //캘린더 관련 메소드
    static class monthCalendar{
        //00.00 (월) 텍스트 표시
        public static void setDateText(int Year, int Month, int Day,TextView selectedDate){
            CalendarDay date = CalendarDay.from(Year,Month,Day);
            LocalDate date1 = LocalDate.of(Year, Month, Day);
            DayOfWeek dayOfWeek = date1.getDayOfWeek();
            String dayOfWeekDay = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREA);
            selectedDate.setText(date.getMonth()+"."+date.getDay()+"("+dayOfWeekDay+")");
        }
    }
}