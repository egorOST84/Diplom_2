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
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет, что регистрация пользователя проходит успешно.
     */
    @Test
    @DisplayName("User registration")
    @Description("This test verifies that user can created")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateNewUser() {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
    }
}
