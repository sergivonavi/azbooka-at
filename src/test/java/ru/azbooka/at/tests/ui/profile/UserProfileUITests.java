package ru.azbooka.at.tests.ui.profile;

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
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.UpdateUserRequestDto;
import ru.azbooka.at.config.TestUserConfig;
import ru.azbooka.at.data.UserTestData;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.ProfilePage;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import static ru.azbooka.at.api.endpoints.auth.UserApi.updateUser;
import static ru.azbooka.at.data.UserTestData.DEFAULT_FIRST_NAME;
import static ru.azbooka.at.data.UserTestData.DEFAULT_LAST_NAME;
import static ru.azbooka.at.data.UserTestData.getRandomAvatar;
import static ru.azbooka.at.data.UserTestData.getRandomFirstName;
import static ru.azbooka.at.data.UserTestData.getRandomLastName;
import static ru.azbooka.at.ui.steps.CommonSteps.refreshPage;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("UserService")
@Tag("ui")
@Epic("Пользователи")
@Feature("Профиль пользователя")
@Story("Просмотр и редактирование информации о пользователе через UI")
@DisplayName("UI: Информация о пользователе")
public class UserProfileUITests extends BaseTest {
    private final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);
    private final ProfilePage profilePage = new ProfilePage();

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
        UpdateUserRequestDto requestDto = new UpdateUserRequestDto();
        requestDto.setFirstName(DEFAULT_FIRST_NAME);
        requestDto.setLastName(DEFAULT_LAST_NAME);
        requestDto.setEmail(userConfig.email());
        requestDto.setAvatar(UserTestData.DEFAULT_AVATAR);

        updateUser(token, requestDto);
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Отображение информации в профиле с загруженным аватаром")
    @Severity(SeverityLevel.NORMAL)
    void checkProfileInfoTest() {
        profilePage
                .openPage()
                .shouldHaveFirstName(DEFAULT_FIRST_NAME)
                .shouldHaveLastName(DEFAULT_LAST_NAME)
                .shouldHaveEmail(userConfig.email())
                .shouldHaveEmptyPassword()
                .shouldHaveEmptyPassword2()
                .shouldHaveAvatar();
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Изменение информации в профиле с загрузкой аватара")
    @Severity(SeverityLevel.NORMAL)
    void updateProfileInfoTest() {
        String newFirstName = getRandomFirstName();
        String newLastName = getRandomLastName();
        String newAvatar = getRandomAvatar();

        profilePage
                .openPage()
                .setFirstName(newFirstName)
                .setLastName(newLastName)
                .uploadAvatar(newAvatar)
                .clickSubmitButton();

        refreshPage();

        profilePage
                .shouldHaveFirstName(newFirstName)
                .shouldHaveLastName(newLastName)
                .shouldHaveEmail(userConfig.email())
                .shouldHaveEmptyPassword()
                .shouldHaveEmptyPassword2()
                .shouldHaveAvatar();
    }
}
