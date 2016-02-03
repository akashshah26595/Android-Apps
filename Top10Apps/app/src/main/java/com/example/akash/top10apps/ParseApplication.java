package com.example.akash.top10apps;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by akash on 10/2/15.
 */
public class ParseApplication {
    private ArrayList<Application> applications;
    private String xmlData;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
   }

  //  public void setApplications(ArrayList<Application> applications) {
   //     this.applications = applications;
   // }

    public boolean process() {
        boolean isEntry = false;
        Application currentRecord = null;
        boolean status = true;
        String textValue = "";

        try {
            //Log.d("ParseApplication", "In try block");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData)); //give xml input to XMLPullParser
            int eventType = xpp.getEventType();
            //Log.d("ParseApplication", "Before while block");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
               // Log.d("ParseApplication", "Before switch block");
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        //Log.d("ParseApplication", "The current tag is :" + tagName);

                        if (tagName.equalsIgnoreCase("entry")) {
                            isEntry = true;
                            currentRecord = new Application();
                        }
                        break;

                    case XmlPullParser.TEXT:
                       // Log.d("ParseApplication", "In Text block");
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        //Log.d("ParseApplication", "The ending tag is :" + tagName);
                        if (isEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                applications.add(currentRecord);
                                isEntry = false;
                            //    Log.d("ParseApplication", "In entry block");
                            } else if (tagName.equalsIgnoreCase("name")) {
                                currentRecord.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setCategory(textValue);
                            } else if (tagName.equalsIgnoreCase("content")) {
                                currentRecord.setContent(textValue);
                            } else if (tagName.equalsIgnoreCase("releasedate")) {
                                currentRecord.setReleasedate(textValue);
                            } else if (tagName.equalsIgnoreCase("summary")) {
                                currentRecord.setSummary(textValue);
                            }
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        for (Application app : applications) {
            Log.d("ParseApplication", "***********");
            Log.d("ParseApplication", "Name:" + app.getName());
            Log.d("ParseApplication", "Release Date:" + app.getReleasedate());
            Log.d("ParseApplication", "Category: " + app.getCategory());
            Log.d("ParseApplication", "Summary: " + app.getSummary());
            Log.d("ParseApplication", "Content : " + app.getContent());
        }
        return true;
    }
}
