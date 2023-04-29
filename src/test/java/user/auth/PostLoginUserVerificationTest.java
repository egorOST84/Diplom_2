package user.auth;

import common.BaseSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.User;

import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[User authorization] POST api/auth/login")
public class PostLoginUserVerificationTest {
    private static BaseSteps baseSteps;
    private User rndUser;
    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * Этот тест проверяет возможность входа пользователя в систему после его регистрации.
     * Сначала в тесте создается случайный пользователь rndUser, затем выполняется регистрация
     * этого пользователя при помощи registrationNewUserAndVerifyResponse и проверяется, что регистрация прошла успешно.
     * После этого выполняется вход пользователя в систему при помощи метода loginUser и проверяется, что пользователь успешно авторизовался.
     */
    @Test
    @DisplayName("User login")
    @Description("This test verifies that user can be logged after it's registration")
    @Severity(SeverityLevel.BLOCKER)
    public void loginUser() {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        baseSteps.loginUser(rndUser);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя,
     * которого мы зарегистрировали ранее в методе loginUser().
     * Для этого сначала получается accessToken пользователя с помощью метода loginUserAndGetToken(),
     * затем этот токен используется для вызова метода deleteRegisteredUser(), который отправляет запрос на удаление пользователя на сервере.
     */
    @After
    public void tearDown() throws Exception {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(rndUser, accessToken);
    }
}
