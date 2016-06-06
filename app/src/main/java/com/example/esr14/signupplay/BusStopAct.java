package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esr14.signupplay.Model.BusStop;
import com.example.esr14.signupplay.settings.SettingsActivity;
import com.example.esr14.signupplay.util.AbstractActivity;
import com.example.esr14.signupplay.util.MyTime;

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
public class BusStopAct extends AbstractActivity {

    TextView tvNextBusTime1;
    TextView tvNextBusTime2;
    TextView tvBusLine1;
    TextView tvBusLine2;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        tvNextBusTime1 = (TextView) findViewById(R.id.tvNextBusTime1);
        tvBusLine1 = (TextView) findViewById(R.id.tvBusLine1);
        tvNextBusTime2 = (TextView) findViewById(R.id.tvBusLine2);
        tvBusLine2 = (TextView) findViewById(R.id.tvBusLine2);
        retry = (Button) findViewById(R.id.button_retry);

      //  connect();
        if (isBtConnected) {
            msg("ok");
            retry.setText("Connected");
        }
        else {
            msg("Failed to connect");
        }
        retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                msg("Retrying to connect");
                if(connect())
                    retry.setText("Connected");

            }
        });
    }
    @Override
    public void detectedCard() {
        startActivity(new Intent(this,insideBus.class));
    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void getInsideBus(View view) {
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
                final String url = "http://172.20.10.13:8080/api/busstop/stopname=Damaia";
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
            try {
                Log.i("info", busStop.getStopName());

                List<String> schedule = busStop.getSchedule();
                List<Integer> itemsInteger = new ArrayList<>();
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
                            valString = valString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
                            List<String> items = Arrays.asList(valString.split(","));

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
                            val = val.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
                            Log.i("val", val);
                            List<String> items = Arrays.asList(val.split(","));
                            linesMap.put(key, items);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int curH = 8;
                int curM = 18;
                int numberOfNextBuses = 3;
                String timeText1 = "Next bus in: ";
                List<Integer> times = MyTime.getNextBusTime(numberOfNextBuses, curH, curM, itemsInteger);
                for (int i = 0; i < times.size(); i++) {
                    Log.i("times", MyTime.intToHourMinute(times.get(i))[0] + ":" + MyTime.intToHourMinute(times.get(i))[1]);
                    int difference = MyTime.getTimeDifference(MyTime.timeToInt(curH, curM), times.get(i));
                    Log.i("times in minutes", MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1]);
                    if (i != times.size() - 1) {
                        timeText1 = timeText1 + MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1] + ", ";
                    } else {
                        timeText1 = timeText1 + MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1] + " minutes";
                    }
                }
                tvNextBusTime1.setText(timeText1);

                String timeText2 = "Next bus in: ";
                List<Integer> times2 = MyTime.getNextBusTime(numberOfNextBuses, curH, curM, itemsInteger);
                for (int i = 0; i < times.size(); i++) {
                    Log.i("times", MyTime.intToHourMinute(times.get(i))[0] + ":" + MyTime.intToHourMinute(times.get(i))[1]);
                    int difference = MyTime.getTimeDifference(MyTime.timeToInt(curH, curM), times.get(i));
                    Log.i("times in minutes", MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1]);
                    if (i != times.size() - 1) {
                        timeText1 = timeText1 + MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1] + ", ";
                    } else {
                        timeText1 = timeText1 + MyTime.intToHourMinute(difference)[0] + ":" + MyTime.intToHourMinute(difference)[1] + " minutes";
                    }
                }
                tvNextBusTime2.setText(timeText1);


                String[] lineNumbers = new String[2];
                List<Integer> lineSchedule = new ArrayList<>();

                int l = 0;
                for (Map.Entry<String, List<Integer>> entry : scheduleMap.entrySet()) {

                    lineNumbers[l] = entry.getKey();
                    l++;

                }
                tvBusLine1.setText(lineNumbers[0]);
                tvBusLine2.setText(lineNumbers[1]);
                // tvNextBusTime1
                // tvNextBusTime1

                for (Map.Entry<String, List<String>> entry : linesMap.entrySet()) {

                    List<String> temp = entry.getValue();
                    for (String t : temp) {

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Services is not available!", Toast.LENGTH_SHORT).show();
            }


        }

    }
}
