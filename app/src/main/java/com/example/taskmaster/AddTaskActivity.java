package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.datastore.generated.model.Task;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.provider.Settings;


public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";
    public static Long taskCount;
    private String idTeam;

    // lab 42
    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.i(TAG, "The location is => " + mLastLocation);
        }
    };

    private double lat;
    private double lon;

    private static final int REQUEST_PERMISSION = 123;
    private static final int REQUEST_OPEN_GALLERY = 1111;
    private static final int PERMISSION_ID = 44;


    //    LAB 37
    String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        //lab 42
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button getLocationBut= findViewById(R.id.getLocationId);
        getLocationBut.setOnClickListener(view -> {
            getLastLocation();
        });

        //lab 41
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        ImageView image = findViewById(R.id.shareImageTextViewId);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    image.setImageURI(imageUri);
                    image.setVisibility(View.VISIBLE);
                }
            }
        }

        //=============================================================================================
        recordEvent();


        Spinner teamSpinner = findViewById(R.id.team_spinner);

        ArrayAdapter<CharSequence> teamArrayAdapter = ArrayAdapter.createFromResource(this, R.array.teams, android.R.layout.simple_spinner_item);
        teamArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamArrayAdapter);

        Button addFile = findViewById(R.id.uploadFileId);
        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromDevice();
            }
        });

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


//===========================================================================================================

    // lab 42
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {

            if (isLocationEnabled()) {

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

                    Location location = task.getResult();

                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(10);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this); // this may or may not be needed
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getFileFromDevice();
            } else {
                Log.i(TAG, "Error : Permission Field");
            }
        }
    }

    //======================================================================================

    private void getFileFromDevice(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a File");
        startActivityForResult(chooseFile, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFileCopied");
        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(data.getData());
            OutputStream outputStream = new FileOutputStream(uploadFile);
            fileName = data.getData().toString();
            byte[] buff = new byte[1024];
            int length;
            while ((length = exampleInputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            exampleInputStream.close();
            outputStream.close();
            Log.i(TAG, "onActivityResult: onActivityResult");
            Amplify.Storage.uploadFile(
                    "image",
                    uploadFile,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void recordEvent(){
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Launch Add Task Activity")
                .addProperty("Channel", "SMS")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .addProperty("UserAge", 120.3)
                .build();

        Amplify.Analytics.recordEvent(event);
    }




}