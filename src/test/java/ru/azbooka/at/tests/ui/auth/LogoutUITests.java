package ru.azbooka.at.tests.ui.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.MainPage;
import ru.azbooka.at.ui.pages.ProfilePage;

@DisplayName("Выход из системы")
public class LogoutUITests extends BaseTest {
    private final MainPage mainPage = new MainPage();

    @BeforeEach
    void setup() {
        mainPage.openPage();
    }

    @Test
    @WithLogin
    @DisplayName("Выход из аккаунта")
    public void logoutTest() {
        mainPage.clickSignOutButton();

        mainPage
                .shouldHaveLoginButton()
                .shouldNotHaveSignOutButton()
                .shouldNotHaveProfileLink();
    }

    @Test
    @WithLogin
    @DisplayName("Переход на страницу профиля после выхода из аккаунта")
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
