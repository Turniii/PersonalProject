package com.companion.locationapp.DrawerActivity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.companion.locationapp.LocationTracker;
import com.companion.locationapp.LocationTracker2;
import com.companion.locationapp.LoginActivity;
import com.companion.locationapp.MapsActivity;
import com.companion.locationapp.R;
import com.companion.locationapp.UsernameActivity;
import com.companion.locationapp.model.User;
import com.companion.locationapp.model.UserAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;

public class DrawerActivity extends AppCompatActivity
        implements ListView.OnItemClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener{

    private final double PROXY_PARAM = 500;
    private Context mContext;
    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private User USER;
    private Marker mMarker, userMarker;
    private HashMap<String, Marker> markerHashMap;
    private  HashMap<String, Boolean> notifiedList;
    private ArrayList<User> userArrayList;
    private LocationTracker locationTracker;
    private UserAdapter userAdapter;
    private ListView listView;
    private MyReceiver myReceiver;
    private boolean firstTime = true;
    private int notificationNumber = 9980;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        this.notifiedList = new HashMap<>();
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rakoon.ttf");
        USER = new User();
        USER.setName(getSharedPreferences("user", 0).getString("username", ""));
        USER.setEmail(getSharedPreferences("user", 0).getString("mail", ""));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        listView = (ListView) findViewById(R.id.nav_view);
        assert listView != null;
        listView.setOnItemClickListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_drawer, listView, false);
        TextView headerMail =  (TextView) header.findViewById(R.id.headerMail);
        TextView headerName =  (TextView) header.findViewById(R.id.headerName);
        ImageButton headerButton = (ImageButton) header.findViewById(R.id.headerButton);
        headerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                moveCameraTowardUser(USER);
            }
        });
        headerMail.setText(USER.getEmail());
        headerName.setText(USER.getName());
        View footer = LayoutInflater.from(this).inflate(R.layout.nav_footer_drawer, listView, false);
        Button changeUsernameBtn = (Button) footer.findViewById(R.id.usernameButton);
        Button logoutBtn = (Button) footer.findViewById(R.id.logoutButton);
        changeUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServices(UsernameActivity.class);
            }
        });
        changeUsernameBtn.setTypeface(tf);
        logoutBtn.setTypeface(tf);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                stopServices(LoginActivity.class);

            }
        });
        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer);
        // Map Part

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userArrayList = new ArrayList<>();
        markerHashMap = new HashMap<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addValueEventListener(valueEventListener);

        mDatabase.child("users").child(USER.getName()).setValue(USER);
        userAdapter = new UserAdapter(this, userArrayList);
        listView.setAdapter(userAdapter);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationTracker.ACTION);
        registerReceiver(myReceiver, intentFilter);

        startService(new Intent(this, LocationTracker.class));
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                mDatabase.child("users").child(USER.getName()).removeValue();
                locationTracker.stopUsingGPS();
                stopService(new Intent(mContext, LocationTracker.class));
            }
        });
    }

    public void stopServices(Class<?> intentActivity) {
        mDatabase.child("users").child(USER.getName()).removeValue();
        mContext.stopService(new Intent(mContext, LocationTracker.class));
        try {
            mContext.unregisterReceiver(myReceiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        mContext.startActivity(new Intent(mContext, intentActivity));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        if (USER.getLatitude() != null && USER.getLongitude() != null){
            LatLng pos = new LatLng(Double.parseDouble(USER.getLatitude()), Double.parseDouble(USER.getLongitude()));

            mMap.addMarker(new MarkerOptions().position(pos).title("Me : "+ USER.getName()));

            moveCameraTowardUser(USER);
        }

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                User user = new User();
                try {
                    user = snapshot.getValue(User.class);
                    userArrayList.add(user);
                }
                catch (DatabaseException data){

                }
            }
            Location USERLocation = new Location("user loc");
            if (USER.getLongitude() != null){
                USERLocation.setLatitude(Double.parseDouble(USER.getLatitude()));
                USERLocation.setLongitude(Double.parseDouble(USER.getLongitude()));
            }
            for (int i = 0; i < userArrayList.size(); i++){
                User tempsUser = userArrayList.get(i);
                if (tempsUser.getLongitude() != null){
                    if (tempsUser.getName().equals(USER.getName())){
                        if (userMarker != null){
                            userMarker.remove();
                        }
                        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(USER.getLatitude()), Double.parseDouble(USER.getLongitude()))).title("Me : "+ USER.getName()).icon(BitmapDescriptorFactory.defaultMarker(HUE_AZURE)));
                    }
                    else {
                        Marker previousMarker = markerHashMap.get(tempsUser.getName());
                        if (previousMarker != null){
                            previousMarker.remove();
                            markerHashMap.remove(tempsUser.getName());
                        }
                        Location tempLoc = new Location("tempLoc");
                        tempLoc.setLongitude(Double.parseDouble(tempsUser.getLongitude()));
                        tempLoc.setLatitude(Double.parseDouble(tempsUser.getLatitude()));
                        double distance = USERLocation.distanceTo(tempLoc);
                        if (distance < PROXY_PARAM){
                            System.out.println("DISTANCE : " + distance);
                            if (notifiedList.get(tempsUser.getName()) != null && !notifiedList.get(tempsUser.getName())){
                                sendNotification(tempsUser.getName(), distance);
                            }
                            notifiedList.put(tempsUser.getName(), true);
                        }
                        else if (distance > PROXY_PARAM){
                            notifiedList.put(tempsUser.getName(), false);

                        }




                        NumberFormat formatter = NumberFormat.getNumberInstance();
                        formatter.setMinimumFractionDigits(0);
                        formatter.setMaximumFractionDigits(0);
                        String distanceFmt = formatter.format(distance);
                        LatLng myPos = new LatLng(Double.parseDouble(tempsUser.getLatitude()), Double.parseDouble(tempsUser.getLongitude()));
                        mMarker = mMap.addMarker(new MarkerOptions().position(myPos).title("User : "+ tempsUser.getName()).snippet("Distance to : "+ distanceFmt + " m"));
                        markerHashMap.put(tempsUser.getName(), mMarker);
                    }
                }
            }
            userAdapter = new UserAdapter(getBaseContext(), userArrayList);
            listView.setAdapter(userAdapter);
            userArrayList = new ArrayList<>();


        }

        private void sendNotification (String username, double distance){

            notificationNumber++;
            if (notificationNumber > 9999){
                notificationNumber = 9980;
            }

            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.drawable.icon);
            mBuilder.setContentTitle("A user Approach");
            mBuilder.setContentText(username +" is close to you ("+formatter.format(distance)+")");
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationNumber, mBuilder.build());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_username) {
                stopServices(UsernameActivity.class);
            return true;
        }
        else if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            stopServices(LoginActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("=======");
        System.out.println(parent.getItemAtPosition(position));
        System.out.println("=======");
        User clickedUser = (User) parent.getItemAtPosition(position);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        moveCameraTowardUser(clickedUser);

    }

    @Override
    public boolean onMyLocationButtonClick() {
        moveCameraTowardUser(USER);

        return true;
    }
    @Override
    protected void onDestroy() {
        mDatabase.child("users").child(USER.getName()).removeValue();
        super.onDestroy();
    }

    private class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {

        }

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            System.out.println("==== Reveiver works ! ====");
            // TODO Auto-generated method stub
            double latitudeDbl = arg1.getDoubleExtra("latitude", 0);
            double longitudeDbl = arg1.getDoubleExtra("longitude", 0);
            NumberFormat formater = NumberFormat.getNumberInstance();
            formater.setMaximumFractionDigits(2);
            formater.setMinimumFractionDigits(2);
            String latitude = formater.format(latitudeDbl);
            String longitude = formater.format(longitudeDbl);
            if (USER != null){
                USER.setLatitude(Double.toString(latitudeDbl));
                USER.setLongitude(Double.toString(longitudeDbl));
                mDatabase.child("users").child(USER.getName()).setValue(USER);

            }
            if (firstTime){
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                moveCameraTowardUser(USER);
            }
            /*Toast.makeText(MapsActivity.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + latitude + " && longitude : "+ longitude,
                    Toast.LENGTH_LONG).show();
                    */

        }

    }

    @Override
    protected void onPause() {
        stopService(new Intent(DrawerActivity.this, LocationTracker.class));
        try {
            unregisterReceiver(myReceiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationTracker.ACTION);
        registerReceiver(myReceiver, intentFilter);

        startService(new Intent(this, LocationTracker.class));


    }

    public void moveCameraTowardUser(User user){
        if (user.getLongitude() != null && user.getLatitude() != null){

            LatLng pos = new LatLng(Double.parseDouble(user.getLatitude()), Double.parseDouble(user.getLongitude()));

            CameraUpdate center= CameraUpdateFactory.newLatLng(pos);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(8);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom, 500, null);
        }
    }
}
