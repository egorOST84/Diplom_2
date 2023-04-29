package user.create;

import common.BaseSteps;
import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.User;

import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[User registration] POST api/auth/register - \"existing user\" validation / negative")
public class PostCreateUserValidationExistingUserTest {
    private static BaseSteps baseSteps;
    private User rndUser;

    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * Этот тест проверяет, что при попытке создания пользователя с уже существующим именем возвращается ошибка с кодом 403 (запрещено).

     * В этом тесте сначала создается новый пользователь (rndUser) с помощью метода generateRandomUser(),
     * затем создание пользователя происходит с помощью метода registrationNewUserAndVerifyResponse(rndUser),
     * чтобы убедиться, что пользователь успешно создан.

     * Затем повторно отправляется запрос на создание пользователя с тем же именем, и ожидается,
     * что в ответе вернется ошибка с кодом 403 (запрещено), согласно описанию теста.
     */

    @Test
    @DisplayName("User registration / \"with existing name\" validation / negative")
    @Description("This test verifies that user with existing name cannot be created")
    @Severity(SeverityLevel.BLOCKER)
    public void createNewUserWithExistingName() throws Exception {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        Response response = baseSteps.sendRegistrationUserRequestAndGetResponse(rndUser);
        baseSteps.checkResponse(response, ErrorMessage.USER_CREATE_SECOND_ATTEMPT_ERROR_403);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя,
     * которого мы зарегистрировали ранее в методе createNewUserWithExistingName().
     * Для этого сначала получается accessToken пользователя с помощью метода loginUserAndGetToken(),
     * затем этот токен используется для вызова метода deleteRegisteredUser(), который отправляет запрос на удаление пользователя на сервере.
     */
    @After
    public void tearDown() throws Exception {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(rndUser, accessToken);
    }
}
