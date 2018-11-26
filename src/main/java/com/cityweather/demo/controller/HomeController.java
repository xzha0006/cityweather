package com.cityweather.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * controller for index page
 *
 * Created by xuanzhang on 5/8/18.
 */
@Controller
public class HomeController {
    @Value("${cityList}")  // call city list from application.properties
    private String cityNames;

    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        String [] cityList = cityNames.split(",");
        model.put("cityList", cityList);
        return "index";
    }
}
