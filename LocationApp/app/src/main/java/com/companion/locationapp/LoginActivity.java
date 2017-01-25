package com.companion.locationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.companion.locationapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText mailInput, passwordInput;
    private Button signinBtn, signupBtn, forgotPasswordBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    auth = FirebaseAuth.getInstance();
    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rakoon.ttf");
    TextView titleView = (TextView) findViewById(R.id.titleView);
    titleView.setTypeface(tf);
    signinBtn = (Button) findViewById(R.id.signin);
    signupBtn = (Button) findViewById(R.id.signup);
    mailInput = (EditText) findViewById(R.id.usernameField);
    passwordInput = (EditText) findViewById(R.id.passwordField);
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    forgotPasswordBtn = (Button) findViewById(R.id.forgotPassword);
    signinBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String mail = mailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (TextUtils.isEmpty(mail)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, UsernameActivity.class);
                                SharedPreferences pref = getSharedPreferences("user", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("mail", mail);
                                editor.commit();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(LoginActivity.this, SignupActivity.class));
         }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }
}

