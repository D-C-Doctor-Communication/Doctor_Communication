package com.dc.doctor_communication.HomeScreen;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeListViewItem {
    static FirebaseAuth firebaseAuth;
    private int record_img;
    private String record_title;
    private int record_content_painLevel;
    private String record_content_characteristics;
    private String record_content_situation;

    public void setImage(int image){
        this.record_img = image;
    }
    public void setTitle(String title){
        this.record_title = title;
    }
    public void setPainLevel(int content_painLevel){
        this.record_content_painLevel = content_painLevel;
    }
    public void setCharacteristics(String content_characteristics){
        this.record_content_characteristics = content_characteristics;
    }
    public void setSituation(String content_situation){
        this.record_content_situation = content_situation;
    }

    public int getImage(){
        return this.record_img;
    }

    public String getTitle(){
        return this.record_title;
    }
    public String getPainLevel(){
        String resultPainLevel;

        switch(record_content_painLevel){
            case 0: resultPainLevel = "통증없음"; break;
            case 1:
            case 2: resultPainLevel = "가벼운 통증"; break;
            case 3:
            case 4: resultPainLevel = "보통 통증"; break;
            case 5:
            case 6: resultPainLevel = "심한 통증"; break;
            case 7:
            case 8: resultPainLevel = "매우 심한 통증"; break;
            case 9:
            case 10: resultPainLevel = "최악의 고통"; break;
            default: resultPainLevel="알 수 없음";
        }

        return resultPainLevel;
    }
    public String getCharacteristics(){
        return this.record_content_characteristics;
    }
    public String getSituation(){
        return this.record_content_situation;
    }

}
