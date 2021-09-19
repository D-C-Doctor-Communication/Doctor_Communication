package com.dc.doctor_communication.FireBaseManagement;

public class Symptom {
    private String date; //Date클래스 형식을 String형으로 변환
    private String part; //부위
    //--증상 이름과 부위 구분함--
    private String symptom_name; //증상 이름
    private String pain_level; //통증 정도
    private String pain_characteristics; //통증 양상
    private String pain_situation; //악화 상황
    private String accompany_pain; //동반 증상
    private String additional; //추가사항

    //추가사항 없는 값 생성자
    public Symptom(String date,String part,String symptom_name,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain){
        this.date = date;
        this.part = part;
        this.symptom_name = symptom_name;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";
    }
    //추가사항 있는 값 생성자
    public Symptom(String date,String part,String symptom_name,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain,String additional){
        this.date = date;
        this.part = part;
        this.symptom_name = symptom_name;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = additional;
    }

    //각 값별 get메서드
    public String getDate(){ return date; }
    public String getPart(){ return part; }
    public String getSymptom_name(){ return symptom_name; }
    public String getPain_level(){ return pain_level; }
    public String getPain_characteristics(){ return pain_characteristics; }
    public String getPain_situation(){ return pain_situation; }
    public String getAccompany_pain(){ return accompany_pain; }
    public String getAdditional(){
        if(additional!=null) return additional;
        return "해당없음";
    }
}
