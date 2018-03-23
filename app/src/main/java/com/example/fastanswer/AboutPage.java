package com.example.fastanswer;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");

        TextView TextActionBar      = (TextView)findViewById(R.id.convert_actionbar_title);
        TextActionBar.setTypeface(Font);
        TextActionBar.setText("About");

        TextView AboutText  = (TextView)findViewById(R.id.AboutText);
        AboutText.setTypeface(Font);

        TextView ButtonBack = (TextView)findViewById(R.id.AboutButtonBack);
        ButtonBack.setTypeface(Font);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.button_click);
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                AboutPage.this.finish();
            }
        });



    }
}
