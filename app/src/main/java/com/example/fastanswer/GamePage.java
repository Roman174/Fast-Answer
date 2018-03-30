package com.example.fastanswer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class GamePage extends Activity {

    private int count;

    Settings settings;

    private Question Quest;

    private TextView ScoreView;

    private TextView QuestionView;
    private TextView ResultView;

    private TextView ButtonAnswerTrue;
    private TextView ButtonAnswerFalse;

    private ProgressBar ProgressBar;
    private CountDownTimer countDownTimer;

    private Settings Settings;
    SystemFiles systemFiles;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        count = 0;
        systemFiles = new SystemFiles(this);
        settings =  systemFiles.ReadSettings();

        if(settings == null) {
            settings = new Settings();
            settings.SetEasy();
            systemFiles.SaveSettings(settings);
        }

        Quest = new Question();
        ScoreView    = (TextView)findViewById(R.id.ScoreView);
        ScoreView.setText(String.valueOf(count));

        QuestionView = (TextView)findViewById(R.id.GamePageQuestion);
        ResultView   = (TextView)findViewById(R.id.GamePageResult);

        ButtonAnswerTrue  = (TextView)findViewById(R.id.ButtonAnswerTrue);
        ButtonAnswerFalse = (TextView)findViewById(R.id.ButtonAnswerFalse);

        ProgressBar = (ProgressBar)findViewById(R.id.progressbar);
        ProgressBar.setMax(settings.ProgressSize);
        ProgressBar.setProgress(100);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");
        SetFont(Font);

        SetQuestion();
        StartTimer();

        final MediaPlayer playerSuccess = MediaPlayer.create(this, R.raw.button_click);
        final MediaPlayer playerFailed  = MediaPlayer.create(this, R.raw.failed);

        ButtonAnswerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCorrectResult()){
                    playerSuccess.start();
                    SuccessAnswer();
                } else {
                    playerFailed.start();
                    GameOver();
                }
            }
        });

        ButtonAnswerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectResult()) {
                    playerSuccess.start();
                    SuccessAnswer();
                } else {
                    playerFailed.start();
                    GameOver();
                }
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
        progress = settings.ProgressSize;
        countDownTimer = new CountDownTimer(settings.Speed, settings.Interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                ProgressBar.setProgress(progress);
                progress-=settings.DecrementSize;
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
        int min, max;
        switch (settings.levelComplexity) {
            case 0:
                min = 1;
                max = 9;
                break;

            default:
                min = 1;
                max = 20;
                break;
        }

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
        intent.putExtra("PlayerName", settings.PlayerName);
        startActivity(intent);
        GamePage.this.finish();
    }

    private void ShowMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void SetColorProgressBar() {
        Drawable progressDrawable = ProgressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN);
        ProgressBar.setProgressDrawable(progressDrawable);
    }

}
