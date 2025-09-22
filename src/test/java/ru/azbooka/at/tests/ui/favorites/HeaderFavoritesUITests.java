package ru.azbooka.at.tests.ui.favorites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.FavoritesPage;
import ru.azbooka.at.ui.pages.MainPage;

import java.util.List;

import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@DisplayName("Ссылка на закладки в шапке")
public class HeaderFavoritesUITests extends BaseTest {
    private final MainPage mainPage = new MainPage();

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
    @DisplayName("Переход к закладкам через ссылку в шапке")
    void linkNavigatesToFavoritesPageTest() {
        mainPage
                .openPage()
                .shouldHaveFavoritesLink()
                .clickFavoritesLink();

        new FavoritesPage().verifyEmptyListHeader();
    }

    @Test
    @WithLogin
    @DisplayName("Счетчик закладок не отображается, когда нет закладок")
    void favoritesCounterIsNotDisplayedWhenNoFavoritesTest() {
        mainPage
                .openPage()
                .verifyFavoritesCounterNotDisplayed();
    }

    @Test
    @WithLogin
    @DisplayName("Счетчик закладок отображается, когда есть закладки")
    void favoritesCounterShowsCorrectNumberTest() {
        List<String> codes = getRandomBookCodes();
        addListToFavorites(token, codes);

        mainPage
                .openPage()
                .verifyFavoritesCounterValue(codes.size());
    }
}
