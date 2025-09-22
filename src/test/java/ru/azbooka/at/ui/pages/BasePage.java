package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

@SuppressWarnings("unchecked")
public class BasePage<T extends BasePage<T>> {
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
    public T shouldHaveLoginButton() {
        loginButton
                .shouldBe(visible)
                .shouldHave(text("Вход"));
        return (T) this;
    }

    @Step("Проверяем, что ссылка на страницу \"Закладки\" отображается")
    public T shouldHaveFavoritesLink() {
        favoritesLink.shouldBe(visible, enabled);
        return (T) this;
    }

    @Step("Нажимаем кнопку \"Закладки\"")
    public void clickFavoritesLink() {
        favoritesLink
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Проверяем, что значения счетчика закладок равно {count}")
    public T verifyFavoritesCounterValue(int count) {
        favoritesCounter
                .shouldBe(visible)
                .shouldHave(text(String.valueOf(count)));
        return (T) this;
    }

    @Step("Проверяем, что счетчик закладок не отображается")
    public T verifyFavoritesCounterNotDisplayed() {
        favoritesCounter.shouldNot(exist);
        return (T) this;
    }

    @Step("Проверяем, что ссылка на страницу профиля не отображается")
    public T shouldNotHaveProfileLink() {
        profileLink.shouldNot(exist);
        return (T) this;
    }

    @Step("Проверяем, что ссылка на страницу профиля отображается")
    public T shouldHaveProfileLink() {
        profileLink.shouldBe(visible);
        return (T) this;
    }

    @Step("Проверяем, что кнопка выхода не отображается")
    public T shouldNotHaveSignOutButton() {
        signOutButton.shouldNot(exist);
        return (T) this;
    }

    @Step("Проверяем, что кнопка выхода отображается")
    public T shouldHaveSignOutButton() {
        signOutButton.should(exist);
        return (T) this;
    }
}
