package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.esr14.signupplay.settings.SettingsActivity;
import com.example.esr14.signupplay.util.AbstractActivity;

public class Display extends AbstractActivity {

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
