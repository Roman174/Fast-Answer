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
    private Context context;

    public SystemFiles(Context context) {
        this.context = context;
    }

    public void SaveRecords(int record) {
        int[] Records = ReadRecords();
        int position = 0;
        int min = Records[0];

        for (int i=0; i<Records.length; i++) {
            if(Records[i] < min) {
                min = Records[i];
                position = i;
            }
        }

        Records[position] = record;
        Sort(Records);

        Gson gson = new Gson();
        String JsonString = gson.toJson(Records);
        SaveFile(FileNameRecords, JsonString);
    }

    public int[] ReadRecords() {
        String JsonString;
        try {
            JsonString = ReadFile(FileNameRecords);
        } catch (Exception e) {
            return new int[] {0, 0, 0, 0, 0};
        }

        Gson gson = new Gson();
        int[] Records = gson.fromJson(JsonString, int[].class);
        if(Records == null)
            return new int[] {0, 0, 0, 0, 0};
        return Records;
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

    public static void Sort(int[] array) {
        int last = array.length;

        for ( boolean sorted = last == 0; !sorted; --last )
        {
            sorted = true;
            for ( int i = 1; i < last; ++i )
            {
                if ( array[i-1] < array[i] )
                {
                    sorted = false;

                    int tmp = array[i-1];
                    array[i-1] = array[i];
                    array[i] = tmp;
                }
            }
        }
    }
}
