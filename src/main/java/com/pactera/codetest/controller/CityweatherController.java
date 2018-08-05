package com.pactera.codetest.controller;

import com.pactera.codetest.service.WeatherService;
import com.pactera.codetest.vo.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller handles requests of current weather information
 *
 * Created by xuanzhang on 4/8/18.
 */

@RestController
@RequestMapping("/currentWeather")
public class CityweatherController {
    @Autowired
    private WeatherService weatherService;

    /**
     * This method is used to process GET request with city name
     *
     * @param cityName
     * @return WeatherInfo current weather information
     */

    @GetMapping("/cityName/{cityName}")
    public WeatherInfo getCurrentWeatherByCityName(@PathVariable("cityName") String cityName) {
        return weatherService.getCurrentWeatherByCityName(cityName);
    }

}
