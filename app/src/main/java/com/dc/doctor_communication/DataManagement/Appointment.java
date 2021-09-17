package com.dc.doctor_communication.DataManagement;

//진료 / 검사 일정 등록
public class Appointment {

    //일정이 이루어질 날짜 (사용자가 캘린더에서 선택한 날짜)
    private String date;
    //일정 이름
    private String name;
    //일정 시간
    private String time;
    //일정 장소
    private String location;
    //일정 종류 (진료|검사)
    private String sort;

    public Appointment(){}
    public Appointment(String date, String name, String time, String location, String sort){
        this.date = date;
        this.name = name;
        this.time = time;
        this.location = location;
        this.sort = sort;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getSort() {
        return sort;
    }
}
