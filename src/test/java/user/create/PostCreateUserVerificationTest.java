package user.create;

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

@Feature("[User registration] POST api/auth/register")
public class PostCreateUserVerificationTest extends BaseTest {
    private User rndUser;

    /**
     * Этот тест проверяет, что регистрация пользователя проходит успешно.
     */
    @Test
    @DisplayName("User registration")
    @Description("This test verifies that user can created")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateNewUser() {
        rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
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
