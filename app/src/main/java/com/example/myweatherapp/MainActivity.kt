package com.example.myweatherapp

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
 private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        with(binding){
            setContentView(root)
            weatherTask().execute()
        }
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=Houilles&units=metric&appid=50825c937099634773e1e10925f05f1e").readText(Charsets.UTF_8);
            return response;
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name")+", "+sys.getString("country")
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                binding.address.text = address;
                binding.temp.text = temp;
                binding.updatedAt.text = updatedAtText;
                binding.weatherDescription.text = weatherDescription.capitalize();
                binding.tempMin.text = tempMin;
                binding.tempMax.text = tempMax;

            } catch (e: Exception) {
            }
        }
    }
}