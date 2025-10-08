package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static ru.azbooka.at.utils.BrowserUtils.open;

public class BookPage extends BasePage<BookPage> {
    private final SelenideElement addFavoriteButton = $x("//button[text()='Добавить в закладки']");
    private final SelenideElement deleteFavoriteButton = $x("//button[text()='Удалить из закладок']");

    @Step("Открываем страницу книги с code [{code}]")
    public BookPage openPage(String code) {
        open("/books/" + code);
        return this;
    }

    @Step("Нажимаем кнопку \"Добавить в закладки\"")
    public void clickAddFavoriteButton() {
        addFavoriteButton
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Нажимаем кнопку \"Удалить из закладок\"")
    public void clickDeleteFavoriteButton() {
        deleteFavoriteButton
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Проверяем, что кнопка \"Добавить в закладки\" отображается")
    public BookPage shouldHaveAddFavoriteButton() {
        addFavoriteButton.shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что кнопка \"Добавить в закладки\" не отображается")
    public BookPage shouldNotHaveAddFavoriteButton() {
        addFavoriteButton.shouldNot(exist);
        return this;
    }

    @Step("Проверяем, что кнопка \"Удалить из закладок\" отображается")
    public BookPage shouldHaveDeleteFavoriteButton() {
        deleteFavoriteButton.shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что кнопка \"Удалить из закладок\" не отображается")
    public BookPage shouldNotHaveDeleteFavoriteButton() {
        deleteFavoriteButton.shouldNot(exist);
        return this;
    }
}
