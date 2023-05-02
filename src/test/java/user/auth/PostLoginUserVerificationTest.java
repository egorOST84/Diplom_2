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
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет возможность входа пользователя в систему после его регистрации.
     */
    @Test
    @DisplayName("User login")
    @Description("This test verifies that user can be logged after it's registration")
    @Severity(SeverityLevel.BLOCKER)
    public void checkLoginUser() {
        rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        baseSteps.loginUser(rndUser);
    }
}
