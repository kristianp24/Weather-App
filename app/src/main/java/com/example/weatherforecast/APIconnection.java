package com.example.weatherforecast;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APIconnection {
    private String url;
    private float temp;
    private float maxTemp;
    private float minTemp;
    private String imageURL;
    private String condtion;
    private List<Day> days;

    public String getImageURL() {
        return imageURL;
    }

    public String getCondtion() {
        return condtion;
    }

    public List<Day> getDays() {
        return days;
    }

    public APIconnection(String url){
        this.url = url;
        this.temp = 0.0f;
        days = new ArrayList<>();
    }

    public void getWeatherForecastforWeek(String infoJSONmode){
        try {

            JSONTokener tokener = new JSONTokener(infoJSONmode);
            JSONArray array = new JSONArray(tokener);
            for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                JSONObject forecast = obj.getJSONObject("forecast");
                JSONArray array2 = forecast.getJSONArray("forecastday");
                for(int j=0;j< array2.length();j++){
                    JSONObject obj2 = array2.getJSONObject(j);
                    String date = obj2.getString("date");
                    JSONObject day = obj2.getJSONObject("day");
                    float max = (float) day.getDouble("maxtemp_c");
                    float min = (float)day.getDouble("mintemp_c");
                    JSONObject condition = day.getJSONObject("condition");
                    String conditionDay = condition.getString("text");
                    days.add(new Day(max,min,conditionDay,date));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
                getWeatherForecastforWeek(infoJSONmode);
                conn.disconnect();
            }

        }
        catch (Exception e){
             String message = e.getMessage();
        }
        return this.temp;
    }

}
