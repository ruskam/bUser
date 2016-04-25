package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    EditText usernameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = (EditText) findViewById(R.id.loginEditText);
        passwordET = (EditText) findViewById(R.id.passEditText);

        usernameET.setText("");
        passwordET.setText("");
    }

    public void registerUser(View view) {
        if (view.getId() == R.id.btnRegister) {
            Intent registerIntent = new Intent(MainActivity.this, Register.class);
            startActivity(registerIntent);
        }
    }

    public void displayUser(View view) {

        if (view.getId() == R.id.btnLogin) {

            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();

            String passwordFromDB = databaseHelper.searchPassword(username);


            if (usernameET.getText().toString().length() != 0
                    && password.equals(passwordFromDB)) {
                Intent loginIntent = new Intent(MainActivity.this, BusStopAct.class);

                loginIntent.putExtra("username", username);
                loginIntent.putExtra("password", password);

                startActivity(loginIntent);
            } else if (usernameET.getText().toString().length() == 0
                    && passwordET.getText().toString().length() != 0) {
                usernameET.setError("Username is required!");
            } else if (passwordET.getText().toString().length() == 0
                    && usernameET.getText().toString().length() != 0) {
                passwordET.setError("Password is required!");
            } else if (passwordET.getText().toString().length() == 0
                    && usernameET.getText().toString().length() == 0) {
                usernameET.setError("Username is required!");
                passwordET.setError("Password is required!");
            }
            else {
                Toast warning = Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT);
                warning.show();
            }

            Log.i("Data exist?", databaseHelper.dataExist().toString());

        }
    }

    public void cleanData(View view) {
        if (view.getId() == R.id.cleanData) {
            databaseHelper.removeAllUsers("user");
        }
    }
}
