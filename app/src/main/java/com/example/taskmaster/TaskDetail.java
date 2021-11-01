package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //get the intent obj
        Intent intent = getIntent();
        //get using key the string you passed
        String taskName = intent.getExtras().getString("Title");
        TextView text = findViewById(R.id.textView7);
        text.setText(taskName);

    }
}