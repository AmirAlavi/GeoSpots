package com.mhacks4.maxamir.geospots;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("Spot")
public class Spot extends IBMDataObject{
    private static final String TITLE = "title";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

//    private double longitude;
//    private double latitude;

    public Spot(){
        super();
    }

    public Spot(String title, double longitude, double latitude){
        setTitle(title);
        setLongitude(longitude);
        setLatitude(latitude);
        /*
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        */
    }

    public String getTitle(){
        return (String) getObject(TITLE);
    }

    public void setTitle(String spotName) {
        setObject(TITLE, (spotName != null) ? spotName : "");
    }

    public double getLongitude(){
        return (Double) getObject(LONGITUDE);
    }

    public void setLongitude(double spotLong){
        setObject(LONGITUDE, spotLong);
    }

    public double getLatitude(){
        return (Double) getObject(LATITUDE);
    }

    public void setLatitude(double spotLat){
        setObject(LATITUDE, spotLat);
    }

}
