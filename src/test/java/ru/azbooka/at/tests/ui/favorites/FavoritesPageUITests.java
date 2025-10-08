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
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.data.Book;
import ru.azbooka.at.data.BooksTestData;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.FavoritesPage;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import java.util.List;

import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("FavoritesService")
@Tag("ui")
@Epic("Аккаунт")
@Feature("Закладки")
@Story("Просмотр закладок")
@DisplayName("UI: Страница закладок")
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

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Отображение книг в списке закладок")
    @Severity(SeverityLevel.NORMAL)
    void favoritesListWithSeveralBooksTest() {
        favoritesPage
                .openPage()
                .verifyCardsContents(books);
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Отображение счетчика закладок")
    @Severity(SeverityLevel.MINOR)
    void favoritesCounterTest() {
        favoritesPage
                .openPage()
                .verifyFavoritesCounterValue(books.size());
    }
}
