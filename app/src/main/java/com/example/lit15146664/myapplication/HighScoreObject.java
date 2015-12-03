package com.example.lit15146664.myapplication;

import io.paperdb.Paper;


public class HighScoreObject {

    private String Name;
    private int Score;
    private long Time;

    public HighScoreObject(int Score, String Name, long Time){

        this.Name = Name;
        this.Score = Score;
        this.Time = Time;

    }

    public HighScoreObject() {
    }

    public long getTime() {
        Paper.book().write("dd-MM-yyyy",Time);

        return Time;
    }

    public int getScore() {
        Paper.book().write("10",Score);
        return Score;
    }

    public String getName() {
        Paper.book().write("Samuel",Name);
        return Name;
    }
}
