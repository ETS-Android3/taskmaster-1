package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    public static int c =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button button= findViewById(R.id.addTask2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView = findViewById(R.id.textView6);
                c++;
                textView.setText("Total Tasks :"+c);

                Toast addTask= Toast.makeText(getApplicationContext(),"new Task added", Toast.LENGTH_SHORT);
                addTask.show();
            }
        });
    }
}