package com.example.akash.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity implements View.OnClickListener {
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextAge;
    EditText editTextName;
    Button button;
    String username;
    String password;
    TextView textView;
    String age;
    String name;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextAge = (EditText) findViewById(R.id.etAge1);
        editTextName = (EditText) findViewById(R.id.etName1);
        editTextUsername = (EditText) findViewById(R.id.etUsername1);
        editTextPassword = (EditText) findViewById(R.id.etPassword1);
        button = (Button) findViewById(R.id.btnRegister);
        textView = (TextView)findViewById(R.id.txtLogin);

        userLocalStore = new UserLocalStore(this);
        textView.setOnClickListener(this);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:

                age = editTextAge.getText().toString();
                password = editTextPassword.getText().toString();
                username = editTextUsername.getText().toString();
                name = editTextName.getText().toString();

                User user = new User(name,username,password,age);
                Log.d("test","Value of username:" + username);
                Log.d("test","Value of password:" + password);
                Log.d("test","Value of name:" + name);
                Log.d("test","Value of age:" + age);
                registerUser(user);
                //userLocalStore.storeUserData(user);
                break;
            case R.id.txtLogin:
                startActivity(new Intent(this,Login.class));
                break;
        }
    }
    private void  registerUser(User user){
            ServerRequests  serverRequests = new ServerRequests(this);
            serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
                @Override
                public void done(User user) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
            });
    }
}
