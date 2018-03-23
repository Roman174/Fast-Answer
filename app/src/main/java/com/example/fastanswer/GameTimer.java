package com.example.fastanswer;

import java.util.TimerTask;

/**
 * Created by 174pr on 23.03.2018.
 */

public class GameTimer extends TimerTask {
    Thread thread;
    public boolean isAnswered;

    GameTimer() {
        isAnswered = false;
    }

    GameTimer( boolean is_answered) {
        isAnswered = is_answered;
    }

    public void run() {
        while(true) {
            if(isAnswered)
                isAnswered = false;
            else {
                return;
            }
        }
    }

}
