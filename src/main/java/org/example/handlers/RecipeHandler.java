package org.example.handlers;

import org.example.data.SpoonacularAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class RecipeHandler implements ResponseHandler {
    private enum State {
        INITIAL, AWAITING_RECIPE_NAME, FETCHING_RECIPE, COMPLETED
    }

    private State currentState = State.INITIAL;
    private String recipeName = "";
    private static final Logger logger = LoggerFactory.getLogger(RecipeHandler.class);

    @Override
    public String handleResponse(String input) {
        Scanner scanner = new Scanner(System.in);

        while (currentState != State.COMPLETED) {
            switch (currentState) {
                case INITIAL:
                    System.out.println("You are in the right place for recipes!! Please tell me the name of the dish you want to learn about.");
                    currentState = State.AWAITING_RECIPE_NAME;
                    break;
                case AWAITING_RECIPE_NAME:
                    recipeName = scanner.nextLine();
                    currentState = State.FETCHING_RECIPE;
                    break;
                case FETCHING_RECIPE:
                    String apiResponse = SpoonacularAPI.searchRecipes(recipeName);
                    logger.debug("API Response: {}", apiResponse); // API yanıtını loglama
                    String formattedResponse = formatApiResponse(apiResponse);
                    System.out.println(formattedResponse);
                    currentState = State.COMPLETED;
                    break;
            }
        }

        resetState();
        return "Recipe fetching process completed.";
    }

    private void resetState() {
        currentState = State.INITIAL;
        recipeName = "";
    }

    private String formatApiResponse(String apiResponse) {
        try {
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() > 0) {
                JSONObject firstResult = results.getJSONObject(0);
                String title = firstResult.getString("title");  // Yemeğin adı
                String ingredientsUrl = firstResult.optString("ingredients", "No ingredients available");  // Resim URL'si, yoksa default mesaj
                String serving = firstResult.optString("servings","No servings available");
                String instructions = firstResult.optString("instructions", "No instructions link available");  // Tarif URL'si, yoksa default mesaj

                return "Title: " + title + "\ningredientsUrl : " + ingredientsUrl + "\nserving : "+serving + "\nRecipe URL: " + instructions;
            } else {
                return "Sorry, no recipes found.";
            }
        } catch (JSONException e) {
            return "Failed to process recipe information.";
        }
    }
}
