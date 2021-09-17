package com.dc.doctor_communication.DataManagement;

//병원 예약에 대한 데이터 클래스
public class memo {
    private String date;
    private String memo;

    public memo(String date,String memo){
        this.date = date;
        this.memo = memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate(){
        return date;
    }
    public String getMemo(){ return memo; }
}
