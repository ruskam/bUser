package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.esr14.signupplay.settings.SettingsActivity;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        TextView hello = (TextView) findViewById(R.id.textViewDiplay);
        hello.setText("Hello " + username + "; password:" + password);
    }

    public void displaySettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
