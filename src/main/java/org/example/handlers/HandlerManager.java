package org.example.handlers;

import org.example.nlp.NLPProcessor;
import java.util.HashMap;
import java.util.Map;

public class HandlerManager {
    private final Map<String, ResponseHandler> handlers = new HashMap<>();
    private NLPProcessor nlpProcessor;

    public HandlerManager(NLPProcessor nlpProcessor) {
        this.nlpProcessor = nlpProcessor;

        // Handler'ları kaydedin
        handlers.put("greetings", new GreetingsHandler());
        handlers.put("creation", new CreationHandler());
        handlers.put("origin", new OriginHandler());
        handlers.put("location", new LocationHandler());
        handlers.put("farewells",new FarewellsHandler());
        handlers.put("weather", new WeatherHandler(nlpProcessor));
        handlers.put("sports", new SportsHandler(nlpProcessor));
        handlers.put("date",new DateHandler());
        handlers.put("time",new TimeHandler());
        handlers.put("twitter", new TwitterHandler());
        // Daha fazla handler ekleyin
    }

    public String handleRequest(String userInput) {
        String category = nlpProcessor.determineCategory(userInput);
        ResponseHandler handler = handlers.getOrDefault(category, input -> "Sorry, I didn't understand that.");
        return handler.handleResponse(userInput);
    }
}
