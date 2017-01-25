package com.companion.locationapp.model;

import java.util.ArrayList;

/**
 * Created by Turni on 11/27/16.
 */

public class User {
    private String email;
    private String name;
    private String latitude;
    private String longitude;

    public User() {
        this.email = null;
        this.name = null;
        this.latitude = null;
        this.longitude = null;
    }

    public User(String latitude, String longitude, String name, String email) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEqual(User user){
        boolean latitudeT = false;
        boolean longitudeT = false;
        boolean nameT = false;
        if (this.latitude.equals(user.getLatitude())){
            latitudeT = true;
        }
        if (this.longitude.equals(user.getLongitude())) {
            longitudeT = true;
        }
        if (this.name.equals(user.getName())){
            nameT = true;
        }
        if (!latitudeT && !longitudeT && !nameT){
            return false;
        }
        else return true;

    }

    @Override
    public String toString() {
        return "Name "+ getName()+", latitude :" + getLatitude()+ ", longitude : "+getLongitude();

    }
}
