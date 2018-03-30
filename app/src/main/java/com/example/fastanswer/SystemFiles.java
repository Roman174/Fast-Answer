package com.example.fastanswer;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by 174pr on 26.03.2018.
 */

public final class SystemFiles {
    final String FileNameRecords = "records.rec";
    final String FileNameSettings = "settings.ini";
    private Context context;

    public SystemFiles(Context context) {
        this.context = context;
    }

    public void SaveRecords(int record, String PlayerName) {
        Record[] Records = ReadRecords();
        int position = 0;
        int min = Records[0].Record;

        for (int i=0; i<Records.length; i++) {
            if(Records[i].Record < min) {
                min = Records[i].Record;
                position = i;
            }
        }

        Records[position].Record = record;
        Records[position].PlayerName = PlayerName;
        Sort(Records);

        Gson gson = new Gson();
        String JsonString = gson.toJson(Records);
        SaveFile(FileNameRecords, JsonString);
    }

    public Record[] ReadRecords() {
        String JsonString;
        try {
            JsonString = ReadFile(FileNameRecords);
        } catch (Exception e) {
            Record[] res = new Record[5];
            for (int i=0; i< 5; i++)
                res[i] = new Record();

            return res;
        }

        Gson gson = new Gson();
        Record[] Records = gson.fromJson(JsonString, Record[].class);
        if(Records == null) {
            Record[] res = new Record[5];
            for (int i=0; i< 5; i++)
                res[i] = new Record();

            return res;
        }
        return Records;
    }

    public static void Sort(Record[] array) {
        int last = array.length;

        for ( boolean sorted = last == 0; !sorted; --last )
        {
            sorted = true;
            for ( int i = 1; i < last; ++i )
            {
                if ( array[i-1].Record < array[i].Record )
                {
                    sorted = false;

                    Record tmp = array[i-1];
                    array[i-1] = array[i];
                    array[i] = tmp;
                }
            }
        }
    }


    public  void SaveSettings(Settings settings) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(settings);
        SaveFile(FileNameSettings, jsonString);
    }

    public Settings ReadSettings() {
        Settings settings;
        try {
            String jsonString = ReadFile(FileNameSettings);
            Gson gson = new Gson();
            settings = gson.fromJson(jsonString, Settings.class);
        } catch (Exception e) {
            return null;
        }

        return settings;
    }


    private void SaveFile(String fileName, String fileData) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.write(fileData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            return;
        }
    }

    private String ReadFile(String FileName) throws Exception {
        FileInputStream fIn = context.openFileInput(FileName);
        InputStreamReader isr = new InputStreamReader(fIn);
        StringBuilder stringBuilder = new StringBuilder();

        int temp;
        while((temp = isr.read()) != -1) {
            char symbol = (char)temp;
            stringBuilder.append((symbol));
        }

        return stringBuilder.toString();
    }
}
