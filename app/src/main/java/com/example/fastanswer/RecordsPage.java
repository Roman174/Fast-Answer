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

    final String FileName = "records.rec";
    String DataFile;
    int sizeFile = 0;

    ListView ListRecords;
    ImageView AppBarButtonBack;
    TextView ButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_page);

        Typeface Font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto.ttf");

        TextView TextStatusBar = (TextView)findViewById(R.id.convert_actionbar_title);
        TextStatusBar.setTypeface(Font);
        TextStatusBar.setText("Records");

        ListRecords      = (ListView)findViewById(R.id.ListRecords);

        ButtonBack       = (TextView)findViewById(R.id.ButtonBack);
        ButtonBack.setTypeface(Font);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.button_click);
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                RecordsPage.this.finish();
            }
        });

        String[] Records;
        try {
            DataFile = ReadRecords(FileName);
        } catch (Exception exc) {
            DataFile = null;
        }

        Records = new String[5];
        if(DataFile != null && DataFile != "") {
            Records = GetListRecods(DataFile);
        } else {
            for (int i = 0; i < 5; i++)
                Records[i] = "0 - Player";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.aligment_center, Records);
        ListRecords.setAdapter(adapter);
    }

    private String ReadRecords(String FileName) throws Exception {
        FileInputStream fIn = openFileInput(FileName);
        InputStreamReader isr = new InputStreamReader(fIn);
        StringBuilder stringBuilder = new StringBuilder();

        int temp;
        while((temp = isr.read()) != -1) {
            char symblol = (char)temp;
            stringBuilder.append((symblol));
        }

        return stringBuilder.toString();
    }

    private void WriteRecords(String FileName, String Records){
        try {
            FileOutputStream fileOutputStream = openFileOutput(FileName, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.write(Records);
            writer.flush();
            writer.close();

            sizeFile = Records.length();
        }
        catch (Exception e) {
            return;
        }
    }

    private String[] GetListRecods(String DataFile){
        String Records[] = DataFile.split(" ");
        return  Records;
    }
}
