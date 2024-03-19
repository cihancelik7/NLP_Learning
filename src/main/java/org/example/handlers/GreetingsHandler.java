package org.example.handlers;

public class GreetingsHandler implements ResponseHandler{
    @Override
    public String handleResponse(String input) {
        return "Hello how can i help you ?";
    }
}
