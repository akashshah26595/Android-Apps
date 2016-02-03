package com.example.akash.loginregister;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends Activity {


    EditText editTextPassword;
    EditText editTextAge;
    EditText editTextName;
    Button button;

    String password;
    int age;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextAge = (EditText) findViewById(R.id.etAge);
        editTextName = (EditText) findViewById(R.id.etName);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        button = (Button)findViewById(R.id.btnUpdate);
        age = Integer.parseInt(editTextAge.getText().toString());
        password = editTextPassword.getText().toString();

        name = editTextName.getText().toString();
    }
}