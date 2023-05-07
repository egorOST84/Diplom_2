package utilities;

import com.github.javafaker.Faker;
import pojo.user.User;

import java.util.Locale;

public class UserGenerator {
    public static User generateRandomUser() {
        Faker faker = new Faker(new Locale("en-US"));

        return new User()
                .setName(faker.name().fullName())
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.regexify("[a-zA-Z0-9]{12}"));
    }
}
