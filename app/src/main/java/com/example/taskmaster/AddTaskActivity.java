package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.datastore.generated.model.Task;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";
    public static Long taskCount;
    private String idTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Spinner teamSpinner = findViewById(R.id.team_spinner);

        ArrayAdapter<CharSequence> teamArrayAdapter = ArrayAdapter.createFromResource(this, R.array.teams, android.R.layout.simple_spinner_item);
        teamArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamArrayAdapter);
        
        getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = findViewById(R.id.addTask2);
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
            String taskStatus = taskStateInput.getText().toString();

            Spinner spinner = findViewById(R.id.team_spinner);
            String teamName = spinner.getSelectedItem().toString();

            Log.i(TAG, "TEAM NAME ====> " + teamName);

            String id = null;
            if (teamName.equals("Team A")) id = "50cfe996-fd1f-4e92-b2d0-2354d624b66d";
            else if (teamName.equals("Team B")) id = "bd12a537-d0a6-4ea8-9be9-3330210f41db";
            else if (teamName.equals("Team C")) id = "b019efd8-6709-4351-986d-6367f039de73";

            Log.i(TAG, "TEAM ID ====> " + id);

            saveToApi(taskTitle, taskDescription, taskStatus, id);

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

//    private void saveToDataStore(String taskTitle, String taskDescription, String taskStatus) {
//
//        Task task = Task.builder().title(taskTitle).body(taskDescription).state(taskStatus).build();
//        Amplify.DataStore.save(task,
//                success -> Log.i(TAG, "Saved item: " + success.item().getTitle()),
//                error -> Log.i(TAG, "Saved item: " + error.getMessage()));
//    }

    private void saveToApi(String taskTitle, String taskDescription, String taskStatus, String teamID) {

        Task task = Task.builder().title(taskTitle).body(taskDescription).state(taskStatus).teamId(teamID).build();

        System.out.println(task.toString());

        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle()),
                error -> Log.i(TAG, "Saved item: " + error.getMessage()));
    }

//    private void saveToApi(String name, String taskTitle, String taskDescription, String taskStatus) {
//
//        final Team[] team = new Team[1];
//        Amplify.API.query(ModelQuery.get(Team.class, name),
//                res ->
//                        team[0] = Team.builder().name(name).id(res.getData().getId()).build()
//                , error -> Log.i("MainActivity", error.getMessage()));
//
//        Task task = Task.builder().title(taskTitle).body(taskDescription).state(taskStatus).teamId(team[0].getId()).build();
//
//        Amplify.API.mutate(ModelMutation.create(task),
//                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle()),
//                error -> Log.i(TAG, "Saved item: " + error.getMessage()));
//    }
}