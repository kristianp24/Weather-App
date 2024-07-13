package com.example.weatherforecast

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.zip.Inflater
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
   lateinit var text:TextView
   lateinit var toolbar:androidx.appcompat.widget.Toolbar
   lateinit var tempText:TextView;
    lateinit var thread:Thread
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
        setSupportActionBar(toolbar)
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
        return temp;
    }
    fun apiCreator(town:String): String{
        var url:String = "http://api.weatherapi.com/v1/current.json?key=5f7ab45fa09d4d82b8f173259241207&q="+town+"&aqi=no"
        return url
    }


}