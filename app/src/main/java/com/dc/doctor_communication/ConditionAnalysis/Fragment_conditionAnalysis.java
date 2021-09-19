package com.dc.doctor_communication.ConditionAnalysis;

//android 버전 30쓸거면 androidx.Fragment 사용할것
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dc.doctor_communication.DataManagement.Person1;
import com.dc.doctor_communication.FireBaseManagement.FireData;
import com.dc.doctor_communication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_conditionAnalysis extends Fragment {

    //상단 날짜 선택 바
    private Button nextBtn,previousBtn;
    TextView monthSelect;

    //[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]
    //병원 예약 횟수 텍스트
    private TextView reservation_count;
    //심각도 5 이상 텍스트
    private TextView severity_more_5;
    //총 기록된 통증 수 텍스트
    private TextView accrue_symptom_count;

    //그래프
    private LineChart lineChart;
    //그래프 증상 선택 버튼
    private Button select_symptom;
    //팝업으로 증상 선택 처리
    AlertDialog.Builder builder;
    //사용자가 선택한 증상 목록
    List<String> selectedSymptom = new ArrayList<>();

    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("users");
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    static String uid = user.getUid();

    //증상 빈도 순위
    private TextView firstSymptom;
    private TextView secondSymptom;
    private TextView thirdSymptom;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","상태분석탭 열림");
        View view =  inflater.inflate(R.layout.fragment_condition_analysis,container,false);

        //상단 날짜 선택 바
        nextBtn = view.findViewById(R.id.next_btn);
        previousBtn = view.findViewById(R.id.previous_btn);
        monthSelect = view.findViewById(R.id.month_select);
        //병원 예약 횟수 텍스트
        reservation_count = view.findViewById(R.id.reservation_count);
        //심각도 5 이상 텍스트
        severity_more_5 = view.findViewById(R.id.severity_more_5);
        //총 기록된 통증 수 텍스트
        accrue_symptom_count = view.findViewById(R.id.accrue_symptom_count);
        //증상 빈도 순위
        firstSymptom = view.findViewById(R.id.first_symptom);
        secondSymptom = view.findViewById(R.id.second_symptom);
        thirdSymptom = view.findViewById(R.id.third_symptom);
        //그래프
        lineChart = view.findViewById(R.id.condition_chart);
        //그래프 증상선택 버튼
        select_symptom = view.findViewById(R.id.select_symptom);


        Log.d("파이어베이스",FireData.symptoms.size()+"");


        //현재 날짜를 기준으로 상단 선택 바 텍스트 기본 지정
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleFormatting = new SimpleDateFormat ( "yyyy년 MM월");
        Calendar time = Calendar.getInstance();
        String monthSelectText = simpleFormatting.format(time.getTime());
        monthSelect.setText(monthSelectText);

        //날짜 비교 위해 날짜형식을 "yyyy년 MM월" -> 0000.00형으로 바꿈
        String dataString = changeToString(monthSelectText);
        //기본 선택된 달의 각 텍스트 표시
        if(OrganizedData.appointmentDC(dataString)<10)
            reservation_count.setText("0"+OrganizedData.appointmentDC(dataString)+"");
        else
            reservation_count.setText(OrganizedData.appointmentDC(dataString)+"");
        if(OrganizedData.moreThanFive(dataString)<10)
            severity_more_5.setText("0"+OrganizedData.moreThanFive(dataString)+"");
        else
            severity_more_5.setText(OrganizedData.moreThanFive(dataString)+"");
        accrue_symptom_count.setText(OrganizedData.accruedData(dataString)+"");
        //그래프 초기 설정 (기본 두통과 가래로 선택)
        initGraph();
        chartEvent(dataString,"두통","복통");
        //증상 순위 (날짜에 맞춰 텍스트 지정)
        setRanking(dataString,firstSymptom,secondSymptom,thirdSymptom);
        //그래프 증상선택 버튼 이벤트
        select_symptom.setOnClickListener(v -> {
            //팝업 생성 메소드
            Log.d("myapp",dataString+" ");
            showDialog(dataString);
        });



        //상단 날짜 선택 바 -> 이전 버튼 눌렀을 경우 1달씩 줄임
        previousBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , -1);
            String previousMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(previousMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = changeToString(previousMonthText); //날짜형식 바꿈
            if(OrganizedData.appointmentDC(dataStr)<10)
                reservation_count.setText("0"+OrganizedData.appointmentDC(dataStr)+"");
            else
                reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"");
            if(OrganizedData.moreThanFive(dataStr)<10)
                severity_more_5.setText("0"+OrganizedData.moreThanFive(dataStr)+"");
            else
                severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"");
            initGraph();
            //사용자가 선택해놓은 증상이 있으면 그것으로 새로고침
            if(selectedSymptom.size()==0){
                chartEvent(dataStr,"두통","복통");
            }
            else {
                if(selectedSymptom.size()==2){
                    chartEvent(dataStr,selectedSymptom.get(0),selectedSymptom.get(1));
                }
                else chartEvent(dataStr,selectedSymptom.get(0));
            }

            select_symptom.setOnClickListener(v1 -> {
                //팝업 생성 메소드
                Log.d("myapp",dataStr+" ");
                showDialog(dataStr);
            });
            //증상 순위
            setRanking(dataStr,firstSymptom,secondSymptom,thirdSymptom);

        });


        //상단 날짜 선택 바 -> 다음 버튼 눌렀을 경우 1달씩 늘림
        nextBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , +1);
            String nextMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(nextMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = changeToString(nextMonthText); //날짜형식 바꿈
            if(OrganizedData.appointmentDC(dataStr)<10)
                reservation_count.setText("0"+OrganizedData.appointmentDC(dataStr)+"");
            else
                reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"");
            if(OrganizedData.moreThanFive(dataStr)<10)
                severity_more_5.setText("0"+OrganizedData.moreThanFive(dataStr)+"");
            else
                severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"");
            initGraph();
            //사용자가 선택해놓은 증상이 있으면 그것으로 새로고침
            if(selectedSymptom.size()!=0){
                if(selectedSymptom.size()==2){
                    chartEvent(dataStr,selectedSymptom.get(0),selectedSymptom.get(1));
                }
                else chartEvent(dataStr,selectedSymptom.get(0));
            }
            else chartEvent(dataStr,"두통","복통");
            //그래프 증상선택 버튼 이벤트
            select_symptom.setOnClickListener(v1 -> {
                //팝업 생성 메소드
                Log.d("myapp",dataStr+" ");
                showDialog(dataStr);
            });
            //증상 순위
            setRanking(dataStr,firstSymptom,secondSymptom,thirdSymptom);
        });
        return view;
    }


    //그래프 - 증상선택기능(팝업)
    public void showDialog(String monthSelectText){
        Log.d("myapp",monthSelectText+" ");
        //사용자가 선택한 증상 리스트
        selectedSymptom = new ArrayList<>();
        //팝업빌더
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("증상을 선택하세요");

        //증상 목록 배열
        final String[] items = getResources().getStringArray(R.array.symptom_list);
        //증상이 체크되었는지 확인할 bool 배열
        boolean[] checkedItems = new boolean[items.length];
        for(int i=0;i<items.length;i++){
            checkedItems[i] = false;
        }

        //클릭이벤트
        builder.setMultiChoiceItems(R.array.symptom_list, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                //선택하면 리스트에 저장
                if(isChecked) {
                    selectedSymptom.add(items[which]);
                    //체크한 증상 표시
                    checkedItems[which] = isChecked;
                }
                //선택취소하면 리스트에서 삭제
                else {
                    selectedSymptom.remove(items[which]);
                    //체크한 증상 표시
                    checkedItems[which] = false;
                }
                //선택한 증상의 개수를 기록해서 2개 이상일 경우 체크박스 비활성화
                int count = 0;
                for(int i=0;i<checkedItems.length;i++){
                    if(checkedItems[i]) count++;
                }
                if(count>=3){
                    Toast.makeText(getActivity(),"최대 2개까지 선택이 가능합니다.",Toast.LENGTH_SHORT).show();
                    Log.d("myapp","3개 이상 체크함");
                    checkedItems[which] = false;
                    ((AlertDialog)dialog).getListView().setItemChecked(which,false);
                }
            }
        });
        //적용하기버튼 눌렀을 경우 -> 그래프 데이터에 적용, 그래프 생성
        builder.setPositiveButton("적용하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String final_selection = "";
                if(which==DialogInterface.BUTTON_POSITIVE) {
                    final_selection="";
                    int selectCount = 0;
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            selectCount++;
                            if (selectCount <= 2) {
                                final_selection += items[i] + ",";
                            }
                        }
                    }
                }else if(which==DialogInterface.BUTTON_NEUTRAL) dialog.dismiss();
                //그래프 생성
                String[] selevtedItem = final_selection.split(",");
                for(int i=0;i<selevtedItem.length;i++){
                    Log.d("myapp",getActivity().getApplicationContext()+"선택된 아이템 : "+selevtedItem[i]);
                }
                //체크된 아이템이 하나일때 -> 그래프 한개 생성
                if(selevtedItem.length==1) chartEvent(monthSelectText,selevtedItem[0]);
                    //체크된 아이템이 두개일때 -> 그래프 두개 생성
                else if(selevtedItem.length==2) chartEvent(monthSelectText,selevtedItem[0],selevtedItem[1]);
            }
        });
        //취소버튼 눌렀을 경우 -> popup dialog 삭제
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    //기능별 공통사용 메소드
    //선택한 날과 각 데이터의 달을 비교하기위해 선택한 달을 0000.00형으로 바꿈
    public static String changeToString(String selectedMonth){
        return selectedMonth.substring(0,4)+""+selectedMonth.substring(6,8);
    }
    //데이터의 기록 날짜가 상단 바에서 선택한 달과 일치하면 true 반환
    public static boolean isInSameMonth(String recordedDate,String strDate){ //0000년 00월
        //0000.00.00(데이터.getDate())과 선택한(0000.00) 달 비교
        return recordedDate.substring(0, 6).equals(strDate);
    }


    //증상순위
    static void setRanking(String strDate,TextView one,TextView two,TextView three){
        HashMap<String,Integer> data = new HashMap<>();//new에서 타입 파라미터 생략가능
        if(FireData.symptoms.size()==0){
            one.setText("해당없음");
            two.setText("해당없음");
            three.setText("해당없음");
        }else {
            //각 증상을 key값으로, 증상의 개수를 value값으로 가지는 Map 생성
            for (int i = 0; i < FireData.symptoms.size(); i++) {
                data.put(FireData.symptoms.get(i).getPart(), 0);
            }
            //증상에 따라 +1
            for (int i = 0; i < FireData.symptoms.size(); i++) {
                if (isInSameMonth(FireData.symptoms.get(i).getDate(), strDate))
                    data.put(FireData.symptoms.get(i).getPart(), data.get(FireData.symptoms.get(i).getPart()) + 1);
            }
            //value 개수를 기준으로 내림차순 정렬 (정렬결과에 따라 순위 지정)
            // Map.Entry 리스트 작성
            List<Map.Entry<String, Integer>> list_entries = new ArrayList<>(data.entrySet());
            // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
            Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
                // compare로 값을 비교
                public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {   // 내림 차순으로 정렬
                    return obj2.getValue().compareTo(obj1.getValue());
                }
            });

            if (list_entries.get(0).getValue() != 0) one.setText(list_entries.get(0).getKey());
            else one.setText("해당없음");
            if (list_entries.get(1).getValue() != 0) two.setText(list_entries.get(1).getKey());
            else two.setText("해당없음");
            if (list_entries.get(2).getValue() != 0) three.setText(list_entries.get(2).getKey());
            else three.setText("해당없음");
            //값을 받으려면 list_entries.get(i).getValue().toString();
        }
    }


    //그래프의 x값(각 주별 심각도 평균) - 날짜와 증상이 사용자가 선택한 것과 일치하는지 확인
    static int[] getAverageOfWeek(String strDate,String symptom){ //상단바에서 선택한 날짜, 증상
        int[] graphData = new int[4];   //그래프의 x좌표 -> 1,2,3,4주차
        int firstWeek = 0,fNum = 0;     //1주차 심각도의 총합과 개수
        int secondWeek = 0,sNum = 0;    //2주차 심각도의 총합과 개수
        int thirdWeek = 0,tNum = 0;     //3주차 심각도의 총합과 개수
        int fourthWeek = 0,foNum = 0;   //4주차 심각도의 총합과 개수
        for(int i=0;i<FireData.symptoms.size();i++){
            if(!FireData.symptoms.get(i).getSymptom_name().equals(symptom)) continue; //사용자가 선택한 증상인지 확인
            switch (isInSameWeek(FireData.symptoms.get(i).getDate(),strDate)){ //사용자가 선택한 날짜인지 확인 (1주차면 1,2주차면 2..반환)
                case 1 : //1주차
                    firstWeek += Integer.parseInt(FireData.symptoms.get(i).getPain_level());
                    fNum++;
                    break;
                case 2 : //2주차
                    secondWeek += Integer.parseInt(FireData.symptoms.get(i).getPain_level());
                    sNum++;
                    break;
                case 3 : //3주차
                    thirdWeek += Integer.parseInt(FireData.symptoms.get(i).getPain_level());
                    tNum++;
                    break;
                case 4 : //4주차
                    fourthWeek += Integer.parseInt(FireData.symptoms.get(i).getPain_level());
                    foNum++;
                    break;
            }
        }

        //각 데이터의 값이 0일경우 그래프에도 0으로 표시
        if(fNum!=0) {
            graphData[0] = firstWeek/fNum;
            Log.d("check",firstWeek+" / "+fNum+" : "+graphData[0]);
        }
        if(sNum!=0){
            graphData[1] = secondWeek/sNum;
            Log.d("check",secondWeek+" / "+sNum+" : "+graphData[1]);
        }
        if(tNum!=0) {
            graphData[2] = thirdWeek/tNum;
            Log.d("check",thirdWeek+" / "+tNum+" : "+graphData[2]);
        }
        if(foNum!=0) {
            graphData[3] = fourthWeek/foNum;
            Log.d("check",fourthWeek+" / "+foNum+" : "+graphData[3]);
        }



        return graphData;
    }
    //각 날짜가 선택한 달과 일치하지 않으면 0 반환
    //1주차에 존재하면 1 반환
    //2주차 : 2, 3주차 : 3, 4주차 : 4
    static int isInSameWeek(String recordedDate,String strDate){ //각각 데이터가 입력된 날짜, 상단 바에서 선택한 날짜
        //getDate()와 선택한 날짜 비교
        if(recordedDate.substring(0,6).equals(strDate)){
            int checkDate = Integer.parseInt(recordedDate.substring(6)); //몇일인지 저장
            if(checkDate>=1&&checkDate<=7) return 1;
            else if(checkDate>=8&&checkDate<=14) return 2;
            else if(checkDate>=15&&checkDate<=21) return 3;
            else if(checkDate>=22&&checkDate<=31) return 4;
        }
        return 0;
    }



    //[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]와 관련된 작업 클래스
    static class OrganizedData{
        //총 기록된 통증 수
        public static int accruedData(String strDate){
            int numberOfData = 0;
            for(int i=0;i<FireData.symptoms.size();i++){
                //데이터가 기록된 날짜가 선택된 달과 일치할경우 1씩 증가
                Log.d("총 기록된 통증 수","symptoms : "+FireData.symptoms.get(i).getDate()+", strDate : "+strDate);
                if(isInSameMonth(FireData.symptoms.get(i).getDate(),strDate)) numberOfData++;
            }
            return numberOfData;
        }

        //심각도 5 이상
        public static int moreThanFive(String strDate){
            int numberOfData = 0;
            for(int i=0;i<FireData.symptoms.size();i++){
                //데이터가 기록된 날짜가 선택된 달과 일치할경우 1씩 증가
                if(isInSameMonth(FireData.symptoms.get(i).getDate(),strDate)){
                    if(Integer.parseInt(FireData.symptoms.get(i).getPain_level())>=5) numberOfData++;
                }
            }
            return numberOfData;
        }

        //병원 예약 횟수
        public static int appointmentDC(String strDate){
            int numberOfData = 0;
            for(int i=0;i< Person1.memos.length;i++){
                //데이터날짜가 선택된 달과 일치할경우 1씩 증가
                if(isInSameMonth(Person1.memos[i].getDate(),strDate)) numberOfData++;
            }
            return numberOfData;
        }

    }

    //그래프 관련 메소드
    //그래프 초기화(기본설정)
    private void initGraph(){
        //초기 기본 설정
        //X값 속성 설정
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축이 아래에 위치하도록 설정
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelCount(4,true);
        xAxis.setAxisMaximum(4);
        xAxis.setAxisMinimum(1);
        xAxis.enableGridDashedLine(20, 20, 0);
        xAxis.setDrawLabels(true); //왼쪽 라벨
        xAxis.setDrawAxisLine(false); //왼쪽 라벨라인
        xAxis.setDrawGridLines(false); //세로선

        //Y값 속성 설정
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        yLAxis.setAxisMaximum(10);
        yLAxis.setAxisMinimum(0);
        yLAxis.setLabelCount(6);
        yLAxis.setGranularity(2);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false); //오른쪽 라벨
        yRAxis.setDrawAxisLine(false); //오른쪽 라벨라인
        yRAxis.setDrawGridLines(false);

        //부가설명 공백으로 처리
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        lineChart.setDoubleTapToZoomEnabled(false); //그래프 이동(줌기능)
        lineChart.setDrawGridBackground(false);

        // lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }
    //체크박스에서 선택한 증상 1개를 받아 그래프(1개)를 그림
    private void chartEvent(String strDate,String firstSymp){ //날짜와 사용자가 선택한 증상 1개를 받아옴
        Log.d("myapp","strDate : " + strDate);
        int[] dataArray1 = getAverageOfWeek(strDate,firstSymp); //날짜에 해당하는 데이터 배열을 받아옴

        //그래프 데이터 리스트 생성 (x축 한칸당 값, y값)
        List<Entry> entries1 = new ArrayList<>(); //첫번째 증상
        for(int i=1;i<=4;i++){
            entries1.add(new Entry(i,(float)dataArray1[i-1]));
        }

        //첫번째 증상에 대한 그래프 선 그리기
        LineDataSet lineDataSet1 = new LineDataSet(entries1, firstSymp);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(3);
        lineDataSet1.setCircleColor(Color.parseColor("#FF247D30"));
        lineDataSet1.setCircleHoleColor(Color.BLUE);
        lineDataSet1.setColor(Color.parseColor("#FF247D30"));
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawHorizontalHighlightIndicator(true);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setDrawValues(false);


        LineData lineData = new LineData(lineDataSet1);

        Legend legend = lineChart.getLegend(); //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)

        legend.setWordWrapEnabled(true);
        LegendEntry l1 = new LegendEntry(firstSymp,Legend.LegendForm.SQUARE,10f,2f,null,Color.parseColor("#FF247D30"));
        legend.setCustom(new LegendEntry[]{l1});

        lineChart.animateY(1000,Easing.EaseInCubic);
        lineChart.invalidate();
        lineChart.setData(lineData);

    }
    //체크박스에서 선택한 증상 2개를 받아 그래프(2개)를 그림
    private void chartEvent(String strDate,String firstSymp,String secondSymp){ //날짜와 사용자가 선택한 증상 2개를 받아옴
        int[] dataArray1 = getAverageOfWeek(strDate,firstSymp); //날짜에 해당하는 데이터 배열을 받아옴
        int[] dataArray2 = getAverageOfWeek(strDate,secondSymp);


        //그래프 데이터 리스트 생성 (x축 한칸당 값, y값)
        ArrayList<Entry> entries1 = new ArrayList<>(); //첫번째 증상
        ArrayList<Entry> entries2 = new ArrayList<>(); //두번째 증상
        for(int i=1;i<=4;i++){
            entries1.add(new Entry(i,(float)dataArray1[i-1]));
            entries2.add(new Entry(i,(float)dataArray2[i-1]));
        }
        for(int i=1;i<=4;i++){
            Log.d("myapp","첫번째 데이터 : "+entries1.get(i-1).toString());
        }
        for(int i=1;i<=4;i++){
            Log.d("myapp","두번째 데이터 : "+entries2.get(i-1).toString());
        }
        //그래프선 데이터
        LineData lineData = new LineData();

        //첫번째 증상에 대한 그래프 선 그리기
        LineDataSet lineDataSet1 = new LineDataSet(entries1, firstSymp);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(3);
        lineDataSet1.setCircleColor(Color.parseColor("#FF247D30"));
        lineDataSet1.setCircleHoleColor(Color.BLUE);
        lineDataSet1.setColor(Color.parseColor("#FF247D30"));
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawHorizontalHighlightIndicator(true);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setDrawValues(false);


        //두번째 증상에 대한 그래프 선 그리기
        LineDataSet lineDataSet2 = new LineDataSet(entries2, secondSymp);
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setCircleRadius(3);
        lineDataSet2.setCircleColor(Color.parseColor("#FFE9E965"));
        lineDataSet2.setCircleHoleColor(Color.BLUE);
        lineDataSet2.setColor(Color.parseColor("#FFE9E965"));
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(true);
        lineDataSet2.setDrawHighlightIndicators(true);
        lineDataSet2.setDrawValues(false);

        //첫번째 증상 데이터와 그래프 선 데이터 결합
        lineData.addDataSet(lineDataSet1);
        //두번째 증상 데이터와 그래프 선 데이터 결합
        lineData.addDataSet(lineDataSet2);



        //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
        Legend legend = lineChart.getLegend();
        legend.setWordWrapEnabled(true);
        LegendEntry l1 = new LegendEntry(firstSymp,Legend.LegendForm.SQUARE,10f,2f,null,Color.parseColor("#FF247D30"));
        LegendEntry l2 = new LegendEntry(secondSymp,Legend.LegendForm.SQUARE,10f,2f,null,Color.parseColor("#FFE9E965"));
        legend.setCustom(new LegendEntry[]{l1,l2});
        //애니메이션효과
        lineChart.animateY(1000,Easing.EaseInCubic);

        lineChart.setData(lineData);
        lineChart.invalidate(); //그래프 적용(그리기

    }


}