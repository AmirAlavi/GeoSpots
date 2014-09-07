package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.games.Game;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class PlayActivity extends FragmentActivity {
    private GoogleMap map;
    private LocationManager location_manager;
    private LocationListener location_listener;
    private ArrayList<LatLng> geoSpots;
    //private ArrayList<Geofence> geoFenceList;
    Haversine haversine;
    Handler h;
    int delay;
    private double my_lat;
    private double my_long;

    public PendingIntent pending_intent;
    int t = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        haversine = new Haversine();
        geoSpots = new ArrayList<LatLng>();

        //pending_intent = createPendingIntent();

        //geoFenceList = new ArrayList<Geofence>();


        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                my_lat = location.getLatitude();
                my_long = location.getLongitude();
                setMapOnUser();

                if (t == 0){
                    t++;
                    markGeoFence(my_lat, my_long, 20);

                   // buildGeofence(my_lat, my_long, 20, "t");
                }

                for(int i = 0; i < geoSpots.size(); i++) {
                    LatLng pos = geoSpots.get(i);
                    Log.d("FUCK", ""+haversine.haversine(pos.latitude, pos.longitude, my_lat, my_long));
                    if (haversine.haversine(pos.latitude, pos.longitude, my_lat, my_long) <= 50) {
                        Log.d("YES", "FINALLY");
                        //Close enough to GeoSpot
                        Intent intent = new Intent(getBaseContext(), GameActivity.class);
                        geoSpots.remove(i);

                        startActivity(intent);
                        break;
                    }
                }
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

    private void markGeoFence(double lat, double lng, double rad){
        map.addCircle(new CircleOptions()
            .center(new LatLng(lat, lng))
            .radius(rad)
            .fillColor(Color.parseColor("#B2A9F6")));

        Marker marker = map.addMarker(new MarkerOptions()
            .position(new LatLng(lat, lng))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));

        geoSpots.add(new LatLng(lat, lng));
    }

    /*private void buildGeofence(double lat, double lng, double rad, String id){

        Geofence fence = new Geofence.Builder()
                .setCircularRegion(lat, lng, (float)rad)
                .setExpirationDuration(3600000)
                .setLoiteringDelay(1000)
                .setRequestId(id)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

        geoFenceList.add(fence);
    }


    private PendingIntent createPendingIntent(){
        Intent intent = new Intent(this, GameActivity.class);
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }*/

    public class Haversine {
        public static final double R = 6372.8; // In kilometers
        public  double haversine(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        }
    }
}
