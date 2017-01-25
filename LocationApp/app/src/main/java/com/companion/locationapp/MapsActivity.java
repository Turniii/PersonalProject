package com.companion.locationapp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.companion.locationapp.model.User;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.location.Criteria.ACCURACY_FINE;
import static com.google.android.gms.maps.UiSettings.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Location mLastLocation;
    private DatabaseReference mDatabase;
    private User USER;
    private Marker mMarker, userMarker;

    private HashMap<String, Marker> markerHashMap;
    private ArrayList<User> userArrayList;
    // Request code to use when launching the resolution activity

    // Unique tag for the error dialog fragment

    // Bool to track whether the app is already resolving an error
    private LocationTracker locationTracker;
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.USER = new User();
        myReceiver = new MyReceiver();
        userArrayList = new ArrayList<>();
        markerHashMap = new HashMap<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addValueEventListener(valueEventListener);
        USER.setName(getSharedPreferences("user", 0).getString("username", ""));
        USER.setEmail(getSharedPreferences("user", 0).getString("mail", ""));
        locationTracker = new LocationTracker(MapsActivity.this);
        locationTracker.getLocation();
        startService(new Intent(this, LocationTracker.class));
        System.out.println("==== "+ locationTracker.isCanGetLocation()+ "====");
        if(locationTracker.isCanGetLocation()){

            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();

            USER.setLatitude(Double.toString(latitude));
            USER.setLongitude(Double.toString(longitude));
            mDatabase.child("users").child(USER.getName()).setValue(USER);
            NumberFormat formater = NumberFormat.getNumberInstance();
            formater.setMaximumFractionDigits(2);
            formater.setMinimumFractionDigits(2);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + formater.format(latitude) + "\nLong: " + formater.format(longitude), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Error Location", Toast.LENGTH_LONG).show();
        }
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                mDatabase.child("users").child(USER.getName()).removeValue();
                locationTracker.stopUsingGPS();
                stopService(new Intent(MapsActivity.this, LocationTracker.class));
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationTracker.ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(MapsActivity.this,
                LocationTracker.class);
        startService(intent);

        super.onStart();
    }

    @Override
    protected void onStop() {
        mDatabase.child("users").child(USER.getName()).removeValue();
        super.onStop();
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        LatLng pos = new LatLng(Double.parseDouble(USER.getLatitude()), Double.parseDouble(USER.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(pos).title("Me : "+ USER.getName()));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(pos);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        // Add a marker in Sydney and move the camera

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                System.out.println(snapshot.getValue());
                User user;
                try {
                    user = snapshot.getValue(User.class);
                    userArrayList.add(user);
                }
                catch (DatabaseException data){

                }
            }
            Location USERLocation = new Location("user loc");
            USERLocation.setLatitude(Double.parseDouble(USER.getLatitude()));
            USERLocation.setLongitude(Double.parseDouble(USER.getLongitude()));
            for (int i = 0; i < userArrayList.size(); i++){
                User tempsUser = userArrayList.get(i);
                Marker previousMarker = markerHashMap.get(tempsUser.getName());
                if (previousMarker != null){
                    previousMarker.remove();
                    markerHashMap.remove(tempsUser.getName());
                }
                Location tempLoc = new Location("tempLoc");
                tempLoc.setLongitude(Double.parseDouble(tempsUser.getLongitude()));
                tempLoc.setLatitude(Double.parseDouble(tempsUser.getLatitude()));
                double distance = USERLocation.distanceTo(tempLoc);
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMinimumFractionDigits(0);
                formatter.setMaximumFractionDigits(0);
                String distanceFmt = formatter.format(distance);
                LatLng myPos = new LatLng(Double.parseDouble(tempsUser.getLatitude()), Double.parseDouble(tempsUser.getLongitude()));
                mMarker = mMap.addMarker(new MarkerOptions().position(myPos).title("User : "+ tempsUser.getName()).snippet("Distance to : "+ distanceFmt + " m"));
                markerHashMap.put(tempsUser.getName(), mMarker);
            }
            System.out.println("==== Data size : "+userArrayList.size() +" ====");
            System.out.println();
            userArrayList = new ArrayList<>();


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


  /*  @Override
    public void onLocationChangeCallback(Location location) {
        System.out.println("=== Location changed "+ location + " ====");
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(pos).title("Me : "+ USER.getName()));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(pos);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

    }*/
    private class MyReceiver extends BroadcastReceiver {
      public MyReceiver (){

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
            USER.setLatitude(Double.toString(latitudeDbl));
            USER.setLongitude(Double.toString(longitudeDbl));
            mDatabase.child("users").child(USER.getName()).setValue(USER);
            if (userMarker != null){
                userMarker.remove();
            }
            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitudeDbl, longitudeDbl)).title("Me : "+ USER.getName()));
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            /*Toast.makeText(MapsActivity.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + latitude + " && longitude : "+ longitude,
                    Toast.LENGTH_LONG).show();
                    */

        }

    }

    @Override
    protected void onDestroy() {
        locationTracker.stopUsingGPS();
        stopService(new Intent(MapsActivity.this, LocationTracker.class));
        stopService(new Intent(MapsActivity.this, LocationTracker2.class));
        mDatabase.child("users").child(USER.getName()).removeValue();
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }


}
