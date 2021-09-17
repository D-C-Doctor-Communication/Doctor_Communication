package com.dc.doctor_communication.DataManagement;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

//상태등록에서 입력되는 통증정보와 날짜정보에 대한 클래스
public class Symptom2 implements Serializable {
//data
    @PropertyName("part")
    private String part; //부위
    @PropertyName("symptom")
    private String symptom; //증상
    @PropertyName("painLevel")
    private String painLevel; //통증 정도
    @PropertyName("pain_characteristics")
    private String pain_characteristics; //통증 양상
    @PropertyName("pain_situation")
    private String pain_situation; //악화 상황
    @PropertyName("accompany_pain")
    private String accompany_pain; //동반 증상
    @PropertyName("additional")
    private String additional; //추가사항

    @PropertyName("scheduleName")
    private String scheduleName; // 일정명
    @PropertyName("place")
    private String place; //장소
    @PropertyName("time")
    private String time; // 시간
    @PropertyName("clinic_type")
    private String clinic_type; // 진료/검사 종류
    @PropertyName("memo")
    private String memo; // 진료/검사 종류

    public Symptom2(){

    }

    //추가사항 없는 값 생성자
    public Symptom2(String symptom, String part, String painLevel, String pain_characteristics
            , String pain_situation, String accompany_pain, String scheduleName, String place, String time, String clinic_type, String memo){
        this.symptom = symptom;
        this.part = part;
        this.painLevel = painLevel;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";
        this.scheduleName = scheduleName;
        this.place = place;
        this.time = time;
        this.clinic_type = clinic_type;

        this.memo = memo;
    }
    //추가사항 있는 값 생성자
    public Symptom2(String symptom, String part, String painLevel, String pain_characteristics
            , String pain_situation, String accompany_pain, String additional, String scheduleName, String place, String time, String clinic_type, String memo){
        this.symptom = symptom;
        this.part = part;
        this.painLevel = painLevel;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = additional;

        this.scheduleName = scheduleName;
        this.place = place;
        this.time = time;
        this.clinic_type = clinic_type;

        this.memo = memo;
    }

    public String getPart(){ return part; }
    public String getSymptom(){ return symptom; }
    public String getPainLevel(){ return painLevel; }
    public String getPain_characteristics(){ return pain_characteristics; }
    public String getPain_situation(){ return pain_situation; }
    public String getAccompany_pain(){ return accompany_pain; }
    public String getAdditional(){
        if(additional!=null) return additional;
        return "해당없음";
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public String getClinic_type() {
        return clinic_type;
    }
}