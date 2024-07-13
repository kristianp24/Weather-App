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
        var temp:Float = 0.0f
        val thread = Thread{
            temp = tempGetter(text.text.toString())

        }
        thread.start();
        writeTemperature(temp)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.Madrid->{
                text.clearComposingText();
                text.text="Madrid"
                text.textAlignment= View.TEXT_ALIGNMENT_CENTER
                var temp:Float = tempGetter("Madrid");
                writeTemperature(temp)
                return true
            }
            R.id.Barcelona->{
                text.clearComposingText()
                text.text = "Barcelona"
                text.textAlignment = View.TEXT_ALIGNMENT_CENTER
                return true
            }
            else->super.onOptionsItemSelected(item)
        }

    }

    fun writeTemperature(temp:Float){
        tempText=findViewById(R.id.tempText)
        tempText.clearComposingText()
        tempText.text = temp.toString()+"°C"
        tempText.textAlignment=View.TEXT_ALIGNMENT_CENTER
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