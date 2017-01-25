package com.companion.locationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.companion.locationapp.DrawerActivity.DrawerActivity;
import com.companion.locationapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsernameActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String email;
    private Button launchBtn;
    private EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rakoon.ttf");
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setTypeface(tf);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = getSharedPreferences("user", 0).getString("mail", "");
        usernameInput = (EditText) findViewById(R.id.usernameField);
        launchBtn = (Button) findViewById(R.id.launchMap);
        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString().trim();
                User user = new User("", "", username, email);
                mDatabase.child("users").child(user.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            System.out.println("user exist");
                            Toast.makeText(UsernameActivity.this, "Username Already Exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            System.out.println("user not exist");
                            SharedPreferences pref = getSharedPreferences("user", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username", username);
                            editor.commit();
                            startActivity(new Intent(UsernameActivity.this, DrawerActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*startActivity(new Intent(UsernameActivity.this, MapsActivity.class));
                finish();*/
            }
        });
        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setTypeface(tf);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });


    }
}
