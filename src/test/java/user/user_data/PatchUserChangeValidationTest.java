package user.user_data;

import common.BaseSteps;
import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.User;

import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[User] PATCH api/auth/user - validation / negative")
public class PatchUserChangeValidationTest {
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
     * Этот тест проверяет, что при попытке изменить электронную почту и имя пользователя без авторизации
     * должен быть получен ответ с кодом 401 Unauthorized. Вначале тест регистрирует нового пользователя,
     * затем отправляет запрос на изменение электронной почты и имени пользователя без токена авторизации
     * и проверяет, что полученный ответ содержит ожидаемое сообщение об ошибке. Если ответ содержит другой
     * код статуса или сообщение об ошибке отличается от ожидаемого, то тест считается неудачным.
     * Этот тест является критическим, так как он проверяет важную функциональность системы - правильность
     * обработки запросов на изменение данных пользователя без авторизации.
     */

    @Test
    //@DisplayName("Update user / \"without authorization\" validation / negative")
    @Description("Verify that attempting to change a user's email and name without authorization results in a 401 error")
    @Severity(SeverityLevel.CRITICAL)
    public void changeUserEmailAndNameUnauthorized() throws Exception {
        // Регистрируем нового пользователя
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Попытка изменить адрес электронной почты и имя пользователя без предоставления токена доступа
        Response response = baseSteps.sendPatchUserRequestAndGetResponse(rndUser, "newEmail", "newName", "");
        // Проверяем, что код ответа 401 Unauthorized, а сообщение ответа соответствует ожидаемому сообщению об ошибке.
        baseSteps.checkResponse(response, ErrorMessage.USER_UPDATE_UNAUTHORIZED_ERROR_401);
    }

    /**
     *
     * @throws Exception
     *
     * Этот тест проверяет, что при попытке изменить email пользователя
     * на уже существующий в системе email, будет получена ошибка со статус кодом 403.
     */
    @Test
    //@DisplayName("Update user / \"with existing email\" validation / negative")
    @Description("")
    @Severity(SeverityLevel.CRITICAL)
    public void validateChangeUserEmailWithExistingEmail() throws Exception {
        // Регистрируем нового пользователя
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Регистрируем второго пользователя
        User rndSecondUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndSecondUser);
        // Берем email rndUser и сохраняем в переменную
        String newSecondUserEmail = rndUser.getEmail();
        // Получаем access token второго пользователя
        String accessToken = baseSteps.loginUserAndGetToken(rndSecondUser);
        // Попытка изменить адрес электронной почты на почту существующего пользователя
        Response response = baseSteps.sendPatchUserRequestAndGetResponse(rndUser, newSecondUserEmail, "newName", accessToken);
        // Проверяем, что код ответа 403 Forbidden, а сообщение ответа соответствует ожидаемому сообщению об ошибке.
        baseSteps.checkResponse(response, ErrorMessage.USER_UPDATE_EXISTING_EMAIL_ERROR_403);
        // Удаляем второго пользователя
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя,
     * которого мы зарегистрировали ранее в методе changeUserEmailAuthorized().
     */
    @After
    public void tearDown() throws Exception {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
