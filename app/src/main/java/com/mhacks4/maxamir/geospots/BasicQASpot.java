package com.mhacks4.maxamir.geospots;

import android.os.Parcel;
import android.os.Parcelable;

public class BasicQASpot extends Spot{
    private String question;
    private String answer;


    BasicQASpot(String title, double longitude, double latitude){
        super(title, longitude, latitude);
    }

    BasicQASpot(String title, double longitude, double latitude, String question, String answer){
        super(title, longitude, latitude);
        this.question = question;
        this.answer = answer;
    }

    void setQuestion(String question){
        this.question = question;
    }

    void setAnswer(String answer){
        this.answer = answer;
    }

    String getQuestion(){
        return question;
    }

    String getAnswer(){
        return answer;

    }


}
