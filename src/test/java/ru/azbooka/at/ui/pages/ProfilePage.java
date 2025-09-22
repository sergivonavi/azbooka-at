package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static ru.azbooka.at.utils.BrowserUtils.open;

public class ProfilePage extends BasePage {
    private final SelenideElement pageHeader = $x("//h2");

    @Step("Открываем страницу профиля")
    public ProfilePage openPage() {
        open("/user/");
        return this;
    }

    @Step("Проверяем, что на странице отображается текст \"Залогиньтесь\"")
    public ProfilePage verifyUnauthorizedHeader() {
        pageHeader
                .shouldBe(visible)
                .shouldHave(text("Залогиньтесь"));
        return this;
    }
}
