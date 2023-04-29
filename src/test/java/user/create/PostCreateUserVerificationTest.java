package user.create;

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

@Feature("[User registration] POST api/auth/register")
public class PostCreateUserVerificationTest {
    private static BaseSteps baseSteps;
    private User rndUser;

    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     *
     * @throws Exception
     *
     * Этот тест создает нового пользователя, используя метод generateRandomUser(),
     * и отправляет запрос на регистрацию пользователя с помощью метода registrationNewUserAndVerifyResponse(),
     * который был реализован в классе BaseSteps.

     * Тест проверяет, что регистрация пользователя прошла успешно, используя утверждения,
     * которые включены в метод registrationNewUserAndVerifyResponse().
     */
    @Test
    //@DisplayName("User registration")
    @Description("This test verifies that user can created")
    @Severity(SeverityLevel.BLOCKER)
    public void createNewUser() throws Exception {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
    }
    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя,
     * которого мы зарегистрировали ранее в методе createNewUser().
     * Для этого сначала получается accessToken пользователя с помощью метода loginUserAndGetToken(),
     * затем этот токен используется для вызова метода deleteRegisteredUser(), который отправляет запрос на удаление пользователя на сервере.
     */
    @After
    public void tearDown() throws Exception {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(rndUser, accessToken);
    }
}
