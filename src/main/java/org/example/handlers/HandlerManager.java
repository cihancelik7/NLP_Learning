package org.example.handlers;

import org.example.nlp.NLPProcessor;
import java.util.HashMap;
import java.util.Map;

public class HandlerManager {
    /*
    HandlerManager sınıfı, kullanıcı girişlerini işlemek ve
    uygun yanıtları döndürmek üzere tasarlanmış bir yönetici sınıfıdır.
    Bu sınıf, NLPProcessor nesnesini kullanarak gelen kullanıcı girişinin
    kategorisini belirler ve bu kategoriye uygun bir yanıt vermek için
    önceden tanımlanmış handler'lardan (işleyicilerden) birini seçer.
    Her bir handler, belirli bir kategoriye karşılık gelen kullanıcı sorularına
    veya komutlarına yanıt vermek üzere özelleştirilmiştir.
     */
    private final Map<String, ResponseHandler> handlers = new HashMap<>();
    private NLPProcessor nlpProcessor;

    // NLPProcessor nesnesini alır ve çeşitli handler'ları kaydeder.
    public HandlerManager(NLPProcessor nlpProcessor) {
        this.nlpProcessor = nlpProcessor;

        // NLPProcessor nesnesini alır ve çeşitli handler'ları kaydeder.
        handlers.put("greetings", new GreetingsHandler()); // Selamlama için handler
        handlers.put("creation", new CreationHandler()); // Oluşturma ile ilgili sorular için handler
        handlers.put("origin", new OriginHandler()); // Köken ile ilgili sorular için handler
        handlers.put("location", new LocationHandler()); // Konum ile ilgili sorular için handler
        handlers.put("farewells", new FarewellsHandler()); // Veda için handler
        handlers.put("weather", new WeatherHandler(nlpProcessor)); // Hava durumu için handler
        handlers.put("sports", new SportsHandler(nlpProcessor)); // Spor ile ilgili sorular için handler
        handlers.put("date", new DateHandler()); // Tarih ile ilgili sorular için handler
        handlers.put("time", new TimeHandler()); // Zaman ile ilgili sorular için handler
        handlers.put("twitter", new TwitterHandler()); // Twitter ile ilgili sorular için handler
        handlers.put("email", new EmailsHandler()); // Email ile ilgili sorular için handler
        handlers.put("recipe",new RecipeHandler());
        // Daha fazla handler ekleyin
    }
    // Kullanıcı girişine yanıt veren metod.
    public String handleRequest(String userInput) {
        // Kullanıcı girişinin kategorisini belirle.
        String category = nlpProcessor.determineCategory(userInput);
        // Belirlenen kategoriye uygun handler'ı al veya varsayılan mesajı döndür.
        ResponseHandler handler = handlers.getOrDefault(category, input -> "Sorry, I didn't understand that.");
        // Belirlenen kategoriye uygun handler'ı al veya varsayılan mesajı döndür.
        return handler.handleResponse(userInput);
    }
}
