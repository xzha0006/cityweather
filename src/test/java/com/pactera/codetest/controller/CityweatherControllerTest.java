package com.pactera.codetest.controller;

import com.pactera.codetest.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * This test class is used for CityweatherController
 * Created by xuanzhang on 5/8/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc


public class CityweatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * if controller has complicated business logic, we could mock the service to test the controller class.
     * But in this case the controller just returns the service results. So there is no need to do that.
     * Example of mocking service:
     * @MockBean
     * private WeatherService weatherService;
     * when(weatherService.getCurrentWeatherByCityName("Melbourne")).thenReturn(new WeatherInfo());
     */

    @Test
    public void testCityweatherController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currentWeather/cityName/Melbourne")).andExpect(status().isOk())
                .andExpect(content().string(startsWith("{\"cityName\":\"Melbourne\",\"updateTime\"")));
        mockMvc.perform(MockMvcRequestBuilders.get("/currentWeather/cityName/Centaurus")).andExpect(status().isOk())
                .andExpect(content().string(startsWith("{\"cityName\":null,\"updateTime\":null,\"weather\":null," +
                        "\"temperature\":null,\"wind\":null,\"status\":\"failure\"}")));
    }
}
