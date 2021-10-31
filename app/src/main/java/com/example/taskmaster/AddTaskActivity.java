package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

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
                Toast addTask= Toast.makeText(getApplicationContext(),"new Task added", Toast.LENGTH_SHORT);
                addTask.show();

            }
        });
    }
}