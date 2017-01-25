package com.companion.locationapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;


/**
 * Created by Turni on 1/9/17.
 */

public class LocationTracker extends Service implements LocationListener {

    private final Context context;
    private final LocationListener locationListener;
    Location location;
    double latitude;
    double longitude;
    public final static String ACTION = "NEWLOCATION";

    private static final float DISTANCE = 10;
    private static final long TIME = 6000;

    public LocationTracker() {
        super();
        this.context = this;
        this.locationListener = this;
    }

    public LocationTracker(Context context) {
        this.context = context;
        this.locationListener = this;
        System.out.println("==== LOCATION Tracker created =====");
        getLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("==== LOCATION Tracker Started =====");
        getLocation();
        return super.onStartCommand(intent, flags, startId);

    }

    protected LocationManager locationManager;
    boolean gpsEnabled;
    boolean networkEnabled;
    boolean canGetLocation;

    public Location getLocation() {
        try {
            System.out.println("==== LOCATION SERVICE =====");

            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gpsEnabled && !networkEnabled) {
            } else {
                this.canGetLocation = true;
                if (networkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME, DISTANCE, this);
                    if (locationManager != null) {


                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (gpsEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME, DISTANCE, this);


                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (location != null){
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        context.sendBroadcast(intent);
        }

        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
            if (location != null){
                System.out.println("==== Location changed ====");
                Intent intent = new Intent();
                intent.setAction(ACTION);
                intent.putExtra("latitude", location.getLatitude());
                intent.putExtra("longitude", location.getLongitude());
                context.sendBroadcast(intent);
            }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public double getLatitude(){
        if ( location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }
    public double getLongitude(){
        if ( location != null){
            longitude = location.getLatitude();
        }
        return longitude;
    }
}
