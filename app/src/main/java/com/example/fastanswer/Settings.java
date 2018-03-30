package com.example.fastanswer;

import android.widget.ProgressBar;

/**
 * Created by 174pr on 29.03.2018.
 */

public class Settings {
    private String PlayerName;
    private int ProgressSize;
    private int DecrementSize;
    private int Speed;
    private int Interval;

    public Settings() {
        PlayerName = null;
        DecrementSize = 1;
        Speed = 1;
        Interval = 1;
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
    }

    public void SetNormal() {
        Speed = 2000;
        Interval = 10;
        DecrementSize = 6;
        ProgressSize = 390;
    }
}
