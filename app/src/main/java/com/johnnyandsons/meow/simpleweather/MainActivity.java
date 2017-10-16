package com.johnnyandsons.meow.simpleweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    public static String city;
    public static  TextView details;
    public static TextView forecast;
    public static  ImageView weatherIcon;
    int positionID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        details = (TextView) findViewById(R.id.textView2);
        weatherIcon = (ImageView) findViewById(R.id.imageView);
        forecast =(TextView) findViewById(R.id.textView3);

        final SharedPreferences sp = getSharedPreferences("Wellington", Context.MODE_PRIVATE);

        int  myIntValue = sp.getInt("tag", 0);

        String[] list = getResources().getStringArray(R.array.city_array);
        Arrays.sort(list);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(myIntValue);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        city = spinner.getSelectedItem().toString();
                        positionID =  spinner.getSelectedItemPosition();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt ("tag", positionID).apply();
                        new UpdateWeather().execute();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }
}

