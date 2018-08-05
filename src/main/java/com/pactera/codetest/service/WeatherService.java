package com.pactera.codetest.service;

import com.pactera.codetest.vo.WeatherInfo;

/**
 * Weather
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