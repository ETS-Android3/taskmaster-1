package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

//

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileUser;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;

public class MainActivity<AppBarConfiguration> extends AppCompatActivity {

    private static final String TAG = "check";

    private static PinpointManager pinpointManager;

    private AppDatabase database;
    private TaskDao taskDao;
    private List<Task> tasksList;


    RecyclerView recyclerView;
    Handler handler;
    Handler handler2;

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i(TAG, "INIT => " + userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }

    public void assignUserIdToEndpoint() {
        TargetingClient targetingClient = pinpointManager.getTargetingClient();
        EndpointProfile endpointProfile = targetingClient.currentEndpoint();
        EndpointProfileUser endpointProfileUser = new EndpointProfileUser();
        endpointProfileUser.setUserId("UserIdValue");
        endpointProfile.setUser(endpointProfileUser);
        targetingClient.updateEndpointProfile(endpointProfile);
        Log.d(TAG, "Assigned user ID " + endpointProfileUser.getUserId() +
                " to endpoint " + endpointProfile.getEndpointId());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPinpointManager(getApplicationContext());
        assignUserIdToEndpoint();

        //===================lab32

//        configureAmplify();
//        seedTeams();

        //======================

        Log.i(TAG, "onCreate: called");

        Button addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        Button showAllTasks = findViewById(R.id.allTasks);
        showAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllTasksActivity.class);
                startActivity(intent);
            }
        });

        Button settings = findViewById(R.id.settingsId);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycleViewId);
        getDataFromDynamoDBApi();

        handler = new Handler(Looper.getMainLooper(), message -> {
            if (tasksList.size() > 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new TaskAdapter(tasksList, getApplicationContext()));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            return false;
        });

        handler2 = new Handler(Looper.getMainLooper(), message -> {
            String teamID = message.getData().getString("teamID");
            Amplify.API.query(ModelQuery.list(Task.class, Task.TEAM_ID.contains(teamID)),// edit team_id and add team obj or team name and query by it
                    success -> {
                        tasksList = new ArrayList<>();
                        success.getData().forEach(task -> tasksList.add(task));
                        handler.sendEmptyMessage(1);
                    },
                    error -> Log.e(TAG, "Could not initialize Amplify", error));
            return false;
        });
    }


    private void configureAmplify() {
        try {
            Amplify.addPlugin(new AWSDataStorePlugin()); // stores records locally
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName", "Go and set the username");

        TextView setUserName = findViewById(R.id.userNameId);
        setUserName.setText(userName + "'s tasks");
        getDataFromDynamoDBApi();

        recyclerView.setAdapter(new TaskAdapter(tasksList, getApplicationContext()));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void seedTeams() {
        String[] teams = getResources().getStringArray(R.array.teams);
        Team teamObj;

        for (String team : teams) {
            teamObj = Team.builder().name(team).build();
            Amplify.API.mutate(ModelMutation.create(teamObj),
                    res -> Log.i("MainActivity", String.format("Team %s has been successfully saved!", team)),
                    error -> Log.i("MainActivity", error.getMessage()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDataFromDynamoDBApi() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("team", "Team A");
        final Team[] team = new Team[1];

        Amplify.API.query(ModelQuery.list(Team.class, Team.NAME.contains(name)),
                res -> {
                    String teamID = null;
                    for (Team item: res.getData()) {
                        teamID = item.getId();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("teamID", teamID);

                    Message message = new Message();
                    message.setData(bundle);

                    handler2.sendMessage(message);
                }

                , error -> Log.i("MainActivity", error.getMessage()));
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void getDataFromDynamoDBApi() {
//        Amplify.API.query(ModelQuery.list(Task.class),
//                success -> {
//                    tasksList = new ArrayList<>();
//                    success.getData().forEach(task -> tasksList.add(task));
//                    System.out.println(tasksList);
//                    handler.sendEmptyMessage(1);
//                },
//                error -> Log.e(TAG, "Could not initialize Amplify", error));
//    }


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