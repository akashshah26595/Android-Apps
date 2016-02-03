package com.example.akash.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView textViewUsername;
    TextView textViewAge;
    TextView textViewName;
    Button btnLogout;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startActivity(new Intent(this, Login.class));

        textViewAge = (TextView)findViewById(R.id.textViewAge);
        textViewUsername = (TextView)findViewById(R.id.textViewUsername);
        textViewName = (TextView)findViewById(R.id.textViewName);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        userLocalStore = new UserLocalStore(this);

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userLocalStore.clearUserData();
//                userLocalStore.setLoggedInUser(false);
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
                userLocalStore.setLoggedInUser(false);
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        if(authenticate()){
            displayData();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
        }



    }

    public Boolean authenticate(){
       return userLocalStore.getLoginStatus();
    }
    public void displayData(){
        User user = userLocalStore.getLoggedInUser();
        textViewAge.setText(user.age);
        textViewName.setText(user.name);
        textViewUsername.setText(user.username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
