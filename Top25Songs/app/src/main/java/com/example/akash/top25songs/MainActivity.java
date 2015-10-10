package com.example.akash.top25songs;

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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String download_content;
    private Button btnShowXML;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowXML = (Button)findViewById(R.id.btnShow);
        listView = (ListView)findViewById(R.id.listView);
        DownloadData data = new DownloadData();
        data.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=50/xml");

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplication parseApplication = new ParseApplication(download_content);
                parseApplication.process();
                ArrayAdapter<Applications> arrayAdapter = new ArrayAdapter<Applications>(
                        MainActivity.this,R.layout.list_view,parseApplication.getArrayList());
                listView.setAdapter(arrayAdapter);

            }
        };
        btnShowXML.setOnClickListener(ocl);

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
    private class DownloadData extends AsyncTask<String,Void,String>{
        String xmlData="";

        @Override
        protected String doInBackground(String... params) {
            download_content = downloadXML(params[0]);
            if(download_content==null){
                Log.d("ParseApplication" , "No content found");
            }
            return download_content;
        }

        @Override
        protected void onPostExecute(String s) {
           Log.d("ParseApplication" , "The xml content is\n" + s);
        }

        protected String downloadXML(String urlpath){
            StringBuilder tempBuffer = new StringBuilder();
            try
            {
                URL url = new URL(urlpath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int code = conn.getResponseCode();
                Log.d("ParseApplication" , "The error response code is\n" + code);

                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                char temp[] = new char[500];
                int count=0;
                while(true){
                    count = isr.read(temp);
                    if(count<=0) {
                        break;
                    }

                    //Log.d("ParseApplication","Before temp buffer append block");
                    tempBuffer.append(String.copyValueOf(temp,0,count));

                }

            }
            catch(Exception e){
                e.printStackTrace();
                Log.d("ParseApplication","In catch block");
            }
            return tempBuffer.toString();
        }
    }
}
