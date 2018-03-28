package com.example.fastanswer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.StringReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage extends Activity {

    private int count;
    private final int Speed = 2000;
    private final int Interval = 10;

    private Timer timer;
    private Question Quest;

    private TextView ScoreView;

    private TextView QuestionView;
    private TextView ResultView;

    private TextView ButtonAnswerTrue;
    private TextView ButtonAnswerFalse;

    private ProgressBar ProgressBar;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        count = 0;

        Quest = new Question();
        timer = new Timer();

        ScoreView    = (TextView)findViewById(R.id.ScoreView);
        ScoreView.setText(String.valueOf(count));

        QuestionView = (TextView)findViewById(R.id.GamePageQuestion);
        ResultView   = (TextView)findViewById(R.id.GamePageResult);

        ButtonAnswerTrue  = (TextView)findViewById(R.id.ButtonAnswerTrue);
        ButtonAnswerFalse = (TextView)findViewById(R.id.ButtonAnswerFalse);

        ProgressBar = (ProgressBar)findViewById(R.id.progressbar);
        ProgressBar.setProgress(100);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");
        SetFont(Font);

        SetQuestion();
        StartTimer();

        final MediaPlayer playerSuccess = MediaPlayer.create(this, R.raw.success);
        final MediaPlayer playerFailed  = MediaPlayer.create(this, R.raw.failed);

        ButtonAnswerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCorrectResult()){
                    SuccessAnswer();
                } else GameOver();
            }
        });

        ButtonAnswerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectResult()) {
                    SuccessAnswer();
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

    private boolean isCorrectResult() {
        int res = Integer.parseInt(ResultView.getText().toString());
        return Quest.isCorrect(res);
    }

    public void SuccessAnswer() {
        countDownTimer.cancel();
        incScore();
        SetQuestion();
        isAnswered = false;
        StartTimer();
    }

    int progress;
    private void StartTimer() {
        progress = 100;
        countDownTimer = new CountDownTimer(Speed, Interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                ProgressBar.setProgress(progress);
                progress-=2;
            }

            @Override
            public void onFinish() {
                if(!isAnswered) GameOver();
                else {
                    SetQuestion();
                }
            }
        };

        countDownTimer.start();
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
                } catch (Exception exc) { }
                break;

            case 1:
                try {
                    ResultView.setText(String.valueOf(Quest.getResult()));
                    break;
                }
                catch (Exception exc) { }

        }
    }

    private void incScore() {
        count ++;
        ScoreView.setText(String.valueOf(count));
    }

    private boolean isAnswered = false;
    private void GameOver() {
        countDownTimer.cancel();
        Intent intent = new Intent(GamePage.this, GameOverPage.class);
        intent.putExtra("Score", count);
        startActivity(intent);
        GamePage.this.finish();
    }

    private void ShowMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
