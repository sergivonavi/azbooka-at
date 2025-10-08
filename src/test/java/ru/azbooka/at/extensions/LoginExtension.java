package ru.azbooka.at.extensions;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.LoginResponseDto;

import static ru.azbooka.at.utils.BrowserUtils.open;

public class LoginExtension implements BeforeEachCallback {

    @Step("Авторизуемся пользователем через API и устанавливаем токен в localStorage браузера")
    @Override
    public void beforeEach(ExtensionContext context) {
        open("/images/article/detail/back-arr.png");

        setAuthToken();
    }

    private void setAuthToken() {
        LoginResponseDto loginResponseDto = LoginApi.loginWithConfiguredUser();
        String token = loginResponseDto.getAccessToken();
        Selenide.localStorage().setItem("token", token);
    }
}
