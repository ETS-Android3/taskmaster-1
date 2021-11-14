package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner teamSpin = findViewById(R.id.settingTeamSpin);

        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(this,
                R.array.teams, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpin.setAdapter(teamAdapter);
        Button button = findViewById(R.id.saveId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor editor2 = preferences2.edit();
                TextView text = findViewById(R.id.userNameInput);
                String team = teamSpin.getSelectedItem().toString();
                String userName =text.getText().toString();
                editor2.putString("userName",userName);
                editor2.putString("team",team);
                editor2.apply();
                Intent intent = new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
            }
        });


//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            findViewById(R.id.saveId).setOnClickListener(view -> {
//            TextView text = findViewById(R.id.userNameInput);
//            String userName =text.getText().toString();
//
//            editor.putString("userName",userName);
//            editor.apply();
//
//        });
    }
}