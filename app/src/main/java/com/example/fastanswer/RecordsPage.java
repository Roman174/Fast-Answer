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
        int[] Records = systemFiles.ReadRecords();

        Integer[] IntegerRecords = ArrIntToArrInteger(Records);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                R.layout.aligment_center, IntegerRecords);
        ListRecords.setAdapter(adapter);
    }

    private Integer[] ArrIntToArrInteger(int[] arr) {
        Integer[] result = new Integer[arr.length];
        for (int i=0; i<arr.length; i++)
            result[i] = new Integer(arr[i]);

        return result;
    }
}
