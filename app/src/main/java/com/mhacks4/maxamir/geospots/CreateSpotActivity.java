package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mhacks4.maxamir.geospots.R;

public class CreateSpotActivity extends Activity {
    private double longitude;
    private double latitude;
    private LocationManager location_manager;
    private LocationListener location_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_spot);

        //Edit texts
        final EditText title_edit = (EditText) findViewById(R.id.titleEdit);
        final EditText question_edit = (EditText) findViewById(R.id.questionEdit);
        final EditText answer_edit = (EditText) findViewById(R.id.answerEdit);

        //Cancel button
        final Button cancel_button = (Button) findViewById(R.id.cancelButton);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Perform on click
                ((Activity) v.getContext()).finish();
            }
        });

        //Save button
        final Button save_button = (Button) findViewById(R.id.saveButton);
        save_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Perform on click
                Intent intent = new Intent();

                intent.putExtra("TITLE", title_edit.getText().toString());
                intent.putExtra("LONG", longitude);
                intent.putExtra("LAT", latitude);
                intent.putExtra("Q", question_edit.getText().toString());
                intent.putExtra("A", answer_edit.getText().toString());

                setResult(1, intent);
                finish();
            }
        });

        //Location
        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_spot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
