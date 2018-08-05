package com.pactera.codetest.vo;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * WeatherInfo is used for the displayed weather information
 *
 * Created by xuanzhang on 4/8/18.
 */

public class WeatherInfo implements Serializable {

    private String cityName;
    private String updateTime;
    private String weather;
    private Float temperature;
    private Float wind;

    /**
     * This method is used to parse json from OpenWeatherAPI to WeatherInfo object
     * customized for OpenWeatherAPI
     *
     * @param weatherData
     * @return
     */

    public static WeatherInfo parseOpenWeatherData(JsonObject weatherData) {
        WeatherInfo weatherInfo = new WeatherInfo();

        String cityName = weatherData.get("name").getAsString();
        String weather = weatherData.getAsJsonArray("weather")
                .get(0).getAsJsonObject().get("main").getAsString();
        Float temp = weatherData.getAsJsonObject("main").get("temp").getAsFloat();
        Long updateTimeStamp = weatherData.get("dt").getAsLong();
        Date updateTime = new Date(updateTimeStamp * 1000); // convert time stamp to milliseconds
        String pattern = "EEEEE hh:mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String updateTimeFormatted = simpleDateFormat.format(updateTime);

        Float wind = weatherData.getAsJsonObject("wind").get("speed").getAsFloat() * 3.6f; // convert m/s to km/h

        weatherInfo.setCityName(cityName);
        weatherInfo.setUpdateTime(updateTimeFormatted);
        weatherInfo.setTemperature(temp);
        weatherInfo.setWind(wind);
        weatherInfo.setWeather(weather);

        return weatherInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getWind() {
        return wind;
    }

    public void setWind(Float wind) {
        this.wind = wind;
    }
}
