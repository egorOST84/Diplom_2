package utilities;

import com.github.javafaker.Faker;
import pojo.Ingredient;

import java.util.HashMap;
import java.util.Random;

import static configs.RndConf.*;

public class IngredientGenerator {

    private static final Random RANDOM = new Random();
    public static Ingredient generateRandomIngredient(){

        Faker faker = new Faker();
        //HashMap<String, String> INGREDIENTS = new HashMap<>();

        String randomIngredientType = INGREDIENT_TYPES[RANDOM.nextInt(INGREDIENT_TYPES.length)];
        String randomKey = INGREDIENTS.keySet().toArray(new String[0])[RANDOM.nextInt(INGREDIENTS.size())];
        String randomIngredientId = INGREDIENTS.get(randomKey);

        String imageUrl = faker.internet().image();
        String imageMobileUrl = faker.internet().image(MIN_WIDTH_MOBILE, MAX_HEIGHT_MOBILE, false, "ingredients-mobile");
        String imageLargeUrl = faker.internet().image(MIN_WIDTH_LARGE, MAX_HEIGHT_LARGE, false, "ingredients-large");
        String ingredientName = "Cosmic_" + faker.food().ingredient();

        Ingredient rndIngredient = new Ingredient()
                .setId(randomIngredientId);
//                .setName(ingredientName)
//                .setType(randomIngredientType)
//                .setProteins(faker.number().numberBetween(MIN_PROTEINS, MAX_PROTEINS))
//                .setFat(faker.number().numberBetween(MIN_FAT, MAX_FAT))
//                .setCarbohydrates(faker.number().numberBetween(MIN_CARBS, MAX_CARBS))
//                .setCalories(faker.number().numberBetween(MIN_CALORIES, MAX_CALORIES))
//                .setPrice(faker.number().numberBetween(MIN_PRICE, MAX_PRICE))
//                .setImage(imageUrl)
//                .setImageMobile(imageMobileUrl)
//                .setImageLarge(imageLargeUrl);

        return rndIngredient;
    }
}
