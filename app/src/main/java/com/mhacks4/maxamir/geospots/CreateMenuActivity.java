package com.mhacks4.maxamir.geospots;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ibm.mobile.services.data.IBMDataObject;
import com.mhacks4.maxamir.geospots.R;

import java.util.ArrayList;
import java.util.Vector;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import bolts.Continuation;
import bolts.Task;

public class CreateMenuActivity extends Activity {
    private Vector<Spot> spots;
    ArrayList<String> items;
    ArrayAdapter<String> array_adapter;
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        spots = new Vector<Spot>();

        array_adapter = new ArrayAdapter<String>(this, R.layout.spot_row, items);
        list_view = (ListView) findViewById(R.id.listView);
        list_view.setAdapter(array_adapter);


        //Remove row on click
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String removed_title = items.remove(position);

                for (int i = 0; i < spots.size(); i++){
                    if (spots.elementAt(i).getTitle() == removed_title){
                        spots.remove(i);
                        break;
                    }
                }

                array_adapter.notifyDataSetChanged();
            }
        });

        //Add Button
        final Button add_button = (Button) findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Action performed when clicked
                Intent intent = new Intent(v.getContext(), CreateSpotActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
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
        if (data == null) return;
        if (requestCode == 1){
            Spot new_spot = new BasicQASpot(
                    data.getStringExtra("TITLE"),
                    data.getDoubleExtra("LONG", 0),
                    data.getDoubleExtra("LAT", 0),
                    data.getStringExtra("Q"),
                    data.getStringExtra("A")
            );

            items.add(data.getStringExtra("TITLE"));
            array_adapter.notifyDataSetChanged();
        }
	}

    public void finishOnClick (View view) {
        System.out.println("in finishOnClick");

        for (Spot aSpot : spots) {
            System.out.println("in loop, before save");
            aSpot.save().continueWith(new Continuation<IBMDataObject, Object>() {
                @Override
                public Object then(Task<IBMDataObject> ibmDataObjectTask) throws Exception {
                    return null;
                }
            });
            System.out.println("in loop, after save");
        }
        //createDoc(spots.elementAt(0).getTitle(), spots.elementAt(0).getLatitude(), spots.elementAt(0).getLongitude(), ((BasicQASpot)spots.elementAt(0)).getQuestion(), ((BasicQASpot)spots.elementAt(0)).getAnswer());
    }
/*
    public void createDoc (String title, double latitude, double longitude, String prompt, String answer) {
        JSONObject myObj = new JSONObject();
        try {
            myObj.put("_id", title);
            myObj.put("latitude", latitude);
            myObj.put("longitude", longitude);
            myObj.put("prompt", prompt);
            myObj.put("answer", answer);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new LongOperation().execute(myObj.toString());

    }
*/

    /*
    private class LongOperation extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... json) {
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            HttpResponse response;

            try {
                HttpPost post = new HttpPost("https://amiralavi.cloudant.com/geospots");
                StringEntity se = new StringEntity(json[0]);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
                response = client.execute(post);
                if (response != null) {

                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }*/

}
