package com.dc.doctor_communication.MedicalChart;

public class MCListViewItem {

    private int icon;
    private String titleText;
    private String location;
    private String time;

    public void setIcon(int icon){
        this.icon = icon;
    }
    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public int getIcon(){
        return icon;
    }
    public String getTitleText() {
        return titleText;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

}