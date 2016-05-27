package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.esr14.signupplay.Model.BusStop;
import com.example.esr14.signupplay.settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is called after a user is logged in
 */
public class BusStopAct extends AppCompatActivity {

    TextView tvNextBusTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        tvNextBusTime = (TextView) findViewById(R.id.tvNextBusTime);

    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void getInsideBus(View view){
        startActivity(new Intent(this, insideBus.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        new HttpRequestTask().execute();
    }

    /**
     * Send a GET request to read data from the database Web API
     */
    private class HttpRequestTask extends AsyncTask<Void, Void, BusStop> {

        @Override
        protected BusStop doInBackground(Void... params) {
            try {
                //final String url = "http://10.0.2.2:8080/api/busstop/near/lng=-9.2037&lat=38.660&d=108";
                //final String url = "http://10.0.2.2:8080/api/busstop/all";
                final String url = "http://10.0.2.2:8080/api/busstop/stopname=Damaia";
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
            try{
                Log.i("info", busStop.getStopName());

                List<String> schedule = busStop.getSchedule();
                Map<String, List<Integer>> scheduleMap = new HashMap<>();
                JSONObject scheduleObject = null;
                for (int i = 0; i < schedule.size(); i++) {
                    Log.i("line " + i, schedule.get(i));
                    try {
                        scheduleObject = new JSONObject(schedule.get(i));
                        Iterator<?> keys = scheduleObject.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Log.i("key", key);
                            String valString = scheduleObject.getString(key);
                            //Integer val = Integer.valueOf(valString);
                            //Log.i("val", val.toString());
                            //valString = valString.replaceAll("[^0-9]","");
                            valString = valString.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","");
                            List<String> items = Arrays.asList(valString.split(","));
                            List<Integer> itemsInteger = new ArrayList<>();
                            for (int k = 0; k < items.size(); k++) {
                                Integer val = Integer.valueOf(items.get(k).trim());
                                itemsInteger.add(val);
                            }
                            Log.i("one hour", items.get(0));
                            scheduleMap.put(key, itemsInteger);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> lines = busStop.getLines();
                int s = lines.size();
                Log.i("List size", String.valueOf(s));
                JSONObject linesObject = null;
                Map<String, List<String>> linesMap = new HashMap<>();
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
                            val = val.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","");
                            Log.i("val", val);
                            List<String> items = Arrays.asList(val.split(","));
                            linesMap.put(key, items);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
/**
                for (Map.Entry<String, List<String>> entry : linesMap.entrySet()) {
                    Log.i("mapLINES", entry.getKey() + ": " + entry.getValue());
                    List<String> temp = entry.getValue();
                    for (String t: temp){
                        Log.i("element of stop", t);
                    }
                }
                for (Map.Entry<String, List<Integer>> entry : scheduleMap.entrySet()) {
                    Log.i("mapSchedule", entry.getKey() + ": " + entry.getValue());
                    List<Integer> temp = entry.getValue();
                    for (Integer t: temp){
                        Log.i("element of time", t.toString());
                    }
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
