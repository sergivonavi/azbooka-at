package ru.azbooka.at.tests.ui.favorites;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.FavoritesResponseDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.BookPage;
import ru.azbooka.at.ui.pages.FavoritesPage;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.BOOKS_TOTAL;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCode;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodeNotIn;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("FavoritesService")
@Tag("ui")
@Epic("Аккаунт")
@Feature("Закладки")
@Story("Добавление в закладки")
@DisplayName("UI: Добавление книг в закладки")
public class AddFavoritesUITests extends BaseTest {
    private final BookPage bookPage = new BookPage();
    private final FavoritesPage favoritesPage = new FavoritesPage();

    private String token;

    @BeforeEach
    void setup() {
        LoginResponseDto loginResponseDto = LoginApi.loginWithConfiguredUser();
        token = loginResponseDto.getAccessToken();

        deleteAllFavorites(token);
    }

    @AfterEach
    void cleanup() {
        deleteAllFavorites(token);
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Добавление книги в закладки, когда список закладок пустой, с переходом на страницу \"Закладки\"")
    @Severity(SeverityLevel.CRITICAL)
    void addBookToFavoritesWhenListEmptyTest() {
        String code = getRandomBookCode();

        bookPage
                .openPage(code)
                .clickAddFavoriteButton();

        bookPage
                .verifyFavoritesCounterValue(1)
                .shouldHaveDeleteFavoriteButton()
                .shouldNotHaveAddFavoriteButton();

        favoritesPage
                .openPage()
                .shouldHaveItemWithCode(code)
                .verifyFavoritesCounterValue(1);
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Добавление книги в закладки, когда список закладок не пустой")
    @Severity(SeverityLevel.CRITICAL)
    void addBookToFavoritesWhenListNotEmptyTest() {
        List<String> list = getRandomBookCodes(1, BOOKS_TOTAL - 1);
        String codeToAdd = getRandomBookCodeNotIn(list);

        addListToFavorites(token, list);

        bookPage
                .openPage(codeToAdd)
                .verifyFavoritesCounterValue(list.size())
                .clickAddFavoriteButton();

        bookPage
                .shouldHaveDeleteFavoriteButton()
                .shouldNotHaveAddFavoriteButton()
                .verifyFavoritesCounterValue(list.size() + 1);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContain(responseDto, list);
        assertFavoritesContain(responseDto, codeToAdd);
    }
}
