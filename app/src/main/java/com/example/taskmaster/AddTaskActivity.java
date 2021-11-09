package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";
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

                TextView taskTitleInput = findViewById(R.id.taskTitleInputId);
                String taskTitle = taskTitleInput.getText().toString();

                TextView taskDescInput = findViewById(R.id.taskDescInputId);
                String taskDesc = taskDescInput.getText().toString();

                TextView taskStateInput = findViewById(R.id.taskStateInputId);
                String taskState = taskStateInput.getText().toString();


                Task task = new Task(taskTitle, taskDesc, taskState);
                Long addedTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);


                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);

                System.out.println(
                        "++++++++++++++++++++++++++++++++++++++++++++++++++" +
                                " Task ID : " + addedTaskID
                                +
                                "++++++++++++++++++++++++++++++++++++++++++++++++++"
                );

                // =================lab32

                com.amplifyframework.datastore.generated.model.Task task1= com.amplifyframework.datastore.generated.model.Task.builder()
                        .title(taskTitle)
                        .body(taskDesc)
                        .state(taskState)
                        .build();

                saveTaskToAPI(task1);


            }
        });


    }


    public void saveTaskToAPI(com.amplifyframework.datastore.generated.model.Task task) {
        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + task.getTitle()),
                error -> Log.e(TAG, "Could not save item to API/dynamodb" + task.getTitle()));

    }

// writing to DataBase

//    Post post = Post.builder()
//            .title("Create an Amplify DataStore app")
//            .status(PostStatus.PUBLISHED)
//            .build();
//
//Amplify.DataStore.save(post,
//    result -> Log.i("MyAmplifyApp", "Created a new post successfully"),
//    error -> Log.e("MyAmplifyApp",  "Error creating post", error)
//            );
}