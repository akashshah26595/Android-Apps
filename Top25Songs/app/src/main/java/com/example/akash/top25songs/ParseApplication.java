package com.example.akash.top25songs;

import android.app.Application;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by akash on 10/10/15.
 */
public class ParseApplication {
    private ArrayList<Applications> arrayList = new ArrayList<>();
    private String  xmlData;

    public ParseApplication(String xmlData) {
      this.xmlData = xmlData;
      arrayList = new ArrayList<Applications>();
    }

    public ArrayList<Applications> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Applications> arrayList) {
        this.arrayList = arrayList;
    }

    public boolean process(){
        boolean inEntry=false;
        String textValue="";
        Applications currentApp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true); //Specifies that the parser produced by this factory will provide support for XML namespaces.
           //the above property ensures that if the tagname in the xml file has namespace, it will accept it , instead of neglecting it.
            // if the above property isn't set , then attributes like "im:price", "im:name" will be neglected and null will be displayed.
            // only attributes without namespace like "id" will be processed.
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));

            int eventType = xpp.getEventType();

            while(eventType!=XmlPullParser.END_DOCUMENT){
             String tagName = xpp.getName();
                Log.d("ParseApplication", "Tag Name: " + tagName);
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("entry")) {
                            Log.d("ParseApplication", "In entry");
                            currentApp = new Applications();
                            inEntry=true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        Log.d("ParseApplication", "In text block");
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("ParseApplication","Current tag: " + tagName);

                        if (inEntry) {
                            if (tagName.equalsIgnoreCase("entry"))
                            {
                                arrayList.add(currentApp);
                                inEntry = false;
                            }
                            else if (tagName.equalsIgnoreCase("title")) {
                                currentApp.setTitle(textValue);
                            }
                            else if (tagName.equalsIgnoreCase("artist")) {
                                currentApp.setArtist(textValue);

                            } else if (tagName.equalsIgnoreCase("price")) {
                                currentApp.setPrice(textValue);
                            }
                            else if (tagName.equalsIgnoreCase("name")) {
                                currentApp.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("releasedate")) {
                                currentApp.setReleasedate(textValue);
                            }
                            else if (tagName.equalsIgnoreCase("id")) {
                                currentApp.setId(textValue);
                            }
                        }
                        break;
                }
                eventType  = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (Applications app : arrayList) {
            Log.d("ParseApplication", "***********");
            Log.d("ParseApplication", "Name:" + app.getName());
            Log.d("ParseApplication", "ID:" + app.getId());
            Log.d("ParseApplication", "Release Date:" + app.getReleasedate());
            Log.d("ParseApplication", "Price: " + app.getPrice());
            Log.d("ParseApplication", "Artist : " + app.getArtist());
            Log.d("ParseApplication", "Title : " + app.getTitle());
        }

        return true;
    }
}
