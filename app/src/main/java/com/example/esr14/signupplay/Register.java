package com.example.esr14.signupplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    EditText usernameET;
    EditText passwordET;
    EditText confirmedPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    public void onRegisterClick(View view) {

        usernameET = (EditText) findViewById(R.id.editTextUserName);
        passwordET = (EditText) findViewById(R.id.editTextPassword);
        confirmedPasswordET = (EditText) findViewById(R.id.editTextConfirmPassword);

        if( usernameET.getText().toString().length() == 0 )
            usernameET.setError( "Username is required!" );

        if( passwordET.getText().toString().length() == 0 )
            passwordET.setError( "Password is required!" );

        if( confirmedPasswordET.getText().toString().length() == 0 )
            confirmedPasswordET.setError( "Password is required!" );

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmedPassword = confirmedPasswordET.getText().toString();

        String passwordFromDB = databaseHelper.searchPassword(username);

        if (!password.equals(confirmedPassword)) {
            Toast warning = Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT);
            warning.show();
        } else if (!databaseHelper.available(username)) {
            Toast warning = Toast.makeText(Register.this, "Username " + username + " occupied", Toast.LENGTH_SHORT);
            warning.show();
        } else {
            User user = new User(username, password);
            databaseHelper.insertUser(user);

            Toast welcome = Toast.makeText(Register.this, username + ", you have been registered", Toast.LENGTH_SHORT);
            welcome.show();

            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);

        }

    }
}
