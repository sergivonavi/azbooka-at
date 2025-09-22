package ru.azbooka.at.tests.ui.favorites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.FavoritesResponseDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.data.BooksTestData;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.BookPage;
import ru.azbooka.at.ui.pages.FavoritesPage;

import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCode;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodeNotIn;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@DisplayName("Добавление книг в закладки")
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

    @Test
    @WithLogin
    @DisplayName("Добавление книги в закладки, когда список закладок пустой, с переходом на страницу \"Закладки\"")
    void addBookToFavoritesWhenListEmptyTest() {
        String code = getRandomBookCode();

        bookPage
                .openPage(code)
                .clickAddFavoriteButton();

        bookPage
                .shouldHaveDeleteFavoriteButton()
                .shouldNotHaveAddFavoriteButton()
                .verifyFavoritesCounterValue(1);

        favoritesPage
                .openPage()
                .shouldHaveItemWithCode(code)
                .verifyFavoritesCounterValue(1);
    }

    @Test
    @WithLogin
    @DisplayName("Добавление книги в закладки, когда список закладок не пустой")
    void addBookToFavoritesWhenListNotEmptyTest() {
        List<String> list = getRandomBookCodes(1, BooksTestData.BOOKS_TOTAL - 1);
        String codeToAdd = getRandomBookCodeNotIn(list);

        addListToFavorites(token, list);

        bookPage
                .openPage(codeToAdd)
                .verifyFavoritesCounterValue(list.size());
        bookPage.clickAddFavoriteButton();

        bookPage
                .shouldHaveDeleteFavoriteButton()
                .shouldNotHaveAddFavoriteButton()
                .verifyFavoritesCounterValue(list.size() + 1);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContain(responseDto, list);
        assertFavoritesContain(responseDto, codeToAdd);
    }
}
