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

//        Intent intent = getIntent();
//        ((TextView) findViewById(R.id.textView7)).setText(intent.getExtras().getString("title"));

        //get the intent obj
        Intent intent = getIntent();
//get using key the string you passed
        String taskNum = intent.getExtras().getString("title");

        TextView text = findViewById(R.id.textView7);

        text.setText(taskNum);

    }
}