package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    private final SelenideElement loginButton = $x("//span[contains(@class, 'fa-user') and not(ancestor::nav)]/ancestor::button");
    private final SelenideElement signOutButton = $x("//header//span[contains(@class,'fa-sign-out') and not(ancestor::nav)]");
    private final SelenideElement favoritesLink = $x("//a[@href='/favorites/' and not(ancestor::nav)]");
    private final SelenideElement favoritesCounter = favoritesLink.$x("./span[3]");
    private final SelenideElement profileLink = $x("//a[@href='/user/']");

    @Step("Нажимаем кнопку \"Вход\"")
    public void clickLoginButton() {
        loginButton
                .shouldBe(visible)
                .shouldHave(text("Вход"))
                .click();
    }

    @Step("Нажимаем кнопку выхода")
    public void clickSignOutButton() {
        signOutButton
                .should(exist)
                .click();
    }

    @Step("Проверяем, что кнопка \"Вход\" отображается")
    public BasePage shouldHaveLoginButton() {
        loginButton
                .shouldBe(visible)
                .shouldHave(text("Вход"));
        return this;
    }

    @Step("Проверяем, что ссылка на страницу \"Закладки\" отображается")
    public BasePage shouldHaveFavoritesLink() {
        favoritesLink.shouldBe(visible, enabled);
        return this;
    }

    @Step("Нажимаем кнопку \"Закладки\"")
    public void clickFavoritesLink() {
        favoritesLink
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Проверяем, что значения счетчика закладок равно {count}")
    public BasePage verifyFavoritesCounterValue(int count) {
        favoritesCounter
                .shouldBe(visible)
                .shouldHave(text(String.valueOf(count)));
        return this;
    }

    @Step("Проверяем, что счетчик закладок не отображается")
    public BasePage verifyFavoritesCounterNotDisplayed() {
        favoritesCounter.shouldNot(exist);
        return this;
    }

    @Step("Проверяем, что ссылка на страницу профиля не отображается")
    public BasePage shouldNotHaveProfileLink() {
        profileLink.shouldNot(exist);
        return this;
    }

    @Step("Проверяем, что ссылка на страницу профиля отображается")
    public BasePage shouldHaveProfileLink() {
        profileLink.shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что кнопка выхода не отображается")
    public BasePage shouldNotHaveSignOutButton() {
        signOutButton.shouldNot(exist);
        return this;
    }

    @Step("Проверяем, что кнопка выхода отображается")
    public BasePage shouldHaveSignOutButton() {
        signOutButton.should(exist);
        return this;
    }
}
