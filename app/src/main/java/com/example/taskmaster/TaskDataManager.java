package com.example.taskmaster;

import com.example.taskmaster.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataManager {
    private static TaskDataManager instance = null;
    private List<Task> tasks = new ArrayList<>();

    private TaskDataManager() {
    }

    public static TaskDataManager getInstance() {
        if (instance == null) {
            instance = new TaskDataManager();
        }

        return instance;
    }

    public void setData(List<Task> data) {
        tasks = data;
    }

    public List<Task> getData() {
        return tasks;
    }
}
