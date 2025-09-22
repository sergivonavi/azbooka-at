package ru.azbooka.at.tests.ui.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Manual;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import static io.qameta.allure.Allure.step;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("SearchService")
@Tag("ui")
@Epic("Поиск")
@Feature("Дропдаун с результатами поиска")
@Story("Поведение дропдауна при поисковых запросах")
@DisplayName("UI: Поведение дропдауна при поисковых запросах")
public class SearchDropdownTests {

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @Manual
    @DisplayName("Дропдаун поиска отображает книги в блоке \"Книги\"")
    @Severity(SeverityLevel.CRITICAL)
    void searchResultsDisplaysBooksInDropdownTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим в поле поиска запрос, который вернет книги");
        step("Проверяем выпадающий список", () -> {
            step("В блоке \"Книги\" отображается книга с искомым названием");
            step("Внизу блока \"Книги\" отображается кнопка \"Показать еще\", если результатов больше двух");
        });
    }

    @Tags({@Tag("regress")})
    @Test
    @Manual
    @DisplayName("Дропдаун поиска отображает статьи в блоке \"Статьи\"")
    @Severity(SeverityLevel.CRITICAL)
    void searchResultsDisplaysArticlesInDropdownTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим в поле поиска запрос, который вернет статьи");
        step("Проверяем выпадающий список", () -> {
            step("В блоке \"Статьи\" отображается статья с искомым названием");
            step("Внизу блока \"Статьи\" отображается кнопка \"Показать еще\", если результатов больше двух");
        });
    }

    @Tags({@Tag("regress")})
    @Test
    @Manual
    @DisplayName("Дропдаун поиска показывает сообщение, если нет результатов")
    @Severity(SeverityLevel.NORMAL)
    void searchShowsNoResultsMessageTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим запрос в поле поиска, который не вернет результатов");
        step("Проверяем выпадающий список", () -> {
            step("Отображается заголовок \"Ничего не найдено\"");
            step("Отображается текст \"Попробуйте изменить запрос\"");
        });
    }

    @Tags({@Tag("regress")})
    @Test
    @Manual
    @DisplayName("Переход на страницу поиска по нажатию на клавишу Enter")
    @Severity(SeverityLevel.NORMAL)
    void searchResultsOpensPageOnEnterTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим запрос в поле поиска и нажимаем Enter");
        step("Проверяем страницу поиска со всеми результатами");
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @Manual
    @DisplayName("Переход на страницу поиска по кнопке \"Показать еще\"")
    @Severity(SeverityLevel.CRITICAL)
    void searchShowMoreOpensFullResultsPageTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим в поле поиска запрос, который вернет больше двух результатов в блоке \"Книги\"");
        step("Нажимаем кнопку \"Показать еще\" в блоке \"Книги\"");
        step("Проверяем страницу поиска со всеми результатами");
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @Manual
    @DisplayName("Нажатие на книгу в дропдауне открывает страницу книги")
    @Severity(SeverityLevel.CRITICAL)
    void clickBookInDropdownOpensPageTest() {
        step("Открываем главную страницу");
        step("Нажимаем кнопку поиска в шапке");
        step("Вводим в поле поиска название существующей книги");
        step("Нажимаем на книгу в дропдауне");
        step("Проверяем переход на страницу книги");
    }
}
