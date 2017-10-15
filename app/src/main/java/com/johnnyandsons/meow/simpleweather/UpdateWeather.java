package com.johnnyandsons.meow.simpleweather;

import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Meow on 10/10/2017.
 */

public class UpdateWeather  extends AsyncTask<String, Integer, String> {

    TextView weather = MainActivity.details;
    String city = MainActivity.city;
    ImageView weatherIcon = MainActivity.weatherIcon;
    TextView forecastWord = MainActivity.forecast;
    private static final String OPEN_WEATHER_MAP_API = "http://www.metservice.com/publicData/mainPageW";
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


    @Override
    protected String doInBackground(String... urls) {

        String finalJson = "";
        String output;
        try {
            URL url = new URL(OPEN_WEATHER_MAP_API);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer();

            while ((output = reader.readLine()) != null)
                json.append(output);

            connection.disconnect();

            finalJson = new String(json.toString());


        } catch (Exception e) {
        }
        return finalJson;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
            displayMessage(result);
    }

    private void displayMessage(String result) {
        JSONArray arr = null;
            try {
                arr = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String location = null;
            for(
                    int i = 0; i<arr.length();i++)

            {

                JSONObject jsonobject = null;
                try {
                    jsonobject = arr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    location = jsonobject.getString("location");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                weather = (TextView) weather.findViewById(R.id.textView2);

                if (location.equalsIgnoreCase(city)) {

                    JSONArray arry = null;
                    try {
                        arry = (JSONArray) jsonobject.get("days");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject obj = null;
                    try {
                        obj = (JSONObject) arry.get(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        String min = obj.getString("min");
                        String max = obj.getString("max");
                        String forecast = obj.getString("forecastWord");



                        weather.setText("High: " + max + "°"
                                + " \nLow: " + min + "°");

                        if (forecast.equalsIgnoreCase("Fine")) {
                            weatherIcon.setImageResource(R.drawable.sunny);

                        }

                        if (forecast.equalsIgnoreCase("Partly Cloudy")){
                            weatherIcon.setImageResource(R.drawable.partly_cloudy);

                        }
                        if (forecast.equalsIgnoreCase("Showers")){
                            weatherIcon.setImageResource(R.drawable.showers);

                        }

                        if (forecast.equalsIgnoreCase("Few showers")){
                            weatherIcon.setImageResource(R.drawable.some_showers);

                        }

                        if (forecast.contains("Rain")){
                            weatherIcon.setImageResource(R.drawable.rain);

                        }
                        if (forecast.contains("Windy")){
                            weatherIcon.setImageResource(R.drawable.windy);

                        }

                        if (forecast.contains("Cloudy")){
                            weatherIcon.setImageResource(R.drawable.cloudy);

                        }

                        forecastWord.setText(forecast);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }




