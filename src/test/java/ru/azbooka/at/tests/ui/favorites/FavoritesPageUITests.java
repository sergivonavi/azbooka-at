package ru.azbooka.at.tests.ui.favorites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.data.Book;
import ru.azbooka.at.data.BooksTestData;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.FavoritesPage;

import java.util.List;

import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@DisplayName("Страница закладок")
public class FavoritesPageUITests extends BaseTest {
    private final FavoritesPage favoritesPage = new FavoritesPage();

    private String token;
    private List<Book> books;

    @BeforeEach
    void setup() {
        LoginResponseDto loginResponseDto = LoginApi.loginWithConfiguredUser();
        token = loginResponseDto.getAccessToken();

        deleteAllFavorites(token);

        List<String> codes = getRandomBookCodes();
        addListToFavorites(token, codes);

        books = BooksTestData.getBooksByCodes(codes);
    }

    @AfterEach
    void cleanup() {
        deleteAllFavorites(token);
    }

    @Test
    @WithLogin
    @DisplayName("Отображение книг в списке закладок")
    void favoritesListWithSeveralBooksTest() {
        favoritesPage
                .openPage()
                .verifyCardsContents(books);
    }

    @Test
    @WithLogin
    @DisplayName("Отображение счетчика закладок")
    void favoritesCounterTest() {
        favoritesPage
                .openPage()
                .verifyFavoritesCounterValue(books.size());
    }
}
