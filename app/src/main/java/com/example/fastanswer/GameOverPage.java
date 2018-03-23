package com.example.fastanswer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverPage extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_page);

        TextView ButtonOk = (TextView)findViewById(R.id.GameOverPageButton);
        ButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverPage.this, HomePage.class);
                startActivity(intent);
                GameOverPage.this.finish();
            }
        });
    }
}
