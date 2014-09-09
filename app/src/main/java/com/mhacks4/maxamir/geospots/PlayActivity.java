package com.mhacks4.maxamir.geospots;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class PlayActivity extends FragmentActivity {
    private List<Spot> objects;
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

                System.out.println("GEOSPOTS" + geoSpots.size());
                for(int i = 0; i < geoSpots.size(); i++) {
                    LatLng pos = geoSpots.get(i);
                    System.out.println(haversine.haversine(pos.latitude, pos.longitude, my_lat, my_long) <= 20);
                    if (haversine.haversine(pos.latitude, pos.longitude, my_lat, my_long) <= 50) {
                        BasicQASpot spot = new BasicQASpot("", 1.0, 1.0);

                        System.out.println(objects==null);

                        if (objects == null){

                            break;
                        }else{

                        for (Spot b: objects){
                            if ((b.getLatitude() == pos.latitude) && (b.getLongitude() == pos.longitude)){
                                spot = (BasicQASpot) b;
                                break;
                            }
                        }}

                        System.out.print("WEHFOWUHE:OHGWER:OTHWE:OHWGPOEHW:OUH");
                        //Close enough to GeoSpot
                        Intent intent = new Intent(getBaseContext(), GameActivity.class);
                        geoSpots.remove(i);

                        intent.putExtra("Q", spot.getQuestion());
                        intent.putExtra("A", spot.getAnswer());
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

        IBMQuery<Spot> queryByClass = null;
        try {
            queryByClass = IBMQuery.queryForClass(Spot.class);
        } catch(Exception e) {
            System.out.println("caught query exception");
            e.printStackTrace();

            finish();
        }

        try {
            queryByClass.find().continueWith(new Continuation<List<Spot>, Void>() {

                @Override
                public Void then(Task<List<Spot>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("query failed");
                    } else {
                        // do more work
                        System.out.println("query success");
                        objects = task.getResult();

                        System.out.println(objects.size());

                        for (Spot aspot : objects) {
                            markGeoFence(aspot.getLatitude(), aspot.getLongitude(), 20);
                        }
                    }
                    return null;
                }
            });
        }catch(Exception e) {}


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
