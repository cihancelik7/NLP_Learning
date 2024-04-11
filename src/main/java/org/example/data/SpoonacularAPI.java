package org.example.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SpoonacularAPI {
    private static final String API_KEY = "d51d8dcbbcca467194b59ef2334b3725";
    private static final String BASE_URL = "https://api.spoonacular.com";

    public static String searchReceipes(String query){
        try {
            URL url = new URL(BASE_URL +"/receipes/complexSearch?query="+query+"&apiKey="+API_KEY );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();

            String line;
            while((line = in.readLine()) != null){
                response.append(line);
            }
            in.close();
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
