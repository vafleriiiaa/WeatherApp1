package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_API_KEY = "83cadb0fbe5a551292e5b616c1889c1d";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/";

    private TextView temperatureInfo;
    private TextView weatherCondition;
    private TextView windInfo;
    private TextView perceivedTemperature;

    private void navigateToMap() {
        // Код открытия карты
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        mainToolbar.setBackgroundColor(getResources().getColor(R.color.blue));

        Button mapButton = findViewById(R.id.btn_open_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMap();
            }
        });

        temperatureInfo = findViewById(R.id.temperatureText);
        weatherCondition = findViewById(R.id.descriptionText);
        windInfo = findViewById(R.id.windText);
        perceivedTemperature = findViewById(R.id.feelsLikeText);

        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl(WEATHER_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherService = retrofitInstance.create(WeatherApi.class);

        Call<WeatherResponse> weatherDataCall = weatherService.getCurrentWeather(37.7749, -122.4194, "metric", WEATHER_API_KEY);
        weatherDataCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse currentWeather = response.body();
                    temperatureInfo.setText("Температура: " + currentWeather.getMain().getTemp() + "°C");
                    weatherCondition.setText("Погода: " + currentWeather.getWeather()[0].getDescription());
                    windInfo.setText("Ветер: " + currentWeather.getWind().getSpeed() + " м/с");
                    perceivedTemperature.setText("Ощущается как: " + currentWeather.getMain().getFeelsLike() + "°C");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(openSettings);
        return super.onOptionsItemSelected(item);
    }
}
