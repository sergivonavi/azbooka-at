package ru.azbooka.at.tests.ui.auth;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.MainPage;
import ru.azbooka.at.ui.pages.ProfilePage;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("UserService")
@Tag("ui")
@Epic("Пользователи")
@Feature("Авторизация (UI)")
@Story("Выход пользователя из системы через UI")
@DisplayName("UI: Выход из системы")
public class LogoutUITests extends BaseTest {
    private final MainPage mainPage = new MainPage();

    @BeforeEach
    void setup() {
        mainPage.openPage();
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Выход из аккаунта")
    @Severity(SeverityLevel.CRITICAL)
    public void logoutTest() {
        mainPage.clickSignOutButton();

        mainPage
                .shouldHaveLoginButton()
                .shouldNotHaveSignOutButton()
                .shouldNotHaveProfileLink();
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Переход на страницу профиля после выхода из аккаунта")
    @Severity(SeverityLevel.NORMAL)
    public void openProfilePageAfterLogout() {
        mainPage.clickSignOutButton();

        mainPage
                .shouldHaveLoginButton()
                .shouldNotHaveSignOutButton()
                .shouldNotHaveProfileLink();

        new ProfilePage()
                .openPage()
                .verifyUnauthorizedHeader()
                .shouldNotHaveSignOutButton()
                .shouldNotHaveProfileLink();
    }
}
