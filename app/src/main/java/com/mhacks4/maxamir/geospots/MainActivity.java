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

    private static final String deviceAlias = "TargetDevice";
    private static final String consumerID = "GeoSpots";

    //IBMPush push;
    //IBMPushNotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IBMBluemix.initialize(this, "85c9072b-5b0d-4ae9-a24a-0f59029cf15e", "964c4118a4d2b841e98e385f337159c67ef75a38", "GeoSpots.mybluemix.net");
        IBMData dataService = IBMData.initializeService();
        IBMCloudCode.initializeService();
        IBMPush push = IBMPush.initializeService();
        //push = IBMPush.initializeService();

        setContentView(R.layout.activity_main);

        /*
        //call the push service to register the device
        push.register(deviceAlias, consumerID).continueWith(new Continuation<String,Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                if(task.isFaulted()) {
                    Exception e = task.getError();
                    //Handle failed calls
                    System.out.println("Failed registration of device");
                    e.printStackTrace();
                } else {
                    String deviceId= task.getResult();
                    //Handle successful calls
                    System.out.println("Successful registration of device");
                }
                return null;
            }
        });*/

        //define IBMPushNotificationListener behavior on receipt of a push notification

        /*
        notificationListener = new IBMPushNotificationListener() {
            @Override
            public void onReceive(final IBMSimplePushNotification message) {
                // Handle Push Notification
                System.out.println("Recieved notificiation!");
                Toast.makeText(getApplicationContext(), "Push worked", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getBaseContext(), JoinActivity.class);
                //startActivity(intent);
            }

        };*/

        //push.listen(notificationListener);



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
                Intent intent = new Intent(v.getContext(), PlayActivity.class);
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

    /*
    @Override
    protected void onResume() {
        super.onResume();
        if (push != null) {
            push.listen(notificationListener);
        }
    }*/

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
