package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        recordEvent();

        Button signUp = findViewById(R.id.buttonSignUpId);
        TextView signIn = findViewById(R.id.signInTextId);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.UserNameSignUpId);
                EditText email = findViewById(R.id.EmailSignUpId);
                EditText password = findViewById(R.id.passwordSignUpId);
                Log.i(TAG, "onClick: clicked");
                signUp(
                        username.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString());
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SignUp.this, SignIn.class);
                startActivity(signIn);

            }
        });

    }

    void signUp(String username, String email, String password) {
        Log.i(TAG, "signUp: HELOOOOOOOOOOOOOOOOOOOOO");
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(
                email,
                password,options,
                success -> {
                    Log.i(TAG, "signUp successful: " + success.toString());
                    Intent goToVerification = new Intent(SignUp.this, VerificationActivity.class);
                    goToVerification.putExtra("username", email);
                    goToVerification.putExtra("password", password);
                    startActivity(goToVerification);
                },
                error -> {
                    Log.e(TAG, "signUp failed: " + error.toString());
                });
    }

    private void recordEvent(){
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Launch Join activity")
                .addProperty("Channel", "SMS")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .addProperty("UserAge", 120.3)
                .build();

        Amplify.Analytics.recordEvent(event);
    }



}