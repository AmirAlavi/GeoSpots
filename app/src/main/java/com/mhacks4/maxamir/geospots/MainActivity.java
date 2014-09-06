package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.push.IBMPush;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IBMBluemix.initialize(this, "85c9072b-5b0d-4ae9-a24a-0f59029cf15e", "964c4118a4d2b841e98e385f337159c67ef75a38", "mybluemix.net");
        IBMCloudCode.initializeService();
        IBMData dataService = IBMData.initializeService();
//        IBMPush.initializeService();

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
                Intent intent = new Intent(v.getContext(), JoinActivity.class);
                startActivity(intent);
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
