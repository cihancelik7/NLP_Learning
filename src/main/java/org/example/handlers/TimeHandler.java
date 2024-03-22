package org.example.handlers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeHandler implements ResponseHandler {

    @Override
    public String handleResponse(String input) {
        input = input.toLowerCase();
        if (input.contains("time")) {
            return getCurrentTime();
        }
        // Gerekirse başka kontrol yapıları eklenebilir
        return "Sorry, I can't provide information on this topic.";
    }

    private String getCurrentTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "The current time is " + time.format(formatter) + ".";
    }
}
