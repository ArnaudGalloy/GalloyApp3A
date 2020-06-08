package com.example.galloyapp3a;

import java.util.List;

public class RestWeatherResponse {
    private String Title;
    private String City;
    private Integer Woeid;
    private List<Weather> consolidated_weather;

    public List<Weather> getConsolidated_weather() {
        return consolidated_weather;
    }

    public String getTitle() {
        return Title;
    }

    public String getCity() {
        return City;
    }

    public Integer getWoeid() {
        return Woeid;
    }
}
