package ru.azbooka.at.tests.api.auth;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.UpdateUserRequestDto;
import ru.azbooka.at.api.models.UpdateUserResponseDto;
import ru.azbooka.at.api.models.UserResponseDto;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import static ru.azbooka.at.api.assertions.UserAssertions.assertAvatarChanged;
import static ru.azbooka.at.api.assertions.UserAssertions.assertEmail;
import static ru.azbooka.at.api.assertions.UserAssertions.assertFirstName;
import static ru.azbooka.at.api.assertions.UserAssertions.assertLastName;
import static ru.azbooka.at.api.assertions.UserAssertions.assertUsername;
import static ru.azbooka.at.api.endpoints.auth.UserApi.getUser;
import static ru.azbooka.at.api.endpoints.auth.UserApi.updateUser;
import static ru.azbooka.at.data.UserTestData.DEFAULT_AVATAR;
import static ru.azbooka.at.data.UserTestData.DEFAULT_FIRST_NAME;
import static ru.azbooka.at.data.UserTestData.DEFAULT_LAST_NAME;
import static ru.azbooka.at.data.UserTestData.getRandomFirstName;
import static ru.azbooka.at.data.UserTestData.getRandomLastName;

@Owner("sergivonavi")
@Layer("api")
@Microservice("UserService")
@Tag("api")
@Epic("Пользователи")
@Feature("Профиль пользователя")
@DisplayName("API: Информация о пользователе")
public class UserApiTests extends BaseTest {
    private final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    private String token;

    @BeforeEach
    void setup() {
        LoginResponseDto loginResponseDto = LoginApi.loginWithConfiguredUser();
        token = loginResponseDto.getAccessToken();

        setDefaultUserData();
    }

    @AfterEach
    void cleanup() {
        setDefaultUserData();
    }

    private void setDefaultUserData() {
        UpdateUserRequestDto requestDto = buildUpdateUserRequestDto(
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_AVATAR
        );
        updateUser(token, requestDto);
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @Story("Получение информации о пользователе")
    @DisplayName("Получение информации о текущем пользователе")
    @Severity(SeverityLevel.CRITICAL)
    void getUserWithValidTokenTest() {
        UserResponseDto responseDto = getUser(token);

        assertUsername(responseDto, userConfig.email());
        assertEmail(responseDto, userConfig.email());
        assertFirstName(responseDto, DEFAULT_FIRST_NAME);
        assertLastName(responseDto, DEFAULT_LAST_NAME);
    }

    @Tag("regress")
    @Test
    @Story("Обновление информации о пользователе")
    @DisplayName("Изменение информации о текущем пользователе без загрузки аватара")
    @Severity(SeverityLevel.NORMAL)
    void updateUserTest() {
        String newFirstName = getRandomFirstName();
        String newLastName = getRandomLastName();

        UpdateUserRequestDto requestDto = buildUpdateUserRequestDto(newFirstName, newLastName, null);

        UpdateUserResponseDto responseDto = updateUser(token, requestDto);
        UserResponseDto userResponseDto = responseDto.getUser();

        assertUsername(userResponseDto, userConfig.email());
        assertEmail(userResponseDto, userConfig.email());
        assertFirstName(userResponseDto, newFirstName);
        assertLastName(userResponseDto, newLastName);
    }

    @Tag("regress")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"avatar/avatar1.jpg", "avatar/avatar2.jpeg", "avatar/avatar3.webp"})
    @Story("Обновление информации о пользователе")
    @DisplayName("Загрузка аватара пользователя")
    @Severity(SeverityLevel.MINOR)
    void uploadAvatarTest(String avatarPath) {
        UserResponseDto initialUserResponseDto = getUser(token);

        UpdateUserRequestDto requestDto = buildUpdateUserRequestDto(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, avatarPath);

        UpdateUserResponseDto responseDto = updateUser(token, requestDto);
        UserResponseDto userResponseDto = responseDto.getUser();
        assertAvatarChanged(userResponseDto, initialUserResponseDto.getAvatar());
    }

    private UpdateUserRequestDto buildUpdateUserRequestDto(String firstName, String lastName, String avatar) {
        UpdateUserRequestDto dto = new UpdateUserRequestDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(userConfig.email());
        dto.setAvatar(avatar);
        return dto;
    }
}
