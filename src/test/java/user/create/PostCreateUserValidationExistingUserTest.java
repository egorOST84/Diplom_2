package user.create;

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

@Feature("[User registration] POST api/auth/register - \"existing user\" validation / negative")
public class PostCreateUserValidationExistingUserTest extends BaseTest {
    private User rndUser;

    /**
     * Этот тест проверяет, что при попытке создания пользователя с уже существующим именем возвращается ошибка с кодом 403.
     */
    @Test
    @DisplayName("User registration / \"with existing name\" validation / negative")
    @Description("This test verifies that user with existing name cannot be created")
    @Severity(SeverityLevel.BLOCKER)
    public void validateCreateNewUserWithExistingName() throws Exception {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        Response response = baseSteps.sendRegistrationUserRequestAndGetResponse(rndUser);
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        baseSteps.checkErrorMessage(response, ErrorMessage.USER_CREATE_SECOND_ATTEMPT_ERROR_403);
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
