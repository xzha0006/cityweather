package com.cityweather.demo.vo;

import com.cityweather.demo.exception.JsonParseException;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    private String status;  //success or failure

    /**
     * This method is used to parse json from OpenWeatherAPI to WeatherInfo object
     * customized for OpenWeatherAPI
     *
     * @param weatherData
     * @return
     */

    public static WeatherInfo parseOpenWeatherData(JsonObject weatherData) throws JsonParseException {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {
            String cityName = weatherData.get("name").getAsString();
            String weather = weatherData.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("main").getAsString();
            Float temp = weatherData.getAsJsonObject("main").get("temp").getAsFloat();

            //parse update time
            Long updateTimeStamp = weatherData.get("dt").getAsLong();
            Date updateTime = new Date(updateTimeStamp * 1000); // convert time stamp to milliseconds
            String pattern = "EEEEE hh:mm aa";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
            String updateTimeFormatted = sdf.format(updateTime);

            //parse wind speed
            Float wind = weatherData.getAsJsonObject("wind").get("speed").getAsFloat() * 3.6f; // convert m/s to km/h
            DecimalFormat decimalFormat=new DecimalFormat(".00");
            Float windSpeed = Float.parseFloat(decimalFormat.format(wind));

            weatherInfo.setCityName(cityName);
            weatherInfo.setUpdateTime(updateTimeFormatted);
            weatherInfo.setTemperature(temp);
            weatherInfo.setWind(windSpeed);
            weatherInfo.setWeather(weather);
            weatherInfo.setStatus("success");
        } catch (Exception e) {
            throw new JsonParseException("Error occurs while parsing json data into WeatherInfo");
        }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
