package com.companion.locationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.companion.locationapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static java.lang.System.err;

public class TestFirebase extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println(mDatabase);
        Button addUser = (Button) findViewById(R.id.addToDB);
        Button removeUser = (Button) findViewById(R.id.removeBtn);
        final TextView nameField2 = (TextView) findViewById(R.id.removeInput);
        final TextView nameField = (TextView) findViewById(R.id.nameInput);
        final TextView valueInput = (TextView) findViewById(R.id.valueInput);
        users = new ArrayList<>();
        mDatabase.child("users").addValueEventListener(valueEventListener);

        addUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println(mDatabase);
                String name = nameField.getText().toString();
                String value = valueInput.getText().toString();
                User user = new User(value, "Long2", name, "");
                mDatabase.child("users").child(name).setValue(user);
                System.out.println("try to add");
                return false;
            }
        });
        removeUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String name = nameField2.getText().toString();
                mDatabase.child("users").child(name).removeValue();
                return false;
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                System.out.println(snapshot.getValue());
                User user = new User();
                try {
                    user = snapshot.getValue(User.class);
                    users.add(user);
                }
                catch (DatabaseException data){

                }
            }
            System.out.println(users);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
