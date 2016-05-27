package com.example.esr14.signupplay;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.esr14.signupplay.settings.SettingsActivity;
import com.example.esr14.signupplay.util.AbstractActivity;

public class insideBus extends AbstractActivity {

    boolean isConnected = false;
    int i = 0, lineSize = 0;
    RadioGroup stoplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_bus);
        isConnected = connect();
        stoplist = (RadioGroup) findViewById(R.id.stop_group);
        lineSize = stoplist.getChildCount();
    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    public void stopBus(View view) {
        if(!isConnected)
            connect();
        //startActivity(new Intent(this, StopBus.class));
        if (ledon)
            turnOffLed(); //method to turn off
        else
            turnOnLed(); //method to turn on
    }

    @Override
    public void nextStop(){
        i++;
        if(i<lineSize)
        {
            View child = stoplist.getChildAt(i);
            if(child instanceof RadioButton)
                ((RadioButton)child).setChecked(true);
        }

    }

}