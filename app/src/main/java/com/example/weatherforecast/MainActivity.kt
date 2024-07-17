package com.example.weatherforecast

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.zip.Inflater
import kotlinx.coroutines.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
   lateinit var text:TextView
   lateinit var toolbar:androidx.appcompat.widget.Toolbar
   lateinit var tempText:TextView;
    lateinit var thread:Thread
    lateinit var back:androidx.constraintlayout.widget.ConstraintLayout
    lateinit var sunImage:ImageView
    lateinit var searchBar:EditText
    lateinit var weatherCondition:TextView
    lateinit var ziua:TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        text = findViewById(R.id.infos)
        toolbar=findViewById(R.id.toolbar)
        tempText=findViewById(R.id.tempText)
        weatherCondition=findViewById(R.id.condition)
        ziua = findViewById(R.id.ziua)
        var currentDay:DayOfWeek = LocalDate.now().dayOfWeek
        ziua.text = currentDay.toString()
        ziua.textAlignment = View.TEXT_ALIGNMENT_CENTER
        ziua.textSize = 43.5f

        setSupportActionBar(toolbar)
        this.setBackground()
        //Orasul default
        var temp:Float = threadFun("Bucharest")

        writeTemperature(temp)

    }
    fun threadFun(town:String):Float{
        var temp:Float = 0.1f
        thread = Thread{
            temp = tempGetter(town)
        }
        thread.start()
        thread.join()

        return temp
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setBackground(){
        sunImage = findViewById(R.id.sun)
        back = findViewById(R.id.main)
        if(LocalTime.now() > LocalTime.of(20,0)){
            back.setBackgroundResource(R.drawable.night)
            sunImage.setImageResource(R.drawable.moon)
            tempText.setTextColor(Color.WHITE)
            text.setTextColor(Color.WHITE)
            ziua.setTextColor(Color.WHITE)
            toolbar.setTitleTextColor(Color.WHITE)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.Madrid->{
                findCity(item.title.toString())
                return true
            }
            R.id.Barcelona->{
                findCity(item.title.toString())
                return true
            }
            R.id.Berlin->{
                findCity(item.title.toString())
                return true
            }
            R.id.Paris->{
                findCity(item.title.toString())
                return true
            }
            R.id.Rome->{
                findCity(item.title.toString())
                return true
            }
            R.id.Other->{
               searchBar = findViewById(R.id.search)
               searchBar.visibility=View.VISIBLE
                searchBar.setOnEditorActionListener(TextView.OnEditorActionListener{ v,id,listener ->
                         if(id == EditorInfo.IME_ACTION_DONE)
                         {
                             val input:String = searchBar.text.toString()
                             val firstLetter:Char = input[0]
                             firstLetter.uppercaseChar()
                             var city:String =""
                             city+=firstLetter
                             for(i in 1..input.length-1){
                                 city+=input[i]
                             }
                             findCity(city)
                             return@OnEditorActionListener true
                         }
                    false
                })

                return true
            }
            else->super.onOptionsItemSelected(item)
        }

    }

    fun findCity(city:String){
        text.clearComposingText()
        text.text = city
        text.textAlignment = View.TEXT_ALIGNMENT_CENTER
        var temp:Float = threadFun(city)
        writeTemperature(temp)
    }

    fun writeTemperature(temp:Float){
        tempText=findViewById(R.id.tempText)
        tempText.clearComposingText()
        tempText.text = temp.toString()+"°C"
        tempText.textAlignment=View.TEXT_ALIGNMENT_CENTER
        tempText.textSize = 50.5f
    }
    fun tempGetter(town:String):Float{
        var url:String = apiCreator(town);
        var connection:APIconnection = APIconnection(url);
        var temp:Float = connection.temperature;
        weatherCondition.text = connection.condtion
        weatherCondition.textAlignment = View.TEXT_ALIGNMENT_CENTER
        weatherCondition.textSize = 40.5f
//        var uri:Uri = connection.imageURL.toUri()
//        sunImage.setImageURI(uri)

        return temp;
    }
    fun apiCreator(town:String): String{
        var url:String = "http://api.weatherapi.com/v1/current.json?key=5f7ab45fa09d4d82b8f173259241207&q="+town+"&aqi=no"
        return url
    }


}