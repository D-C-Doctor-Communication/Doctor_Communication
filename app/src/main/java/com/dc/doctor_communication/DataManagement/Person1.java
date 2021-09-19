package com.dc.doctor_communication.DataManagement;

import com.dc.doctor_communication.DataManagement.Symptom;
import com.dc.doctor_communication.DataManagement.memo;

//임의의 데이터 저장
public class Person1{
    //증상등록
    public static Symptom[] symptom = {
            new Symptom("20210901","머리","두통","2","투명하거나 하얀 가래","시간이 갈수록","목통증"),
            new Symptom("20210905","머리","두통","4","쿡쿡 찌르듯이","시간이 갈수록","피로","몸살이 난것처럼 몸에 힘이 나지 않았다."),
            new Symptom("20210905","머리","두통","6","지끈지끈한","피곤하면","발열"),
            new Symptom("20210905","머리","두통","3","더부룩하며 팽창","음식을 섭취하고 난뒤","피로"),
            new Symptom("20210906","머리","두통","9","조이는 듯한","기침하면","구토","구토감이 들어 새벽에 잠에서 깼다."),
            new Symptom("20210907","허리","요통","10","노란 가래","아침(오전)","기침"),
            new Symptom("20210907","목","가래","4","노란 가래","아침(오전)","목통증"),
            new Symptom("20211008","머리","두통","6","날카로운 통증","시간이 갈수록","피로","하루종일 지속되었다."),
            new Symptom("20211009","머리","두통","8","쿡쿡 찌르듯이","시간이 갈수록","기침"),
            new Symptom("20210910","배","복통","2","단순 통증","피곤하면","메스꺼움"),
            new Symptom("20210910","손목","손목 통증","4","투명하거나 하얀 가래","시간이 갈수록","두통","차가운 물을 마시면 심해진다."),
            new Symptom("20210910","목","가래","6","투명하거나 하얀 가래","시간이 갈수록","피로"),
    };
    //메모
    public static memo[] memos = {
            new memo("20210808","점심 저녁으로 약을 먹고 물을 충분히 마신다."),
            new memo("20210829","자기전에 간단한 스트레칭을 한다."),
            new memo("20210812","따뜻한 물을 마실것."),
            new memo("20210908",""),
            new memo("20210701","")
    };
    //병원진료|검사 예약
    //날짜 이름 시간 장소 종류
    public static Appointment[] appointments = {
            new Appointment("20210806","건강검진","16:00","서울대학병원","검사"),
            new Appointment("20210919","물리치료","18:00","정형외과","진료"),
            new Appointment("20210919","물리치료","19:00","정형외과","검사")
    };
}