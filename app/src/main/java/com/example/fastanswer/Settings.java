package com.example.fastanswer;

/**
 * Created by 174pr on 29.03.2018.
 */

public class Settings {
    public String PlayerName;
    public int ProgressSize;
    public int DecrementSize;
    public int Speed;
    public int Interval;

    int levelComplexity;

    public Settings() {
        PlayerName = null;
        DecrementSize = 1;
        Speed = 1;
        Interval = 1;
        levelComplexity = 0;
    }

//    public void SetEasy() {
//        ProgressSize = 400;
//        Speed =
//    }

    public void SetHard() {
        Speed = 1500;
        Interval = 10;
        DecrementSize = 2;
        ProgressSize = 100;
        levelComplexity = 2;
    }

    public void SetNormal() {
        Speed = 2000;
        Interval = 10;
        DecrementSize = 6;
        ProgressSize = 390;
        levelComplexity = 1;
    }

    public void SetEasy() {
        Speed = 3500;
        Interval = 10;
        DecrementSize = 9;
        ProgressSize = 950;
        levelComplexity = 0;
    }
}
