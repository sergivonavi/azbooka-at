package ru.azbooka.at.tests.ui.auth;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.MainPage;
import ru.azbooka.at.ui.pages.modals.LoginModal;

import static ru.azbooka.at.data.LoginTestData.getRandomEmail;
import static ru.azbooka.at.data.LoginTestData.getRandomPassword;
import static ru.azbooka.at.ui.pages.modals.LoginModal.ERROR_INPUT_REQUIRED;
import static ru.azbooka.at.ui.pages.modals.LoginModal.ERROR_INVALID_CREDENTIALS;

@DisplayName("Авторизация в системе")
public class LoginUITests extends BaseTest {
    private final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    private final MainPage mainPage = new MainPage();
    private final LoginModal loginModal = new LoginModal();

    @BeforeEach
    void beforeEach() {
        mainPage
                .openPage()
                .clickLoginButton();
    }

    @Test
    @DisplayName("Авторизация с валидными данными")
    void loginWithValidCredentialsTest() {
        loginModal
                .setEmail(userConfig.email())
                .setPassword(userConfig.password())
                .clickSubmit();

        mainPage
                .shouldHaveProfileLink()
                .shouldHaveSignOutButton();
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином")
    void loginWithInvalidEmailTest() {
        loginModal
                .setEmail(getRandomEmail())
                .setPassword(userConfig.password())
                .clickSubmit();

        loginModal.verifyModalErrorMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем")
    void loginWithInvalidPasswordTest() {
        loginModal
                .setEmail(userConfig.email())
                .setPassword(getRandomPassword())
                .clickSubmit();

        loginModal.verifyModalErrorMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация без заполнения полей")
    void loginWithEmptyFieldsTest() {
        loginModal.clickSubmit();

        loginModal
                .verifyEmailInputErrorMessage(ERROR_INPUT_REQUIRED)
                .verifyPasswordInputErrorMessage(ERROR_INPUT_REQUIRED);
    }
}
