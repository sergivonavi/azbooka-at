package ru.azbooka.at.ui.pages.modals;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginModal {
    public static final String ERROR_INVALID_CREDENTIALS = "Неверный адрес электронной почты или пароль.";
    public static final String ERROR_INPUT_REQUIRED = "Обязательно к заполнению";

    private final SelenideElement form = $x("//p[text()='Авторизация']/following-sibling::form");
    private final SelenideElement emailInput = form.$x(".//input[@name='email']");
    private final SelenideElement emailInputError = emailInput.$x("./following-sibling::div");
    private final SelenideElement passwordInput = form.$x(".//input[@name='password']");
    private final SelenideElement passwordInputError = passwordInput.$x("./following-sibling::div");
    private final SelenideElement submitButton = form.$x(".//button[@type='submit']");

    @Step("Вводим логин [{email}] в поле \"E-mail\"")
    public LoginModal setEmail(String email) {
        emailInput
                .shouldBe(visible)
                .setValue(email);
        return this;
    }

    @Step("Вводим пароль [{password}] в поле \"Пароль\"")
    public LoginModal setPassword(String password) {
        passwordInput
                .shouldBe(visible)
                .setValue(password);
        return this;
    }

    @Step("Нажимаем кнопку \"Войти\"")
    public void clickSubmit() {
        submitButton
                .shouldBe(visible)
                .shouldHave(text("Войти"))
                .click();
    }

    @Step("Проверяем, что в модалке отображается ошибка \"{message}\"")
    public void verifyModalErrorMessage(String message) {
        form
                .shouldBe(visible)
                .shouldHave(text(message));
    }

    @Step("Проверяем, что под полем \"E-mail\" отображается ошибка \"{message}\"")
    public LoginModal verifyEmailInputErrorMessage(String message) {
        emailInputError
                .shouldBe(visible)
                .shouldHave(text(message));
        return this;
    }

    @Step("Проверяем, что под полем \"Пароль\" отображается ошибка \"{message}\"")
    public LoginModal verifyPasswordInputErrorMessage(String message) {
        passwordInputError
                .shouldBe(visible)
                .shouldHave(text(message));
        return this;
    }
}
