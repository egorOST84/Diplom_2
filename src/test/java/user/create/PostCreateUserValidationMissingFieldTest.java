package user.create;

import common.BaseSteps;
import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
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
    private final String email;
    private final String name;
    private final String password;
    private static BaseSteps baseSteps;

    public PostCreateUserValidationMissingFieldTest(String description, String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getData() {
        return Stream.of(
                new Object[]{"email field is missing", null, generateRandomUser().getName(), generateRandomUser().getPassword()},
                new Object[]{"name field is missing", generateRandomUser().getEmail(), null, generateRandomUser().getPassword()},
                new Object[]{"password field is missing", generateRandomUser().getEmail(), generateRandomUser().getName(), null},
                new Object[]{"all fields are missing", null, null, null}
        ).collect(Collectors.toList());
    }


    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * Этот тест проверяет, что пользователь не может быть создан без обязательных полей,
     * таких как электронная почта, пароль или имя.
     */
    @Test
    @DisplayName("User registration / \"without required fields\" validation / negative")
    @Description("This test verifies that user without email, password or name cannot created")
    @Severity(SeverityLevel.BLOCKER)
    public void validateCreateNewUserWithMissingField() throws Exception {
        User user = new User(email, name, password);
        Response response = baseSteps.sendRegistrationUserRequestAndGetResponse(user);
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        baseSteps.checkErrorMessage(response, ErrorMessage.USER_CREATE_MISSING_FIELDS_ERROR_403);
    }
}
