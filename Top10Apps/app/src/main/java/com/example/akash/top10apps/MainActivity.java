package com.example.akash.top10apps;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String download_content="";
    private Button btnViewXML;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnViewXML = (Button) findViewById(R.id.btnShowXML);
        listView = (ListView) findViewById(R.id.listView);
        //DownloadData download = new DownloadData();
       // download.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");


        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DownloadData data = new DownloadData();
               // data.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

                ParseApplication parseApplication = new ParseApplication(download_content);
                parseApplication.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                        MainActivity.this,R.layout.list_item,parseApplication.getApplications());
                listView.setAdapter(arrayAdapter);
            }
        };
        listView = (ListView) findViewById(R.id.listView);
        DownloadData download = new DownloadData();
         download.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        btnViewXML.setOnClickListener(ocl);

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

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            download_content = downloadContent(params[0]); //gives the url to download
            if (download_content == null) {
                Log.d("DownloadData", "Download Error due to no content");
            }
            return download_content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("DownloadData", "The Result is : " + s);

        }

        private String downloadContent(String urlpath) {
            StringBuilder tempBuffer = new StringBuilder();
            try {
                URL url = new URL(urlpath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int response = conn.getResponseCode(); //the error which occurs eg 404
                Log.d("DownloadData", "Download error due to error code" + response);
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charCount = 0;
                char tempCharBuffer[] = new char[500];
                while (true) {
                    charCount = isr.read(tempCharBuffer);

                    if (charCount <= 0) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(tempCharBuffer, 0, charCount));
                }

                return tempBuffer.toString();

            }
            catch (IOException e)
            {
                e.printStackTrace(); //gives extra info along with the place where error is created
                Log.d("DownloadData", "Download error. Error Message -" + e.getMessage());
            }
            catch (SecurityException e)
            {
                Log.d("DownloadData", "Security Exception. Error Message -" + e.getMessage());
            }

            return null;
        }
    }
}
