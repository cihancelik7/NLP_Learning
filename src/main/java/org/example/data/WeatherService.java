package org.example.data;

import netscape.javascript.JSObject;
import org.json.JSONObject;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    /*
    WeatherService sınıfı,
    OpenWeatherMap API'sini kullanarak belirli bir şehir için hava durumu
    bilgilerini çeken bir servistir. Bu sınıf, API'den aldığı JSON cevabını
    işleyerek, sıcaklık ve hava durumu açıklaması gibi bilgileri içeren bir metin döndürür.
     */

    private static final String API_KEY = "d596524d8c0fcac72f4326ddf4c8d0d2";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";



    public static String getWeather(String city) throws Exception {
        String requestURL = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric&lang=en";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURL)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // JSON cevabını işleme
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONObject main = jsonResponse.getJSONObject("main");
        double temp = main.getDouble("temp");
        String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

        return "The weather in " + city + " is " + temp + "°C with " + weatherDescription + ".";
    }

    // Test için main metodu
   /* public static void main(String[] args) {
        try {
            String weatherInfo = getWeather("Istanbul");
            System.out.println(weatherInfo); // İstanbul için hava durumu bilgisini yazdırır
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
