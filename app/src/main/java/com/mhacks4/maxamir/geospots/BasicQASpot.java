package com.mhacks4.maxamir.geospots;

import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("BasicQASpot")
public class BasicQASpot extends Spot{
    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";

    public BasicQASpot(){
        super();
    }

    public BasicQASpot(String title, double longitude, double latitude){
        super(title, longitude, latitude);
    }

    public BasicQASpot(String title, double longitude, double latitude, String question, String answer){
        super(title, longitude, latitude);
        setQuestion(question);
        setAnswer(answer);
    }

    public String getQuestion(){
        return (String) getObject(QUESTION);
    }

    public String getAnswer(){
        return (String) getObject(ANSWER);
    }

    public void setQuestion(String question){
        setObject(QUESTION, question);
    }

    public void setAnswer(String answer){
        setObject(ANSWER, answer);
    }

}
