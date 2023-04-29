package user.create;

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

@Feature("[User registration] POST api/auth/register - \"fields\" validation / negative")
@RunWith(Parameterized.class)
public class PostCreateUserValidationMissingFieldTest {
    private final String description;
    private final String email;
    private final String name;
    private final String password;
    private final ErrorMessage errorMessage;
    private static BaseSteps baseSteps;

    public PostCreateUserValidationMissingFieldTest(String description, String email, String name, String password, ErrorMessage errorMessage) {
        this.description = description;
        this.email = email;
        this.name = name;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getData() {
        return Stream.of(
                new Object[]{"email field is missing", null, generateRandomUser().getName(), generateRandomUser().getPassword(), ErrorMessage.USER_CREATE_MISSING_FIELDS_ERROR_403},
                new Object[]{"name field is missing", generateRandomUser().getEmail(), null, generateRandomUser().getPassword(), ErrorMessage.USER_CREATE_MISSING_FIELDS_ERROR_403},
                new Object[]{"password field is missing", generateRandomUser().getEmail(), generateRandomUser().getName(), null, ErrorMessage.USER_CREATE_MISSING_FIELDS_ERROR_403},
                new Object[]{"all fields are missing", null, null, null, ErrorMessage.USER_CREATE_MISSING_FIELDS_ERROR_403}
        ).collect(Collectors.toList());
    }


    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     *
     * @throws Exception
     *
     * Этот тест проверяет, что пользователь не может быть создан без обязательных полей,
     * таких как электронная почта, пароль или имя.
     * Для этого создается новый пользователь без указания одного из обязательных полей,
     * отправляется запрос на создание пользователя
     * и полученный ответ проверяется на наличие соответствующего сообщения об ошибке.
     * Шаги теста включают в себя создание нового пользователя без одного или всех из обязательных полей,
     * отправку запроса на создание пользователя и проверку ответа на наличие ожидаемого сообщения
     * об ошибке с помощью метода checkResponse().
     */
    @Test
    //@DisplayName("User registration / \"without required fields\" validation / negative")
    @Description("This test verifies that user without email, password or name cannot created")
    @Severity(SeverityLevel.BLOCKER)
    public void createNewUserWithMissingField() throws Exception {
        User user = new User(email, name, password);
        Response response = baseSteps.sendRegistrationUserRequestAndGetResponse(user);
        baseSteps.checkResponse(response, errorMessage);
    }
}
