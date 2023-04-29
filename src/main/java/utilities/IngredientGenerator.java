package utilities;

import com.github.javafaker.Faker;
import pojo.Ingredient;

import java.util.Random;

import static configs.RndConf.*;

public class IngredientGenerator {

    private static final Random RANDOM = new Random();
    public static Ingredient generateRandomIngredient(){

        Faker faker = new Faker();

        String randomIngredientType = INGREDIENT_TYPES[RANDOM.nextInt(INGREDIENT_TYPES.length)];
        String imageUrl = faker.internet().image();
        String imageMobileUrl = faker.internet().image(MIN_WIDTH_MOBILE, MAX_HEIGHT_MOBILE, false, "ingredients-mobile");
        String imageLargeUrl = faker.internet().image(MIN_WIDTH_LARGE, MAX_HEIGHT_LARGE, false, "ingredients-large");
        String ingredientName = "Cosmic_" + faker.food().ingredient();

        Ingredient rndIngredient = new Ingredient()
                .setId(faker.random().hex(8))
                .setName(ingredientName)
                .setType(randomIngredientType)
                .setProteins(faker.number().numberBetween(MIN_PROTEINS, MAX_PROTEINS))
                .setFat(faker.number().numberBetween(MIN_FAT, MAX_FAT))
                .setCarbohydrates(faker.number().numberBetween(MIN_CARBS, MAX_CARBS))
                .setCalories(faker.number().numberBetween(MIN_CALORIES, MAX_CALORIES))
                .setPrice(faker.number().numberBetween(MIN_PRICE, MAX_PRICE))
                .setImage(imageUrl)
                .setImageMobile(imageMobileUrl)
                .setImageLarge(imageLargeUrl);

        return rndIngredient;
    }
}
