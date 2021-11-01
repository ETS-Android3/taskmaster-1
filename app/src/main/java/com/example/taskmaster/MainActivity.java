package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity<AppBarConfiguration> extends AppCompatActivity {

    private static final String TAG = "check";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: called");

        Button addTaskButton= findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        Button showAllTasks= findViewById(R.id.allTasks);
        showAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AllTasksActivity.class);
                startActivity(intent);
            }
        });

        //Intent Put and Get Extras (share data between Activities)

        findViewById(R.id.taskId1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TaskDetail.class);

                TextView text = findViewById(R.id.textView7);
                String name = text.getText().toString();
//               this will work if we navigate to to the activity right away and that is why we are gonna change it to SharedPreference
//                 store as key and value to pass
                intent.putExtra("title",name);

               startActivity(intent);
            }
        });

    }

    public void getTask1(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task1");
        startActivity(taskDetail);
    }

    public void getTask2(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task2");
        startActivity(taskDetail);
    }

    public void getTask3(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task3");
        startActivity(taskDetail);
    }


   // Life cycle of the activities

    @Override
    protected void onStart() {
        super.onStart();


        Log.i(TAG, "onStart called: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause CAllED");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "CAll OnStop");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        Log.i(TAG, "onDestroy: method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: called!");
    }

}