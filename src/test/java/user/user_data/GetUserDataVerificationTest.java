package user.user_data;

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

@Feature("[User] GET api/auth/user")
public class GetUserDataVerificationTest {
    private static BaseSteps baseSteps;
    private User rndUser;
    private static String accessToken;
    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     *
     * @throws Exception
     *
     * Этот тест проверяет, что данные пользователя могут быть получены после авторизации.
     * Сначала в тесте создается случайный пользователь и выполняется его регистрация
     * с помощью метода registrationNewUserAndVerifyResponse().
     * Затем происходит авторизация созданного пользователя с помощью метода loginUserAndGetToken(),
     * который возвращает токен доступа к API. Наконец, метод getUser() используется
     * для получения информации о пользователе с помощью полученного токена.
     * Если все шаги теста пройдены успешно, то можно утверждать,
     * что получение данных пользователя после авторизации работает корректно.
     */

    @Test
    //@DisplayName("Get user data | authorized")
    @Description("This test verifies that user's can be received after authorization")
    @Severity(SeverityLevel.CRITICAL)
    public void getUserDataAuthorized() throws Exception{
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.getUserAndVerifyResponse(rndUser, accessToken);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя,
     * которого мы зарегистрировали ранее в методе getUserDataAuthorized().
     */
    @After
    public void tearDown() throws Exception {
        baseSteps.deleteRegisteredUser(rndUser, accessToken);
    }
}
