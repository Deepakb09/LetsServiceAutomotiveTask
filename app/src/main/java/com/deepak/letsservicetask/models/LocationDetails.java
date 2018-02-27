package com.deepak.letsservicetask.models;

/**
 * Created by Deepak on 27-Feb-18.
 */

public class LocationDetails {

    String latitude;
    String longitude;
    String speed;
    String time;

    public LocationDetails(String latitude, String longitude, String speed, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.time = time;
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

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
