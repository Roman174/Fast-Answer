package com.example.fastanswer;

/**
 * Created by 174pr on 22.03.2018.
 */

public class Question {
    private int FirstNumber;
    private int SecondNumber;
    private int Result;

    public String QuestString;

    public Question() {
        FirstNumber = 0;
        SecondNumber = 0;
        Result = 0;
        QuestString = "";
    }

    public Question(int ArithmeticAction, int firstNumber, int secondNumber) {
        FirstNumber = firstNumber;
        SecondNumber = secondNumber;

        switch (ArithmeticAction) {
            case 0:
                QuestString = String.format("%d - %d", FirstNumber, SecondNumber);
                Result = FirstNumber - SecondNumber;
                break;

            case 1:
                QuestString = String.format("%d + %d", FirstNumber, SecondNumber);
                Result = FirstNumber + SecondNumber;
                break;
        }
    }

    public boolean isCorrect(int result) {
        if(Result == result)
            return true;
        else return false;
    }

    public int getResult() { return Result; }
}
