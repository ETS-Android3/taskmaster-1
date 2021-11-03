package com.example.taskmaster;


// create the model class
// create the fragment (represent a single). it created to be used over and over again to cover all the data items
// create adapter
public class Task {


    private final String title;
    private final String body;
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
