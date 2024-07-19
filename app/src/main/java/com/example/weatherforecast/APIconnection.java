package com.example.weatherforecast;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIconnection {
    private String url;
    private float temp;
    private float maxTemp;
    private float minTemp;
    private String imageURL;
    private String condtion;

    public String getImageURL() {
        return imageURL;
    }

    public String getCondtion() {
        return condtion;
    }

    public APIconnection(String url){
        this.url = url;
        this.temp = 0.0f;
    }

    public float getTemperature(){
        try{
            //Setam conexiunea cu API
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();
            if(code != 200){
                  this.temp = 55.5f;
            }
            else{
                //Luam raspunsul si prelucram datele
                StringBuilder builder = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while(scanner.hasNext())
                    builder.append(scanner.nextLine());
                String infoJSONmode = "["+builder.toString()+"]";

                JSONTokener tokener = new JSONTokener(infoJSONmode);
                JSONArray array = new JSONArray(tokener);
                for(int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    JSONObject current = object.getJSONObject("current");
                    JSONObject condition = current.getJSONObject("condition");
                    this.temp = (float) current.getDouble("temp_c");
                    this.condtion = condition.getString("text");
                    this.imageURL = condition.getString("icon");
                }
            }
        }
        catch (Exception e){
             String message = e.getMessage();
        }
        return this.temp;
    }

}
