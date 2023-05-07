package user.auth;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import common.BaseTest;
import org.junit.After;
import org.junit.Test;
import pojo.user.User;

import static utilities.UserGenerator.generateRandomUser;

@Feature("[User authorization] POST api/auth/login")
public class PostLoginUserVerificationTest extends BaseTest {
    private User rndUser;

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

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
