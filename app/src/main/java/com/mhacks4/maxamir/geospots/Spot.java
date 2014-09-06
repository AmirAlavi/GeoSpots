package com.mhacks4.maxamir.geospots;

import android.os.Parcel;
import android.os.Parcelable;

public class Spot{
    private String title;

    private double longitude;
    private double latitude;

    Spot(String title, double longitude, double latitude){
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    String getTitle(){
        return title;
    }

    double getLongitude(){
        return longitude;
    }

    double getLatitude(){
        return latitude;
    }

}
