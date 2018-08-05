package com.pactera.codetest.service;

import com.pactera.codetest.controller.CityweatherController;
import com.pactera.codetest.vo.WeatherInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
