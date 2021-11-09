package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Task;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";
    public static Long taskCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button button = findViewById(R.id.addTask2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView textView = findViewById(R.id.textView6);
//                taskCount++;
//                textView.setText("Total Tasks :" + taskCount);
//
//                Toast addTask = Toast.makeText(getApplicationContext(), "new Task added", Toast.LENGTH_SHORT);
//                addTask.show();
//
//                TextView taskTitleInput = findViewById(R.id.taskTitleInputId);
//                String taskTitle = taskTitleInput.getText().toString();
//
//                TextView taskDescInput = findViewById(R.id.taskDescInputId);
//                String taskDesc = taskDescInput.getText().toString();
//
//                TextView taskStateInput = findViewById(R.id.taskStateInputId);
//                String taskState = taskStateInput.getText().toString();
//
//
//                Task task = new Task(taskTitle, taskDesc, taskState);
//                Long addedTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
//
//
//                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
//                startActivity(mainIntent);
//
//                System.out.println(
//                        "++++++++++++++++++++++++++++++++++++++++++++++++++" +
//                                " Task ID : " + addedTaskID
//                                +
//                                "++++++++++++++++++++++++++++++++++++++++++++++++++"
//                );

                // =================lab32

                //save button onClickListener handler
                button.setOnClickListener(View -> {

                    TextView task_title = findViewById(R.id.taskTitleInputId);
                    TextView task_description = findViewById(R.id.taskDescInputId);
                    TextView taskStateInput = findViewById(R.id.taskStateInputId);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

                    String taskTitle = task_title.getText().toString();
                    String taskDescription = task_description.getText().toString();
                    String taskStatus = taskStateInput.getText().toString();;

                 // saveToDataStore(taskTitle, taskDescription, taskStatus);
                    saveToApi(taskTitle, taskDescription, taskStatus);

                    taskCount = sharedPreferences.getLong("taskCount", 0);
                    editor.putLong("taskCount", taskCount + 1);
                    editor.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_LONG);
                    toast.show();

                    TextView textView = findViewById(R.id.textView6);
                    textView.setText("Total Tasks: " + (taskCount + 1));
                });
            }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        taskCount = sharedPreferences.getLong("taskCount", 0);
        System.out.println(taskCount);
        TextView textView = findViewById(R.id.textView6);
        textView.setText("Total Tasks: " + taskCount);
    }

    private void saveToDataStore(String taskTitle, String taskDescription, String taskStatus) {

        Task task = Task.builder().title(taskTitle).body(taskDescription).state(taskStatus).build();
        Amplify.DataStore.save(task,
                success -> Log.i(TAG, "Saved item: " + success.item().getTitle()),
                error -> Log.i(TAG, "Saved item: " + error.getMessage()));
    }

    private void saveToApi(String taskTitle, String taskDescription, String taskStatus) {
        Task task = Task.builder().title(taskTitle).body(taskDescription).state(taskStatus).build();

        System.out.println(task.toString());

        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle()),
                error -> Log.i(TAG, "Saved item: " + error.getMessage()));
    }

}