package com.example.akash.loginregister;

import android.app.Activity;
import android.app.AlertDialog;
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

public class Login extends Activity implements View.OnClickListener {
    EditText editTextUsername;
    EditText editTextPassword;
    TextView textView;
    Button btnLogin;
    String username;
    String password;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPassword = (EditText)findViewById(R.id.etPassword2);
        editTextUsername = (EditText)findViewById(R.id.etUsername2);
        textView = (TextView)findViewById(R.id.textView);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        userLocalStore = new UserLocalStore(this);
        btnLogin.setOnClickListener(this);
        textView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnLogin:

                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                User user = new User(username,password);

                Log.d("test","Password credential given by user.." + user.password);
                Log.d("test","Username credential given by user.." + user.username);
                authenticate(user);
                break;

            case R.id.textView:
                startActivity(new Intent(this,Register.class));
                break;
        }
    }

    private void authenticate(final User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null){
                    showErrorMessage();
                }
                else{
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
        dialog.setMessage("Incorrect user details");
        dialog.setPositiveButton("Ok",null);
        dialog.show();
    }
    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setLoggedInUser(true);
        Intent intent = new Intent(this,DisplayUserData.class);
        intent.putExtra("username",returnedUser.username);
        intent.putExtra("name",returnedUser.name);
        intent.putExtra("age",returnedUser.age);

        startActivity(intent);
    }

}
