package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class PlayActivity extends FragmentActivity {
    private GoogleMap map;
    private LocationManager location_manager;
    private LocationListener location_listener;
    private double my_lat;
    private double my_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                my_lat = location.getLatitude();
                my_long = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        location_manager.requestLocationUpdates(location_manager.NETWORK_PROVIDER, 0, 0, location_listener);
    }

    private void setMapOnUser(){
        CameraPosition camera_pos = new CameraPosition.Builder()
                .target(new LatLng(my_lat, my_long))
                .zoom(15)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(camera_pos));
    }

}
