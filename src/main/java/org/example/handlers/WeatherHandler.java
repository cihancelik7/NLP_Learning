package org.example.handlers;

import org.example.data.WeatherService;
import org.example.nlp.NLPProcessor;
import org.example.data.Cities;


public class WeatherHandler implements ResponseHandler {
    private NLPProcessor nlpProcessor;
    private WeatherService weatherService;

    public WeatherHandler(NLPProcessor nlpProcessor) {
        this.nlpProcessor = nlpProcessor;
        this.weatherService = new WeatherService();
    }

    @Override
    public String handleResponse(String input) {
        try {
            // İlk olarak cümleyi tokenlere ayırın
            String[] tokens = nlpProcessor.tokenize(input);
            // Tokenler arasında şehir arayın
            for (String token : tokens) {
                if (Cities.constainsKey(token)) {
                    // Eşleşen şehir ismiyle hava durumu bilgisini getirin
                    return weatherService.getWeather(token);
                }
            }
            return "Sorry, I couldn't determine the city from your message.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, there was a problem fetching the weather.";
        }
    }
}
