package ru.azbooka.at.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.azbooka.at.data.Book;

import java.util.List;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;
import static ru.azbooka.at.utils.BrowserUtils.open;

public class FavoritesPage extends BasePage<FavoritesPage> {
    private final SelenideElement pageHeader = $x("//h3");
    private final SelenideElement booksList = $x("//h2[text()='Книги']/following-sibling::ul");

    @Step("Открываем страницу \"Закладки\"")
    public FavoritesPage openPage() {
        open("/favorites/");

        $x("//h1")
                .shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
        return this;
    }

    private ElementsCollection getItemsList() {
        return booksList
                .should(exist)
                .$$x("./li")
                .shouldHave(sizeGreaterThan(0));
    }

    private SelenideElement getItemByCode(String code) {
        return getItemsList().stream()
                .filter(li -> li.$("a").getAttribute("href").endsWith(code))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Book with code not found: " + code));
    }

    private SelenideElement getDeleteButtonByItemCode(String code) {
        return getItemByCode(code).$x(".//button");
    }

    private SelenideElement getDeleteButtonByItem(SelenideElement item) {
        return item.$x(".//button");
    }

    @Step("Нажимаем кнопку удаления у книги с code \"{code}\"")
    public FavoritesPage clickDeleteButtonByItemCode(String code) {
        getDeleteButtonByItemCode(code)
                .shouldBe(visible, enabled)
                .click();
        return this;
    }

    @Step("Проверяем, что в списке отсутствует книга с code \"{code}\"")
    public FavoritesPage shouldNotHaveItemWithCode(String code) {
        getItemsList()
                .filterBy(attributeMatching("href", ".*" + code + "$"))
                .shouldHave(size(0));
        return this;
    }

    @Step("Проверяем, что в списке отображается книга с code \"{code}\"")
    public FavoritesPage shouldHaveItemWithCode(String code) {
        getItemByCode(code).shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что в списке отображаются книги с code \"{list}\"")
    public FavoritesPage shouldHaveItemsWithCodes(List<String> list) {
        for (String code : list) {
            shouldHaveItemWithCode(code);
        }
        return this;
    }

    @Step("Проверяем, что у книги отображается кнопка удаления")
    private void itemShouldHaveDeleteButton(SelenideElement item) {
        getDeleteButtonByItem(item).shouldBe(visible, enabled);
    }

    @Step("Проверяем, что у книги отображается название \"{title}\"")
    private void itemShouldHaveTitle(SelenideElement item, String title) {
        item.$x(".//a[@itemprop='name']")
                .shouldBe(visible)
                .shouldHave(text(title));
    }

    @Step("Проверяем, что у книги отображается кнопка \"Подробнее\"")
    private void itemShouldHaveMoreButton(SelenideElement item) {
        item.$x(".//a[@itemprop='url']")
                .shouldBe(visible)
                .shouldHave(text("Подробнее"));
    }

    @Step("Проверяем карточки книг и их элементы")
    public void verifyCardsContents(List<Book> books) {
        for (Book book : books) {
            String code = book.getCode();

            step("Проверяем книгу с code \"" + code + "\"", () -> {
                shouldHaveItemWithCode(code);

                SelenideElement item = getItemByCode(code);
                itemShouldHaveDeleteButton(item);
                itemShouldHaveTitle(item, book.getTitle());
                itemShouldHaveMoreButton(item);
            });
        }
    }

    @Step("Проверяем, что на странице отображается текст \"Закладок пока нет\"")
    public FavoritesPage verifyEmptyListHeader() {
        pageHeader
                .shouldBe(visible)
                .shouldHave(text("Закладок пока нет"));
        return this;
    }
}
