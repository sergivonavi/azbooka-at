package ru.azbooka.at.tests.api.auth;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.error.ErrorResponseDto;
import ru.azbooka.at.api.models.error.ValidationErrorResponseDto;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

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
import static ru.azbooka.at.data.LoginTestData.getRandomEmail;
import static ru.azbooka.at.data.LoginTestData.getRandomPassword;

@Owner("sergivonavi")
@Layer("api")
@Microservice("UserService")
@Tag("api")
@Epic("Пользователи")
@Feature("Авторизация (API)")
@Story("Авторизация пользователя в системе")
@DisplayName("API: Авторизация в системе")
public class LoginApiTests extends BaseTest {
    private final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @DisplayName("Авторизация с валидными данными через API")
    @Severity(SeverityLevel.BLOCKER)
    void loginWithValidCredentialsTest() {
        LoginResponseDto responseDto = login(userConfig.email(), userConfig.password());

        assertAccessTokenIsNotNullOrEmpty(responseDto);
        assertRefreshTokenIsNotNullOrEmpty(responseDto);
        assertTokenTypeIsBearer(responseDto);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с несуществующим логином через API")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithInvalidUsernameTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(getRandomEmail(), userConfig.password());

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с неправильным паролем через API")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithInvalidPasswordTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(userConfig.email(), getRandomPassword());

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с пустым логином")
    @Severity(SeverityLevel.NORMAL)
    void loginWithEmptyLoginTest() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials("", userConfig.password());

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с пустым паролем")
    @Severity(SeverityLevel.NORMAL)
    void loginWithEmptyPassword() {
        ErrorResponseDto responseDto = loginWithIncorrectCredentials(userConfig.email(), "");

        assertErrorDetail(responseDto, INVALID_CREDENTIALS);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с отсутствующим логином")
    @Severity(SeverityLevel.NORMAL)
    void loginWithoutLoginTest() {
        ValidationErrorResponseDto responseDto = loginWithInvalidBody(null, userConfig.password());

        assertDetailNotEmpty(responseDto);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с отсутствующим паролем")
    @Severity(SeverityLevel.NORMAL)
    void loginWithoutPasswordTest() {
        ValidationErrorResponseDto responseDto = loginWithInvalidBody(userConfig.email(), null);

        assertDetailNotEmpty(responseDto);
    }

    @Tag("regress")
    @Test
    @DisplayName("Авторизация с отсутствующим телом запроса")
    @Severity(SeverityLevel.MINOR)
    void loginWithoutBodyTest() {
        ValidationErrorResponseDto responseDto = loginWithoutBody();

        assertDetailNotEmpty(responseDto);
    }
}
