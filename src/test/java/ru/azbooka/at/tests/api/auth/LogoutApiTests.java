package ru.azbooka.at.tests.api.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.error.ErrorResponseDto;
import ru.azbooka.at.tests.BaseTest;

import static ru.azbooka.at.api.assertions.error.ErrorAssertions.Detail.INVALID_TOKEN;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.Detail.UNAUTHORIZED;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.assertErrorDetail;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.loginWithConfiguredUser;
import static ru.azbooka.at.api.endpoints.auth.LogoutApi.logout;
import static ru.azbooka.at.api.endpoints.auth.LogoutApi.logoutWithError;
import static ru.azbooka.at.data.LoginTestData.getRandomToken;

@DisplayName("Выход из системы")
public class LogoutApiTests extends BaseTest {

    @Test
    @DisplayName("Выход из аккаунта с валидным токеном")
    void logoutWithValidTokenTest() {
        LoginResponseDto loginResponseDto = loginWithConfiguredUser();
        logout(loginResponseDto.getAccessToken());
    }

    @Test
    @DisplayName("Выход из аккаунта с невалидным токеном")
    void logoutWithInvalidTokenTest() {
        ErrorResponseDto responseDto = logoutWithError(getRandomToken());

        assertErrorDetail(responseDto, INVALID_TOKEN);
    }

    @Test
    @DisplayName("Выход из аккаунта без токена")
    void logoutWithoutTokenTest() {
        ErrorResponseDto responseDto = logoutWithError(null);

        assertErrorDetail(responseDto, UNAUTHORIZED);
    }
}
