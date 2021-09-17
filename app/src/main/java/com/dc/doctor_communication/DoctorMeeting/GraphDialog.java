package com.dc.doctor_communication.DoctorMeeting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doctorcommunication.DataManagement.Person1;
import com.example.doctorcommunication.R;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphDialog extends Dialog {

    //시작 날짜 '달'
    String startDate;
    //사용자가 선택한 증상
    String buttonValue;

    public GraphDialog(@NonNull Context context) {
        super(context);
    }

    public GraphDialog(@NonNull Context context,String startDate,String buttonValue) {
        super(context);
        this.startDate = startDate;
        this.buttonValue = buttonValue;
    }

    public GraphDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GraphDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    //그래프
    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dc_graph_popup);

        //그래프
        lineChart = findViewById(R.id.graph);
        //그래프 초기 설정
        initGraph();
        //증상 데이터그래프 그리기
        chartEvent(startDate,buttonValue);
    }

    //그래프 초기 기본 설정
    public void initGraph(){
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
        Log.d("myapp","단계 2");
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
        lineChart.invalidate();
    }

    //체크박스에서 선택한 증상 1개를 받아 그래프(1개)를 그림
    public void chartEvent(String strDate,String firstSymp){ //날짜와 사용자가 선택한 증상 1개를 받아옴
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

        lineChart.animateY(1000, Easing.EaseInCubic);
        lineChart.invalidate();
        lineChart.setData(lineData);

    }
    //그래프의 x값(각 주별 심각도 평균) - 날짜와 증상이 사용자가 선택한 것과 일치하는지 확인
    public int[] getAverageOfWeek(String strDate,String symptom){ //상단바에서 선택한 날짜, 증상
        int[] graphData = new int[4];   //그래프의 x좌표 -> 1,2,3,4주차
        int firstWeek = 0,fNum = 0;     //1주차 심각도의 총합과 개수
        int secondWeek = 0,sNum = 0;    //2주차 심각도의 총합과 개수
        int thirdWeek = 0,tNum = 0;     //3주차 심각도의 총합과 개수
        int fourthWeek = 0,foNum = 0;   //4주차 심각도의 총합과 개수
        for(int i = 0; i< Person1.symptom.length; i++){
            if(!Person1.symptom[i].getSymptom_name().equals(symptom)) continue; //사용자가 선택한 증상인지 확인
            switch (isInSameWeek(Person1.symptom[i].getDate(),strDate)){ //사용자가 선택한 날짜인지 확인 (1주차면 1,2주차면 2..반환)
                case 1 : //1주차
                    firstWeek += Integer.parseInt(Person1.symptom[i].getPain_level());
                    fNum++;
                    break;
                case 2 : //2주차
                    secondWeek += Integer.parseInt(Person1.symptom[i].getPain_level());
                    sNum++;
                    break;
                case 3 : //3주차
                    thirdWeek += Integer.parseInt(Person1.symptom[i].getPain_level());
                    tNum++;
                    break;
                case 4 : //4주차
                    fourthWeek += Integer.parseInt(Person1.symptom[i].getPain_level());
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
    public int isInSameWeek(String recordedDate,String strDate){ //각각 데이터가 입력된 날짜, 상단 바에서 선택한 날짜
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
}
