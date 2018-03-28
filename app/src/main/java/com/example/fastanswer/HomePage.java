package com.example.fastanswer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //deleteFile("records.rec");

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");

        TextView Title         = (TextView)findViewById(R.id.Title);
        Title.setTypeface(Font);

        TextView Footer        = (TextView)findViewById(R.id.TextFooter);
        Footer.setTypeface(Font);

        TextView ButtonRecords = (TextView)findViewById(R.id.ButtonRecords);
        ButtonRecords.setTypeface(Font);

        TextView ButtonPlay    = (TextView)findViewById(R.id.ButtonPlay);
        ButtonPlay.setTypeface(Font);

        TextView ButtonAbout   = (TextView)findViewById(R.id.ButtonAbout);
        ButtonAbout.setTypeface(Font);

        ButtonAbout.setWidth(ButtonPlay.getWidth());

        final MediaPlayer player = MediaPlayer.create(this, R.raw.button_click);

        ButtonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, RecordsPage.class);
                player.start();
                startActivity(intent);

            }
        });

        ButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, GamePage.class);
                player.start();
                startActivity(intent);
                HomePage.this.finish();
            }
        });

        ButtonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomePage.this, AboutPage.class);
                player.start();
                startActivity(intent);
            }
        });
    }


}
