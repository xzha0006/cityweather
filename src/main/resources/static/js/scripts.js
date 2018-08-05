/*
 *  Scripts for page index.html
 *  Created on August 5, 2018
 *  Author - Xuan Zhang - zhangxuan.james@gmail.com
 */
function getWeather(city) {
    // var url = "http://35.165.93.211:8080/currentWeather/cityName/" + city
    var url = "http://52.36.124.65:9000/currentWeather/cityName/" + city
    $.get(url, function(weather, status){
        if (status === "success") {
            if (weather.status === "success") {
                document.getElementById("weather-block").style.display = "block";
                document.getElementById("city-name").innerHTML = weather.cityName;
                document.getElementById("temperature").innerHTML = weather.temperature;
                document.getElementById("weather").innerHTML = weather.weather;
                document.getElementById("wind-speed").innerHTML = weather.wind;
                document.getElementById("updated-time").innerHTML = weather.updateTime;
                // change icon and color style based on weather
                if (weather.weather === "Clear") {
                    document.body.style.background = "#edef81";
                    document.getElementById("weather-icon").style.color = "#edef81";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-day-sunny"></i>'
                } else if (weather.weather === "Clouds") {
                    document.body.style.background = "#924da3";
                    document.getElementById("weather-icon").style.color = "#924da3";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-cloudy"></i>'
                } else if (weather.weather === "Rain") {
                    document.body.style.background = "#616468";
                    document.getElementById("weather-icon").style.color = "#616468";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-rain"></i>'
                } else if (weather.weather === "Mist") {
                    document.body.style.background = "#d7f0f2";
                    document.getElementById("weather-icon").style.color = "#d7f0f2";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-fog"></i>'
                } else if (weather.weather === "Thunderstorm") {
                    document.body.style.background = "#203a4f";
                    document.getElementById("weather-icon").style.color = "#203a4f";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-thunderstorm"></i>'
                } else {
                    document.body.style.background = "#d9dce0";
                    document.getElementById("weather-icon").style.color = "#d9dce0";
                    document.getElementById("weather-icon").innerHTML = '<i class="wi wi-cloud"></i>'
                }
            }
        }
    });
}