package com.example.fastanswer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.StringReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage extends Activity {

    private int count;
    private int Speed;

    private Timer timer;
    private Question Quest;

    private TextView ScoreView;

    private TextView QuestionView;
    private TextView ResultView;

    private TextView ButtonAnswerTrue;
    private TextView ButtonAnswerFalse;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        count = 0;
        Speed = 1500;

        Quest = new Question();
        timer = new Timer();

        ScoreView    = (TextView)findViewById(R.id.ScoreView);
        ScoreView.setText(String.valueOf(count));

        QuestionView = (TextView)findViewById(R.id.GamePageQuestion);
        ResultView   = (TextView)findViewById(R.id.GamePageResult);

        ButtonAnswerTrue  = (TextView)findViewById(R.id.ButtonAnswerTrue);
        ButtonAnswerFalse = (TextView)findViewById(R.id.ButtonAnswerFalse);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");
        SetFont(Font);

        SetQuestion();
        StartInitialTimer();

        ButtonAnswerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = Integer.parseInt(ResultView.getText().toString());
                if(!Quest.isCorrect(res)) {
                    TimerStop();
                    SetQuestion();
                    incScore();
                    isAnswered = true;
                    TimerStart(Speed);
                } else GameOver();
            }
        });

        ButtonAnswerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = Integer.parseInt(ResultView.getText().toString());
                if(Quest.isCorrect(res)) {
                    TimerStop();
                    incScore();
                    SetQuestion();
                    isAnswered = true;
                    TimerStart(Speed);
                } else GameOver();
            }
        });
    }

    private void SetFont(Typeface Font) {
        ScoreView.setTypeface(Font);
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

    private void incScore() {
        count ++;
        ScoreView.setText(String.valueOf(count));
    }

    private boolean isAnswered = false;

    private void GameOver() {
        Intent intent = new Intent(GamePage.this, GameOverPage.class);
        intent.putExtra("Score", count);
        startActivity(intent);
        GamePage.this.finish();
        timer.cancel();
    }

    private void StartInitialTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(isAnswered)
                            isAnswered = false;
                        else {
                            GameOver();
                        }
                    }
                });
            }
        }, 1500, Speed);
    }

    private void TimerStart(int Speed) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(isAnswered)
                            isAnswered = false;
                        else {
                            GameOver();
                        }
                    }
                });
            }
        }, 0, Speed);
    }

    private void TimerStop() {
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {}
    }

    private void ShowMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
