package com.example.galloyapp3a;

public class Weather {
    private String weather_state_name;
    private String applicable_date;
    private Float min_temp;
    private Float max_temp;
    private Float wind_speed;
    private Float humidity;

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public String getApplicable_date() {
        return applicable_date;
    }

    public Float getMin_temp() {
        return min_temp;
    }

    public Float getMax_temp() {
        return max_temp;
    }

    public Float getWind_speed() {
        return wind_speed;
    }

    public Float getHumidity() {
        return humidity;
    }
}
