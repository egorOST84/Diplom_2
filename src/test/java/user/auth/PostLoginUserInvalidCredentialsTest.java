package user.auth;

import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import common.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.user.User;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utilities.UserGenerator.generateRandomUser;

@Feature("[User authorization] POST api/auth/login - \"credentials\" validation / negative")
@RunWith(Parameterized.class)
public class PostLoginUserInvalidCredentialsTest extends BaseTest {
    private final String email;
    private final String password;

    public PostLoginUserInvalidCredentialsTest(String description, String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getData() {
        return Stream.of(
                new Object[]{"invalid email", "invalid_email", generateRandomUser().getPassword()},
                new Object[]{"email field is missing", null, generateRandomUser().getPassword()},
                new Object[]{"invalid password", generateRandomUser().getEmail(), "invalid_password"},
                new Object[]{"password field is missing", generateRandomUser().getEmail(), null},
                new Object[]{"all fields are missing", null, null},
                new Object[]{"with unexciting user name", generateRandomUser().getEmail(),  generateRandomUser().getPassword()}
        ).collect(Collectors.toList());
    }

    /**
     * Этот тест проверяет что нельзя авторизоваться с неверными учетными данными.
     */
    @Test
    @DisplayName("User login / \"with invalid credentials\" validation / negative")
    @Description("This test verifies that user with invalid credentials cannot be logged")
    @Severity(SeverityLevel.BLOCKER)
    public void validateLoginUserWithInvalidCredentials() throws Exception {
        User user = new User(email, generateRandomUser().getName(), password);
        Response response = baseSteps.sendLoginUserRequestAndGetResponse(user);
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        baseSteps.checkErrorMessage(response, ErrorMessage.USER_LOGIN_UNAUTHORIZED_ERROR_401);
    }
}
