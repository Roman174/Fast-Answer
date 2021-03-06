package com.example.fastanswer;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RecordsPage extends Activity {

    String DataFile;
    int sizeFile = 0;

    ListView ListRecords;
    ImageView AppBarButtonBack;
    TextView ButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_page);

        Typeface Font = Typeface.createFromAsset(getAssets(), "fonts/Roboto.ttf");
        TextView TextActionBar = (TextView) findViewById(R.id.convert_actionbar_title);
        TextActionBar.setTypeface(Font);
        TextActionBar.setText("Records");

        ListRecords = (ListView) findViewById(R.id.ListRecords);

        ButtonBack = (TextView) findViewById(R.id.ButtonBack);
        ButtonBack.setTypeface(Font);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.button_click);
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                RecordsPage.this.finish();
            }
        });

        SystemFiles systemFiles = new SystemFiles(this);
        Record[] Records = systemFiles.ReadRecords();

        String[] StringRecords = ArrIntToArrInteger(Records);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.aligment_center, StringRecords);
        ListRecords.setAdapter(adapter);
    }

    private String[] ArrIntToArrInteger(Record[] arr) {
        String[] result = new String[arr.length];
        for (int i=0; i<arr.length; i++)
            result[i] = String.format("%d - " + arr[i].PlayerName, arr[i].Record);

        return result;
    }
}
