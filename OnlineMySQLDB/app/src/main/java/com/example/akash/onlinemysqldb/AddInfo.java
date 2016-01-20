package com.example.akash.onlinemysqldb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddInfo extends AppCompatActivity {
    EditText name,mobile,email;
    String Name , Email , Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_info_layout);
        name = (EditText)findViewById(R.id.et_name);
        email = (EditText)findViewById(R.id.et_email);
        mobile= (EditText)findViewById(R.id.et_phone);

    }
    public void saveInfo(View view){
        Name = name.getText().toString();
        Email = email.getText().toString();
        Mobile = mobile.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(Name,Email,Mobile);
        finish();
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        String add_info_url = "";
        @Override
        protected void onPreExecute() {
            add_info_url = "http://swasth-india.esy.es/add_info.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String name,email,mobile;
            name = args[0];
            email = args[1];
            mobile = args[2];

            try{
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("name" , "UTF-8") + "=" + URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email" , "UTF-8") + "=" + URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("mobile" , "UTF-8") + "=" + URLEncoder.encode(mobile,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return  "One row Inserted..";

            }
            catch (MalformedURLException ex){

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result , Toast.LENGTH_LONG).show();
        }
    }


}
