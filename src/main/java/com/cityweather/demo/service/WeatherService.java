package com.cityweather.demo.service;

import com.cityweather.demo.vo.WeatherInfo;

/**
 * Weather Service
 * Created by xuanzhang on 4/8/18.
 */

public interface WeatherService {

    /**
     * Get current weather information by city name
     *
     * @param cityName
     * @return WeatherInfo
     */
    WeatherInfo getCurrentWeatherByCityName(String cityName);
}