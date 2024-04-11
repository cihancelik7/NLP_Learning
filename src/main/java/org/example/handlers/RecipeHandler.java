package org.example.handlers;

import org.example.data.SpoonacularAPI;
import org.example.nlp.NLPProcessor;

public class RecipeHandler implements ResponseHandler{
    private NLPProcessor nlpProcessor;

    public RecipeHandler (NLPProcessor nlpProcessor){
        this.nlpProcessor = nlpProcessor;
    }
    @Override
    public String handleResponse(String input) {
        String query = nlpProcessor.extractQuery(input);
        String apiResponse = SpoonacularAPI.searchReceipes(query);
        return apiResponse;
    }
}
