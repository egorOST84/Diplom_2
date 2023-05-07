package user.user_data;

import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import common.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import pojo.user.User;

import static utilities.UserGenerator.generateRandomUser;

@Feature("[User] PATCH api/auth/user - validation / negative")
public class PatchUserChangeValidationTest extends BaseTest {
    private User rndUser;

    /**
     * Этот тест проверяет, что при попытке изменить электронную почту и имя пользователя без авторизации
     * должен быть получен ответ с кодом 401 Unauthorized.
     */
    @Test
    @DisplayName("Update user / \"without authorization\" validation / negative")
    @Description("Verify that attempting to change a user's email and name without authorization results in a 401 error")
    @Severity(SeverityLevel.CRITICAL)
    public void validateChangeUserEmailAndNameUnauthorized() throws Exception {
        // Регистрируем нового пользователя
        rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Попытка изменить адрес электронной почты и имя пользователя без предоставления токена доступа
        Response response = baseSteps.sendPatchUserRequestAndGetResponse(rndUser, "newEmail", "newName", "");
        // Проверяем, что код ответа 401 Unauthorized, а сообщение ответа соответствует ожидаемому сообщению об ошибке.
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        baseSteps.checkErrorMessage(response, ErrorMessage.USER_UPDATE_UNAUTHORIZED_ERROR_401);
    }

    /**
     * Этот тест проверяет, что при попытке изменить email пользователя
     * на уже существующий в системе email, будет получена ошибка со статус-кодом 403.
     */
    @Test
    @DisplayName("Update user / \"with existing email\" validation / negative")
    @Description("This test verify that user email cannot be changed to existing user's email")
    @Severity(SeverityLevel.CRITICAL)
    public void validateChangeUserEmailWithExistingEmail() throws Exception {
        // Регистрируем нового пользователя
        rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Регистрируем второго пользователя
        User rndSecondUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndSecondUser);
        // Берем email rndUser и сохраняем в переменную
        String newSecondUserEmail = rndUser.getEmail();
        // Получаем access token второго пользователя
        String accessToken = baseSteps.loginUserAndGetToken(rndSecondUser);
        // Попытка изменить адрес электронной почты на почту существующего пользователя
        Response response = baseSteps.sendPatchUserRequestAndGetResponse(rndUser, newSecondUserEmail, "newName", accessToken);
        // Проверяем, что код ответа 403 Forbidden, а сообщение ответа соответствует ожидаемому сообщению об ошибке.
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        baseSteps.checkErrorMessage(response, ErrorMessage.USER_UPDATE_EXISTING_EMAIL_ERROR_403);
        // Удаляем второго пользователя
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
