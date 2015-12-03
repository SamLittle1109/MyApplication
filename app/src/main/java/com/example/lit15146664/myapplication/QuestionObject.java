package com.example.lit15146664.myapplication;

/**
 * Created by lit15146664 on 07/10/2015.
 */
public class QuestionObject {

    private String question;
    private boolean answer;
    private int picture;

    public QuestionObject(String question,boolean answer,int picture){
        this.question = question;
        this.answer = answer;
        this.picture = picture;
    }

    public String getQuestion(){
        return question;
    }
    public boolean isAnswer(){
        return answer;
    }
    public int getPicture(){
        return picture;
    }

}
