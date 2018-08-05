package com.pactera.codetest.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pactera.codetest.exception.JsonParseException;
import com.pactera.codetest.vo.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


/**
 * implement of WeatherService
 * Created by xuanzhang on 4/8/18.
 */

@Service
public class WeatherServiceImpl implements WeatherService {
    private final String WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?units=metric";
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);


    @Value("${OpenWeatherApiKey}")  // call api key from application.properties
    private String ApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherInfo getCurrentWeatherByCityName(String cityName) {
        String url = this.WEATHER_API + "&q=" + cityName + "&appid=" + this.ApiKey;

        return this.getWeatherInfo(url);
    }

    /**
     * Get weather information from OpenWeatherMap api
     * @param url
     * @return WeatherInfo
     */
    private WeatherInfo getWeatherInfo(String url) {
        WeatherInfo weather;
        String strBody;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); //send http request to get data
            if (response.getStatusCodeValue() == 200) {
                strBody = response.getBody();
                JsonParser jsonParser = new JsonParser();
                JsonObject weatherJson = jsonParser.parse(strBody).getAsJsonObject();
                weather = WeatherInfo.parseOpenWeatherData(weatherJson);
            }
            else {
                weather = new WeatherInfo();
                weather.setStatus("failure");
                System.out.println("Status code is: " + response.getStatusCodeValue()); // replaced by logging later
                System.out.println(url); // replaced by logging later
            }
        } catch (HttpClientErrorException e) {
            //catch the http exception
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== weather api http request failure =====", e);
        } catch (JsonParseException e) {
            // catch the JsonParseException
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== Json parse failure =====", e);
        }
        return weather;
    }
}
