package com.cityweather.demo.service;

import com.cityweather.demo.vo.WeatherInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by xuanzhang on 5/8/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    public void testWeatherService() throws Exception {
        //valid city name
        WeatherInfo weatherInfo0 = weatherService.getCurrentWeatherByCityName("Melbourne");
        //invalid city name
        WeatherInfo weatherInfo1 = weatherService.getCurrentWeatherByCityName("Centaurus");
        //empty city name
        WeatherInfo weatherInfo2 = weatherService.getCurrentWeatherByCityName("");

        assertThat(weatherInfo0.getCityName()).isEqualTo("Melbourne");
        assertThat(weatherInfo1.getStatus()).isEqualTo("failure");
        assertThat(weatherInfo2.getStatus()).isEqualTo("failure");

    }
}
