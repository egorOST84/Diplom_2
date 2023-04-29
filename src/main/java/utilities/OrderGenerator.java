package utilities;

import pojo.Ingredient;
import pojo.OrderRequest;

import java.util.ArrayList;
import java.util.List;

public class OrderGenerator {
    public static OrderRequest generateRandomOrder(int numberOfIngredients) {

        List<String> ingredients = new ArrayList<>();

        for (int i = 0; i < numberOfIngredients; i++) {
            Ingredient ingredient = IngredientGenerator.generateRandomIngredient();
            if (ingredient != null) {
                ingredients.add(ingredient.getId());
            }
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(ingredients);
        return orderRequest;
    }
}