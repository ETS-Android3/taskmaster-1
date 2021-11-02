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
        String taskName = intent.getExtras().getString("title");
        TextView text = findViewById(R.id.textView7);
        text.setText(taskName);

        String taskBody= intent.getExtras().getString("body");
        TextView textBody = findViewById(R.id.textView8);
        textBody.setText(taskBody);

        String taskState = intent.getExtras().getString("state");
        TextView textState = findViewById(R.id.textView9);
        textState.setText(taskState);

    }
}