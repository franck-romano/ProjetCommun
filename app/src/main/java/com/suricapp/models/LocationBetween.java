package com.suricapp.models;

import android.location.Location;

/**
 * Created by maxence on 31/03/15.
 */
public class LocationBetween {


    // First point from between
    private double latitudePoint1;
    private double longitudePoint1;
    // Second point from between
    private double latitudePoint2;
    private double longitudePoint2;

    public LocationBetween() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LAT 1 = "+latitudePoint1+" LONG1 = "+longitudePoint1);
        sb.append("LAT2 = "+latitudePoint2+" LONG2 = "+longitudePoint2);
        return sb.toString();
    }

    public LocationBetween(double latitudePoint1, double longitudePoint1, double latitudePoint2, double longitudePoint2) {
        this.latitudePoint1 = latitudePoint1;
        this.longitudePoint1 = longitudePoint1;
        this.latitudePoint2 = latitudePoint2;
        this.longitudePoint2 = longitudePoint2;
    }

    public double getLatitudePoint1() {
        return latitudePoint1;
    }

    public void setLatitudePoint1(double latitudePoint1) {
        this.latitudePoint1 = latitudePoint1;
    }

    public double getLongitudePoint1() {
        return longitudePoint1;
    }

    public void setLongitudePoint1(double longitudePoint1) {
        this.longitudePoint1 = longitudePoint1;
    }

    public double getLatitudePoint2() {
        return latitudePoint2;
    }

    public void setLatitudePoint2(double latitudePoint2) {
        this.latitudePoint2 = latitudePoint2;
    }

    public double getLongitudePoint2() {
        return longitudePoint2;
    }

    public void setLongitudePoint2(double longitudePoint2) {
        this.longitudePoint2 = longitudePoint2;
    }
}
