package ru.azbooka.at.tests.api.auth;

import io.qameta.allure.Owner;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.error.ErrorResponseDto;
import ru.azbooka.at.api.models.error.ValidationErrorResponseDto;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.data.LoginTestData;
import ru.azbooka.at.tests.BaseTest;

import static ru.azbooka.at.api.assertions.LoginAssertions.assertAccessTokenIsNotNullOrEmpty;
import static ru.azbooka.at.api.assertions.LoginAssertions.assertRefreshTokenIsNotNullOrEmpty;
import static ru.azbooka.at.api.assertions.LoginAssertions.assertTokenTypeIsBearer;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.Detail.INVALID_CREDENTIALS;
import static ru.azbooka.at.api.assertions.error.ErrorAssertions.assertErrorDetail;
import static ru.azbooka.at.api.assertions.error.ValidationErrorAssertions.assertDetailNotEmpty;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.login;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.loginWithIncorrectCredentials;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.loginWithInvalidBody;
import static ru.azbooka.at.api.endpoints.auth.LoginApi.loginWithoutBody;

@Owner("sergivonavi")
@DisplayName("Авторизация в системе")
public class LoginApiTests extends BaseTest {
    private final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    @Test
    @DisplayName("Авторизация с валидными данными через API")
    void loginWithValidCredentialsTest() {
        LoginResponseDto responseDto = login(userConfig.email(), userConfig.password());

        assertAccessTokenIsNotNullOrEmpty(responseDto);
        assertRefreshTokenIsNotNullOrEmpty(responseDto);
        assertTokenTypeIsBearer(responseDto);
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином через API")
    void loginWithInvalidUsernameTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(
                LoginTestData.DATA_INVALID_EMAIL,
                userConfig.password()
        );

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем через API")
    void loginWithInvalidPasswordTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(
                userConfig.email(),
                LoginTestData.DATA_INVALID_PASSWORD
        );

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с пустым логином")
    void loginWithEmptyLoginTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials("", userConfig.password());

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с пустым паролем")
    void loginWithEmptyPassword() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(userConfig.email(), "");

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с отсутствующим логином")
    void loginWithoutLoginTest() {
        ValidationErrorResponseDto responseDto = loginWithInvalidBody(null, userConfig.password());

        assertDetailNotEmpty(responseDto);
    }

    @Test
    @DisplayName("Авторизация с отсутствующим паролем")
    void loginWithoutPasswordTest() {
        ValidationErrorResponseDto responseDto = loginWithInvalidBody(userConfig.email(), null);

        assertDetailNotEmpty(responseDto);
    }

    @Test
    @DisplayName("Авторизация с отсутствующим телом запроса")
    void loginWithoutBodyTest() {
        ValidationErrorResponseDto responseDto = loginWithoutBody();

        assertDetailNotEmpty(responseDto);
    }
}
