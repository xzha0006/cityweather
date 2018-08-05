package com.pactera.codetest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pactera.codetest.vo.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * implement of WeatherService
 * Created by xuanzhang on 4/8/18.
 */

@Service
public class WeatherServiceImpl implements WeatherService {
    private final String WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?units=metric";

    @Value("${OpenWeatherApiKey}")  // call api key from application.properties
    private String ApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherInfo getCurrentWeatherByCityName(String cityName) {
        String url = this.WEATHER_API + "&q=" + cityName + "&appid=" + this.ApiKey;

        return this.getWeatherInfo(url);
    }

    private WeatherInfo getWeatherInfo(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String strBody = null;
        WeatherInfo weather = null;

        if (response.getStatusCodeValue() == 200) {
            strBody = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject weatherJson = jsonParser.parse(strBody).getAsJsonObject();
            weather = WeatherInfo.parseOpenWeatherData(weatherJson);
        }
        else if (response.getStatusCodeValue() == 404) {
            System.out.println(url); // replaced by logging later
        }
        return weather;
    }
}
