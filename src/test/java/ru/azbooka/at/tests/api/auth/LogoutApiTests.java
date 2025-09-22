package ru.azbooka.at.tests.api.auth;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.error.ErrorResponseDto;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import static ru.azbooka.at.api.assertions.error.ErrorAssertions.Detail.INVALID_TOKEN;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.Detail.UNAUTHORIZED;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.assertErrorDetail;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.loginWithConfiguredUser;
import static ru.azbooka.at.api.endpoints.auth.LogoutApi.logout;
import static ru.azbooka.at.api.endpoints.auth.LogoutApi.logoutWithError;
import static ru.azbooka.at.data.LoginTestData.getRandomToken;

@Owner("sergivonavi")
@Layer("api")
@Microservice("UserService")
@Tag("api")
@Epic("Пользователи")
@Feature("Авторизация (API)")
@Story("Выход пользователя из системы")
@DisplayName("API: Выход из системы")
public class LogoutApiTests extends BaseTest {

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @DisplayName("Выход из аккаунта с валидным токеном")
    @Severity(SeverityLevel.CRITICAL)
    void logoutWithValidTokenTest() {
        LoginResponseDto loginResponseDto = loginWithConfiguredUser();
        logout(loginResponseDto.getAccessToken());
    }

    @Tag("regress")
    @Test
    @DisplayName("Выход из аккаунта с невалидным токеном")
    @Severity(SeverityLevel.NORMAL)
    void logoutWithInvalidTokenTest() {
        ErrorResponseDto responseDto = logoutWithError(getRandomToken());

        assertErrorDetail(responseDto, INVALID_TOKEN);
    }

    @Tag("regress")
    @Test
    @DisplayName("Выход из аккаунта без токена")
    @Severity(SeverityLevel.NORMAL)
    void logoutWithoutTokenTest() {
        ErrorResponseDto responseDto = logoutWithError(null);

        assertErrorDetail(responseDto, UNAUTHORIZED);
    }
}
