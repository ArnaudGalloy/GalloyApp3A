package com.example.galloyapp3a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    makeApiCall();
    }
    private void showList(List<Weather>weatherList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }



    private void makeApiCall(){




        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

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
    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
