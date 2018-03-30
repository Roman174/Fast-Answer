package com.example.fastanswer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsPage extends Activity {

    private Settings settings;
    SystemFiles systemFiles;

    private Spinner spinnerComplexity;
    private TextView ButtonSaveSettings;
    private EditText EditPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        systemFiles = new SystemFiles(this);
        settings = new Settings();

        EditPlayerName = (EditText) findViewById(R.id.EditPlayerName);

        spinnerComplexity = (Spinner)findViewById(R.id.SpinnerComplexity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_complexity, android.R.layout.simple_spinner_dropdown_item);

        spinnerComplexity.setAdapter(adapter);
        spinnerComplexity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int numberItem = spinnerComplexity.getSelectedItemPosition();
                switch (numberItem) {
                    case 0:
                        settings.SetEasy();
                        break;

                    case 1:
                        settings.SetNormal();
                        break;

                    case 2:
                        settings.SetHard();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerComplexity.setSelection(0);
            }
        });

        ButtonSaveSettings = (TextView)findViewById(R.id.ButtonSaveSettings);
        ButtonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.PlayerName = EditPlayerName.getText().toString();
                systemFiles.SaveSettings(settings);
                SettingsPage.this.finish();
            }
        });

        settings = systemFiles.ReadSettings();
        if(settings == null) {
            settings =new Settings();
            settings.SetEasy();
            EditPlayerName.setText("Player");
            spinnerComplexity.setSelection(settings.levelComplexity);
        } else {
            EditPlayerName.setText(settings.PlayerName);
            spinnerComplexity.setSelection(settings.levelComplexity);
        }
    }
}
