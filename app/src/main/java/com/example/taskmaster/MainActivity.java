package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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

        Button settings= findViewById(R.id.settingsId);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        // create data to use in the view:

        ArrayList<Task> taskData = new ArrayList<Task>();


        taskData.add(new Task("TaskOne", "workout", "in progress"));
        taskData.add(new Task("TaskTwo", "Review React js", "assigned"));
        taskData.add(new Task("TaskThree", "learned a new programing language", "new"));
        taskData.add(new Task("TaskThree", "learned a new programing language", "new"));


        // get the Recyler view
        RecyclerView allTaskRecyclerView = findViewById(R.id.recycleViewId);

        // set a layout manager
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter for this recycler view
        allTaskRecyclerView.setAdapter(new TaskAdapter(taskData, this));

    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName","Go and set the username");

        TextView setUserName = findViewById(R.id.userNameId);
        setUserName.setText(userName + "'s tasks");
    }



   // Life cycle of the activities

    @Override
    protected void onStart() {
        super.onStart();


        Log.i(TAG, "onStart called: ");
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