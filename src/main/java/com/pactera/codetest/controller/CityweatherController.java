package com.pactera.codetest.controller;

import com.pactera.codetest.service.WeatherService;
import com.pactera.codetest.vo.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xuanzhang on 4/8/18.
 */

@RestController
public class CityweatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/cityName/{cityName}")
    public WeatherInfo getReportByCityId(@PathVariable("cityName") String cityName) {
        return weatherService.getCurrentWeatherByCityName(cityName);
    }
}
