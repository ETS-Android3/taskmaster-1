package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            //get the intent obj
            Intent intent = getIntent();
            //get using key the string you passed
            String taskName = intent.getExtras().getString("title");
            TextView text = findViewById(R.id.textView7);
            text.setText(taskName);

            String taskBody = intent.getExtras().getString("body");
            TextView textBody = findViewById(R.id.textView8);
            textBody.setText(taskBody);

            String taskState = intent.getExtras().getString("state");
            TextView textState = findViewById(R.id.textView9);
            textState.setText(taskState);

            String fileName = extras.getString("fileName");
            Amplify.Storage.downloadFile(
                    "image",
                    new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                    result -> {
                        ImageView image = findViewById(R.id.imgeViewIdDetail);
                        extras.getString("fileName");
                        image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));

                        Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile());
                    },
                    error -> Log.e("MyAmplifyApp", "Download Failure", error)
            );
        }
    }
}