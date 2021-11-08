package com.example.taskmaster;


// create the model class
// create the fragment (represent a single). it created to be used over and over again to cover all the data items
// create adapter

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "task_title")
    private final String title;

    @ColumnInfo(name = "task_body")
    private final String body;

    @ColumnInfo(name = "task_state")
    private final String state ;


    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }



    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }



}
