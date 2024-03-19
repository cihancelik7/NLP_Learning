package org.example.handlers;

public class FarewellsHandler implements ResponseHandler{
    @Override
    public String handleResponse(String input) {
        return "goodbye nice to meet you, when do you need me, I am here :)";
    }
}
