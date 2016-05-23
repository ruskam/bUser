package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.esr14.signupplay.Model.BusStop;
import com.example.esr14.signupplay.settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is called after a user is logged in
 */
public class BusStopAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void getInsideBus(View view){
        startActivity(new Intent(this, insideBus.class));
    }

    /**
     * Send a GET request to read data from the database Web API
     */
    private class HttpRequestTask extends AsyncTask<Void, Void, BusStop> {
        @Override
        protected BusStop doInBackground(Void... params) {
            try {
                //final String url = "http://rest-service.guides.spring.io/greeting";
                //final String url = "http://10.0.2.2:8080/people/search/findByUsername?name=wife";
                final String url = "http://10.0.2.2:8080/api/person/username=programmer";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                BusStop busStop = restTemplate.getForObject(url, BusStop.class);
                return busStop;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(BusStop busStop) {

            List<String> lines = busStop.getLines();
            int s = lines.size();
            Log.i("List size", String.valueOf(s));
            JSONObject linesObject = null;
            Map<String, String> linesMap = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                Log.i("line " + i, String.valueOf(lines.get(i)));
                Log.i("line " + i, String.valueOf(lines.get(i)));

                try {
                    linesObject = new JSONObject(lines.get(i));
                    Iterator<?> keys = linesObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.i("key", key);
                        String val = linesObject.getString(key);
                        Log.i("val", val);
                        linesMap.put(key, val);
                        /**
                         if(linesObject.get(key) instanceof JSONObject) {
                         String value = linesObject.getString(key);
                         Log.i("stops", value);
                         }
                         */
                    }
                    //String stops = linesObject.getString("Line767");
                    //Log.i("stops", stops);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (Map.Entry<String, String> entry : linesMap.entrySet()) {
                Log.i("map", entry.getKey() + ": " + entry.getValue());
            }

        }
    }
}
