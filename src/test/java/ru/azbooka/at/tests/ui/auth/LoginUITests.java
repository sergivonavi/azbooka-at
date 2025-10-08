package ru.azbooka.at.tests.ui.auth;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.MainPage;
import ru.azbooka.at.ui.pages.modals.LoginModal;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import static ru.azbooka.at.data.LoginTestData.getRandomEmail;
import static ru.azbooka.at.data.LoginTestData.getRandomPassword;
import static ru.azbooka.at.ui.pages.modals.LoginModal.ERROR_INPUT_REQUIRED;
import static ru.azbooka.at.ui.pages.modals.LoginModal.ERROR_INVALID_CREDENTIALS;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("UserService")
@Tag("ui")
@Epic("Пользователи")
@Feature("Авторизация (UI)")
@Story("Авторизация пользователя в системе через UI")
@DisplayName("UI: Авторизация в системе")
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

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @DisplayName("Авторизация с валидными данными")
    @Severity(SeverityLevel.BLOCKER)
    void loginWithValidCredentialsTest() {
        loginModal
                .setEmail(userConfig.email())
                .setPassword(userConfig.password())
                .clickSubmit();

        mainPage
                .shouldHaveProfileLink()
                .shouldHaveSignOutButton();
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с несуществующим логином")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithInvalidEmailTest() {
        loginModal
                .setEmail(getRandomEmail())
                .setPassword(userConfig.password())
                .clickSubmit();

        loginModal.verifyModalErrorMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с неправильным паролем")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithInvalidPasswordTest() {
        loginModal
                .setEmail(userConfig.email())
                .setPassword(getRandomPassword())
                .clickSubmit();

        loginModal.verifyModalErrorMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация без заполнения полей")
    @Severity(SeverityLevel.NORMAL)
    void loginWithEmptyFieldsTest() {
        loginModal.clickSubmit();

        loginModal
                .verifyEmailInputErrorMessage(ERROR_INPUT_REQUIRED)
                .verifyPasswordInputErrorMessage(ERROR_INPUT_REQUIRED);
    }
}
