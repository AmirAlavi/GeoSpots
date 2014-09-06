package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mhacks4.maxamir.geospots.R;

import java.util.Vector;

public class CreateMenuActivity extends Activity {
    Vector<Spot> spots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        spots = new Vector<Spot>();

        //Add Button
        final Button add_button = (Button) findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Action performed when clicked
                Intent intent = new Intent(v.getContext(), CreateSpotActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_menu, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            Spot new_spot = new BasicQASpot(
                    data.getStringExtra("TITLE"),
                    data.getDoubleExtra("LONG", 0),
                    data.getDoubleExtra("LAT", 0),
                    data.getStringExtra("Q"),
                    data.getStringExtra("A")
            );

            spots.add(new_spot);
        }
    }

}
