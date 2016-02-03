package com.example.akash.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 26/1/16.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://swasth-india.esy.es/";
    public String JSON_DATA;
    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    public void storeUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        StoreUserDataAsyncTask storeUserDataAsyncTask = new StoreUserDataAsyncTask(user, callback);
        storeUserDataAsyncTask.execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        FetchUserDataAsyncTask fetchUserDataAsyncTask = new FetchUserDataAsyncTask(user, callback);
        fetchUserDataAsyncTask.execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback callback;

        public StoreUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("name", user.name);
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);
            dataToSend.put("age", user.age);

            Log.d("test", "Username is " + user.username);
            Log.d("test", "Name is " + user.name);
            Log.d("test", "Password is " + user.password);
            Log.d("test","Age is " + user.age);

            String encodedStr = getEncodedData(dataToSend);
            String data_string="";

            //Will be used if we want to read some data from server
            BufferedReader reader = null;
            HttpURLConnection con = null;
            User returnedUser = null;
            try {
                //http://swasth-india.esy.es/testLoginReg/android_register.php
                URL url = new URL(SERVER_ADDRESS + "testLoginReg/android_register.php");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);

                data_string = URLEncoder.encode("name" , "UTF-8") + "=" + URLEncoder.encode(user.name,"UTF-8")+"&"+
                        URLEncoder.encode("username" , "UTF-8") + "=" + URLEncoder.encode(user.username,"UTF-8")+"&"+
                        URLEncoder.encode("password" , "UTF-8") + "=" + URLEncoder.encode(user.password,"UTF-8")+"&"+
                        URLEncoder.encode("age" , "UTF-8") + "=" + URLEncoder.encode(user.age,"UTF-8");

                //OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = con.getInputStream();
                inputStream.close();

/*
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
               while ((line = reader.readLine()) != null) { //Read till there is something available
                    sb.append(line + "\n");     //Reading and saving line by line - not all at once
                }
                line = sb.toString();           //Saving complete data received in string, you can do it differently

                */
          /*
          * URL url = new URL(add_info_url);
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
                httpURLConnection.disconnect();          *
          * */
                Log.i("custom_check", "The values received in the store part are as follows:");
                //Log.i("custom_check", line);
            }

            catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(avoid);
        }
    }

    private String getEncodedData(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }


    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback callback;
        JSONObject jsonObject;
        JSONArray jsonArray;
        String json_data;
        public FetchUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected User doInBackground(Void... params) {

            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);


            String encodedStr = getEncodedData(dataToSend);

            //Will be used if we want to read some data from server
            BufferedReader reader = null;
            HttpURLConnection con = null;
            User returnedUser = null;
            String name;
            String username;
            String age;
            String password;
            try {
                //http://swasth-india.esy.es/testLoginReg/android_getUserData.php
                URL url = new URL(SERVER_ADDRESS + "testLoginReg/android_getUserData.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("username" , "UTF-8") + "=" + URLEncoder.encode(user.username,"UTF-8")+"&"+
                        URLEncoder.encode("password" , "UTF-8") + "=" + URLEncoder.encode(user.password,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_DATA = reader.readLine())!=null){
                        stringBuilder.append(JSON_DATA);
                }
                reader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                json_data = stringBuilder.toString();

                jsonObject = new JSONObject(json_data);

                if (jsonObject.length() == 0) {
                    returnedUser = null;
                }
                else {
                    username = jsonObject.getString("username");
                    password = jsonObject.getString("password");
                    name = jsonObject.getString("name");
                    age = jsonObject.getString("age");

                    returnedUser = new User(name,username,password,age);
                }
                Log.d("test","User Login Credentials........" + stringBuilder.toString());
                /*
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(encodedStr);
                writer.flush();
                writer.close();

                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) { //Read till there is something available
                    sb.append(line);     //Reading and saving line by line - not all at once
                }
                line = sb.toString();           //Saving complete data received in string, you can do it differently

                JSONObject jsonObject = new JSONObject(line);
                if (jsonObject.length() == 0) {
                    returnedUser = null;
                } else {
                    String name = jsonObject.getString("name");
                    String age = jsonObject.getString("age");
                    returnedUser = new User(name, user.username, user.password, age);
                }
*/
                //Just check to the values received in Logcat
                Log.i("custom_check", "The values received in the store part are as follows:");
               // Log.i("custom_check", line);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //con.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return returnedUser;

        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            callback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }
}



