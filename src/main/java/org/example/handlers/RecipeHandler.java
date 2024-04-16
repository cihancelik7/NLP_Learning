package org.example.handlers;

import org.example.data.ApiNinjasAPI;
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
                    String apiResponse = ApiNinjasAPI.searchRecipes(recipeName);
                    logger.debug("API Response: {}", apiResponse); // Log the API response
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
            JSONArray recipes = new JSONArray(apiResponse);
            if (recipes.length() > 0) {
                JSONObject firstRecipe = recipes.getJSONObject(0);
                String title = firstRecipe.getString("title");
                String ingredients = firstRecipe.getString("ingredients");
                String servings = firstRecipe.getString("servings");
                String instructions = firstRecipe.getString("instructions");

                return String.format("Title: %s\nIngredients: %s\nServings: %s\nInstructions: %s", title, ingredients, servings, instructions);
            } else {
                return "Sorry, no recipes found.";
            }
        } catch (JSONException e) {
            return "Failed to process recipe information.";
        }
    }
}