package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static ru.azbooka.at.utils.BrowserUtils.open;

public class ProfilePage extends BasePage<ProfilePage> {
    private final SelenideElement pageHeader = $x("//h2");
    private final SelenideElement firstNameInput = $x("//input[@name='first_name']");
    private final SelenideElement lastNameInput = $x("//input[@name='last_name']");
    private final SelenideElement emailInput = $x("//input[@name='email']");
    private final SelenideElement passwordInput = $x("//input[@name='password']");
    private final SelenideElement password2Input = $x("//input[@name='password2']");
    private final SelenideElement avatarInput = $x("//input[@name='avatar']");
    private final SelenideElement avatarImage = $x("//h4[text()='Аватар']//following-sibling::div/img");
    private final SelenideElement submitButton = $x("//button[@type='submit']");

    @Step("Открываем страницу профиля")
    public ProfilePage openPage() {
        open("/user/");
        return this;
    }

    @Step("Вводим [{firstName}] в поле \"Имя\" ")
    public ProfilePage setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    @Step("Вводим [{lastName}] в поле \"Фамилия\" ")
    public ProfilePage setLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    @Step("Загружаем картинку [{filepath}] в поле \"Аватар\"")
    public ProfilePage uploadAvatar(String filepath) {
        avatarInput.uploadFromClasspath(filepath);
        return this;
    }

    @Step("Проверяем, что в поле \"Имя\" значение равно \"{firstName}\"")
    public ProfilePage shouldHaveFirstName(String firstName) {
        firstNameInput
                .shouldBe(visible)
                .shouldHave(value(firstName));
        return this;
    }

    @Step("Проверяем, что в поле \"Фамилия\" значение равно \"{lastName}\"")
    public ProfilePage shouldHaveLastName(String lastName) {
        lastNameInput
                .shouldBe(visible)
                .shouldHave(value(lastName));
        return this;
    }

    @Step("Проверяем, что в поле \"Email\" значение равно \"{email}\"")
    public ProfilePage shouldHaveEmail(String email) {
        emailInput
                .shouldBe(visible)
                .shouldHave(value(email));
        return this;
    }

    @Step("Проверяем, что в поле \"Пароль\" значение не задано")
    public ProfilePage shouldHaveEmptyPassword() {
        passwordInput.shouldBe(visible, empty);
        return this;
    }

    @Step("Проверяем, что в поле \"Повторите пароль\" значение не задано")
    public ProfilePage shouldHaveEmptyPassword2() {
        password2Input.shouldBe(visible, empty);
        return this;
    }

    @Step("Проверяем, что аватар отображается")
    public ProfilePage shouldHaveAvatar() {
        avatarImage.should(exist, visible);
        return this;
    }

    @Step("Нажимаем кнопку \"Сохранить\"")
    public void clickSubmitButton() {
        submitButton
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Проверяем, что на странице отображается текст \"Залогиньтесь\"")
    public ProfilePage verifyUnauthorizedHeader() {
        pageHeader
                .shouldBe(visible)
                .shouldHave(text("Залогиньтесь"));
        return this;
    }
}
