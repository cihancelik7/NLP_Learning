package org.example.handlers;

public class DateHandler implements ResponseHandler{
    @Override
    public String handleResponse(String input) {
        return "Date: "+ java.time.LocalDate.now().toString();
    }
}
