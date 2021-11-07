package com.example.taskmaster;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// create the adapter class without extend the adapter from the recyclerview class
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // create the list to take the data to bind it to the view holder
    List<Task> allTaskData = new ArrayList<>();
    private final Context context;

    // create a public constructor to be used in the activity (to set the data to the adapter)
    public TaskAdapter(List<Task> allTaskData,Context context) {
        this.allTaskData = allTaskData;
        this.context= context;

    }

    // create the view holder class (wrap the data and the view)
    //note : we create a static inner class inorder to create a view holder without create instances

    public class TaskViewHolder extends RecyclerView.ViewHolder{


        // create a model object
        public Task task;

        // create the view object
        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.d("my Adapter", "Element "+ getAdapterPosition() + " clicked");
                    String title= allTaskData.get(getAdapterPosition()).getTitle();
                    String body= allTaskData.get(getAdapterPosition()).getBody();
                    String state= allTaskData.get(getAdapterPosition()).getState();

                    Intent intent= new Intent(context,TaskDetail.class);
                    intent.putExtra("title",title);
                    intent.putExtra("body",body);
                    intent.putExtra("state",state);
                    context.startActivity(intent);


                }
            });
        }
    }

    // extends the recycler view adapter

    @NonNull
    @Override
    // this method will create a view to be represented in the UI.
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent , false);

              //  TaskViewHolder taskViewHolder = new TaskViewHolder(view);

        return  new TaskViewHolder(view);
    }

    @Override
    // this method will bind the data from the adapter to the view holder that we create
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = allTaskData.get(position);
        TextView taskTitle= holder.itemView.findViewById(R.id.titleFragId);
        TextView taskBody= holder.itemView.findViewById(R.id.bodyFragId);
        TextView taskState= holder.itemView.findViewById(R.id.stateFragId);

        taskTitle.setText(holder.task.getTitle());
        taskBody.setText(holder.task.getBody());
        taskState.setText(holder.task.getState());

    }

    @Override
    // return the number of items that I have in my data
    // will use by the recyclerView inorder to create a fragment as much as we need to cover the list of data
    public int getItemCount() {
        return allTaskData.size();
    }
}
