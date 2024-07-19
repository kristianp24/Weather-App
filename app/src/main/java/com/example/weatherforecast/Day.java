package com.example.weatherforecast;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Day {
    private String dateofDay;
    private float maxTemp;
    private float minTemp;
    private String condition;
    private String urlImage;

    public Day(float maxTemp, float minTemp, String condition,String dayofWeek) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.dateofDay = dayofWeek;
    }

    @Override
    public String toString() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return  LocalDate.of(Integer.parseInt(dateofDay.split("-")[0]),
                                         Integer.parseInt(dateofDay.split("-")[1]),
                                     Integer.parseInt(dateofDay.split("-")[2]) ).getDayOfWeek().toString()+ "\n" +
                    "Maximum:" + maxTemp+"°C" +
                    "    Minimum:" + minTemp+"°C" + "\n"+
                    "Condition :" + condition ;
        }
        else
            return "";
    }


}
