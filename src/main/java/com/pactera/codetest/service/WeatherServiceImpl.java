package com.pactera.codetest.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pactera.codetest.exception.JsonParseException;
import com.pactera.codetest.vo.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;


/**
 * implement of WeatherService
 * use Redis to cache the weather record
 * Created by xuanzhang on 4/8/18.
 *
 */

@Service
public class WeatherServiceImpl implements WeatherService {
    private final String OPEN_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?units=metric";
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final Long TIME_OUT = 2400L; //set timeout to 40 minutes

    @Value("${OpenWeatherApiKey}")  // call api key from application.properties
    private String ApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherInfo getCurrentWeatherByCityName(String cityName) {
        WeatherInfo weatherInfo;
        String url = this.OPEN_WEATHER_API + "&q=" + cityName + "&appid=" + this.ApiKey;

        //check the connection of Redis. if Redis was done, use the method of no-Redis

        if(!stringRedisTemplate.getConnectionFactory().getConnection().isClosed()) {
            weatherInfo = this.getWeatherInfoWithRedis(url);
        } else {
            weatherInfo = this.getWeatherInfoWithoutRedis(url);
        }
        return weatherInfo;
    }

    /**
     * Get weather information from OpenWeatherMap api with Redis
     * @param url
     * @return WeatherInfo
     */
    private WeatherInfo getWeatherInfoWithRedis(String url) {
        WeatherInfo weather;
        String strBody;
        JsonParser jsonParser = new JsonParser();
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        String recordKey = url;

        try {
            //if the record is not cached, send a request
            if (!this.stringRedisTemplate.hasKey(recordKey)) {
                LOGGER.info("===== Found no cached record  =====");
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); //send http request to get data
                //if the status code is 200
                if (response.getStatusCodeValue() == 200) {
                    strBody = response.getBody();
                    ops.set(recordKey, strBody, this.TIME_OUT, TimeUnit.SECONDS);
                    JsonObject weatherJson = jsonParser.parse(strBody).getAsJsonObject();
                    weather = WeatherInfo.parseOpenWeatherData(weatherJson);
                } else {
                    LOGGER.error("===== http request failure, status code is " + response.getStatusCodeValue() + " =====");
                    weather = new WeatherInfo();
                    weather.setStatus("failure");
                }
            }
            //if the record is cached, use the cached data
            else {
                LOGGER.info("===== Found cached record  =====");
                strBody = ops.get(recordKey);
                JsonObject weatherJson = jsonParser.parse(strBody).getAsJsonObject();
                weather = WeatherInfo.parseOpenWeatherData(weatherJson);
            }
        } catch (HttpClientErrorException e) {
            //catch the http exception
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== weather api http request failure =====");
        } catch (JsonParseException e) {
            // catch the JsonParseException
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== Json parse failure =====");
        }

        return weather;
    }

    /**
     * Get weather information from OpenWeatherMap api without Redis
     * @param url
     * @return WeatherInfo
     */
    private WeatherInfo getWeatherInfoWithoutRedis(String url) {
        WeatherInfo weather;
        String strBody;
        JsonParser jsonParser = new JsonParser();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); //send http request to get data
            //if the status code is 200
            if (response.getStatusCodeValue() == 200) {
                strBody = response.getBody();
                JsonObject weatherJson = jsonParser.parse(strBody).getAsJsonObject();
                weather = WeatherInfo.parseOpenWeatherData(weatherJson);
            } else {
                LOGGER.error("===== http request failure, status code is " + response.getStatusCodeValue() + " =====");
                weather = new WeatherInfo();
                weather.setStatus("failure");
            }
        } catch (HttpClientErrorException e) {
            //catch the http exception
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== weather api http request failure =====");
        } catch (JsonParseException e) {
            // catch the JsonParseException
            weather = new WeatherInfo();
            weather.setStatus("failure");
            e.printStackTrace();
            LOGGER.error("===== Json parse failure =====");
        }

        return weather;
    }
}
