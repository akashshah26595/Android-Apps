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

public class DisplayUserData extends Activity {

    TextView textViewUsername;
    TextView textViewAge;
    TextView textViewName;
    Button btnLogout;
    String username;
    String age;
    String name;
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

        username = getIntent().getExtras().getString("username");
        age = getIntent().getExtras().getString("age");
        name = getIntent().getExtras().getString("name");

        textViewAge.setText(age);
        textViewUsername.setText(username);
        textViewName.setText(name);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
                userLocalStore.setLoggedInUser(false);
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_user_data, menu);
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
