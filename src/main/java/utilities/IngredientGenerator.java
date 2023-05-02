package utilities;

import configs.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngredientGenerator {

    private static final Random RANDOM = new Random();
    public static Ingredient generateRandomIngredient(){

        Response response = RestAssured.get(EndPoints.INGREDIENT_ENDPOINT);
        List<String> idList = new ArrayList<>(response.jsonPath().getList("data._id", String.class));
        String randomId = idList.get(RANDOM.nextInt(idList.size()));

        Ingredient rndIngredient = new Ingredient()
                .setId(randomId);

        return rndIngredient;
    }
}
