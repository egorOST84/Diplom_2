package user.auth;

import common.BaseSteps;
import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.User;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[User authorization] POST api/auth/login - \"credentials\" validation / negative")
@RunWith(Parameterized.class)
public class PostLoginUserInvalidCredentialsTest {
    private final String description;
    private final String email;
    private final String password;
    private final ErrorMessage errorMessage;
    private static BaseSteps baseSteps;

    public PostLoginUserInvalidCredentialsTest(String description, String email, String password, ErrorMessage errorMessage) {
        this.description = description;
        this.email = email;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getData() {
        return Stream.of(
                new Object[]{"invalid email", "invalid_email", generateRandomUser().getPassword(), ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401},
                new Object[]{"email field is missing", null, generateRandomUser().getPassword(), ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401},
                new Object[]{"invalid password", generateRandomUser().getEmail(), "invalid_password", ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401},
                new Object[]{"password field is missing", generateRandomUser().getEmail(), null, ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401},
                new Object[]{"all fields are missing", null, null, ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401},
                new Object[]{"with unexciting user name", generateRandomUser().getEmail(),  generateRandomUser().getPassword(), ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401}
        ).collect(Collectors.toList());
    }


    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * @throws Exception
     *
     * Этот тест проверяет поведение системы при попытке входа пользователя с неверными учетными данными.
     * В методе теста loginUserWithInvalidCredentials() сначала создается объект User с некоторыми корректными данными,
     * такими как email и password, но с неправильным именем пользователя.
     * Затем отправляется запрос на вход с этими учетными данными, и полученный ответ проверяется на соответствие ожидаемому.
     * Если ответ содержит ожидаемое сообщение об ошибке, то тест считается пройденным, иначе он не пройден.
     */

    @Test
    //@DisplayName("User login / \"with invalid credentials\" validation / negative")
    @Description("This test verifies that user with invalid credentials cannot be logged")
    @Severity(SeverityLevel.BLOCKER)
    public void loginUserWithInvalidCredentials() throws Exception {
        User user = new User(email, generateRandomUser().getName(), password);
        Response response = baseSteps.sendLoginUserRequestAndGetResponse(user);
        baseSteps.checkResponse(response, errorMessage);
    }
}
