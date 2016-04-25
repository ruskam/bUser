package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.esr14.signupplay.settings.SettingsActivity;
public class insideBus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_bus);
    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
    public void stopBus(View view){
        startActivity(new Intent(this, StopBus.class));
    }
}