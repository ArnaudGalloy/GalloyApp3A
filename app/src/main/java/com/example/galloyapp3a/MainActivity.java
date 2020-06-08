package com.example.galloyapp3a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://www.metaweather.com";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("GalloyApp3A", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        List<Weather> weatherList = getDataFromCache();
        if(weatherList != null ) {
            showList(weatherList);
        }
        else {
    makeApiCall();
    }
    }

    private List<Weather> getDataFromCache() {
        String jsonWeather = sharedPreferences.getString(Constants.KEY_WEATHER_LIST, null);

        if(jsonWeather == null){
            return null;
        } else {
            Type listType = new TypeToken<List<Weather>>() {
            }.getType();
            return gson.fromJson(jsonWeather, listType);
        }
    }

    private void showList(List<Weather>weatherList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(weatherList);
        recyclerView.setAdapter(mAdapter);
    }



    private void makeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<RestWeatherResponse> call = weatherApi.getWeatherResponse();
        call.enqueue(new Callback<RestWeatherResponse>() {
            @Override
            public void onResponse(Call<RestWeatherResponse> call, Response<RestWeatherResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Weather> weatherList = response.body().getConsolidated_weather();
                    Toast.makeText(getApplicationContext(), "API Succes", Toast.LENGTH_SHORT).show();
                    saveList(weatherList);
                    showList(weatherList);

                }
                else {
                    showError();
                }
            }



            @Override
            public void onFailure(Call<RestWeatherResponse> call, Throwable t) {
                showError();

            }


        });

    }

    private void saveList(List<Weather> weatherList) {
        String jsonString = gson.toJson(weatherList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_WEATHER_LIST, jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List sauvegardée", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
