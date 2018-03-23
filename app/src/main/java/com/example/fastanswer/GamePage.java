package com.example.fastanswer;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringReader;
import java.util.Random;
import java.util.Timer;

public class GamePage extends Activity {

    int count;

    private Question Quest;

    private TextView QuestionView;
    private TextView ResultView;

    private TextView ButtonAnswerTrue;
    private TextView ButtonAnswerFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        count = 0;
        Quest = new Question();

        QuestionView = (TextView)findViewById(R.id.GamePageQuestion);
        ResultView   = (TextView)findViewById(R.id.GamePageResult);

        ButtonAnswerTrue  = (TextView)findViewById(R.id.ButtonAnswerTrue);
        ButtonAnswerFalse = (TextView)findViewById(R.id.ButtonAnswerFalse);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");
        SetFont(Font);

        PlayGame();

        ButtonAnswerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = Integer.parseInt(ResultView.getText().toString());
                if(!Quest.isCorrect(res)) {
                    SetQuestion();
                    count ++;
                    isAnswered = true;
                } else ShowMessage(String.valueOf(count));
            }
        });

        ButtonAnswerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = Integer.parseInt(ResultView.getText().toString());
                if(Quest.isCorrect(res)) {
                    SetQuestion();
                    count ++;
                    isAnswered = true;
                } else ShowMessage(String.valueOf(count));
            }
        });
    }

    private void SetFont(Typeface Font) {
        QuestionView.setTypeface(Font);
        ResultView.setTypeface(Font);
        ButtonAnswerTrue.setTypeface(Font);
        ButtonAnswerFalse.setTypeface(Font);
    }

    public void SetQuestion() {
        int min = 1, max = 9;
        Random RandomNumber = new Random();
        int FirstNumber       = RandomNumber.nextInt(max - min + 1) + min;
        int SecondNumber      = RandomNumber.nextInt(max - min + 1) + min;
        int ArithmeticAction  = RandomNumber.nextInt(2);

        Quest = new Question(ArithmeticAction, FirstNumber, SecondNumber);
        QuestionView.setText(Quest.QuestString);

        int verResult         = RandomNumber.nextInt(2);
        switch (verResult) {
            case 0:
                String stringResult = String.valueOf(RandomNumber.nextInt(max - min + 1) + min);
                try {
                    ResultView.setText(stringResult);
                } catch (Exception exc) {
                    String aaa = exc.getMessage();
                    ShowMessage(aaa);
                }
                break;

            case 1:
                String aaa = "";
                try {
                    ResultView.setText(String.valueOf(Quest.getResult()));
                    break;
                }
                catch (Exception exc) {
                    aaa = exc.getMessage();
                    ShowMessage(aaa);
                }

        }
    }

    private boolean isAnswered = false;

    public void PlayGame() {
        new Thread(new Runnable() {
            public void run() {
                while(true) { //бесконечно крутим
                    try {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowMessage("started");

                                if (isAnswered) {
                                    isAnswered = false;
                                }
                                else {
                                    GamePage.this.finish();
                                }
                            }
                        });
                        Thread.sleep(3000); // 4 секунды в милисекундах
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void ShowMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
