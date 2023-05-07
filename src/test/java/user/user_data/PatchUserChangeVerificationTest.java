package user.user_data;

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

@Feature("[User] PATCH api/auth/user")
public class PatchUserChangeVerificationTest extends BaseTest {
    private static String accessToken;

    /**
     * Этот тест проверяет возможность изменения email пользователя после авторизации.
     */
    @Test
    @DisplayName("Change user email and name | authorized")
    @Description("This test verifies that user email and name can be changed after authorization")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangeUserEmailAndNameAuthorized() {
        // Регистрируем нового пользователя
        User rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Обновляем email и имя пользователя
        String newEmail = "new_" + rndUser.getEmail();
        String newName = "new_" + rndUser.getName();
        baseSteps.updateUserEmailAndName(rndUser, newEmail, newName, accessToken);
        // Проверяем, что email и имя пользователя были успешно обновлены
        baseSteps.getUserAndVerifyResponse(rndUser, accessToken);
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
