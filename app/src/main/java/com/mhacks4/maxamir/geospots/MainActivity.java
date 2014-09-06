package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create button
        final Button create_button = (Button) findViewById(R.id.createButton);
        create_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Performed when button is clicked
                Intent intent = new Intent(v.getContext(), CreateMenuActivity.class);
                startActivity(intent);
            }
        });

        //Join button
        final Button join_button = (Button) findViewById(R.id.joinButton);
        join_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Performed when the button is clicked
                //Intent to open JoinActivity
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
