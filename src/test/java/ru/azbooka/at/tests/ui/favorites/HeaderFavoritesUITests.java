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
import ru.azbooka.at.extensions.WithLogin;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.ui.pages.FavoritesPage;
import ru.azbooka.at.ui.pages.MainPage;
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
@Story("Ссылка на закладки в шапке")
@DisplayName("UI: Ссылка на закладки в шапке")
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

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Переход к закладкам через ссылку в шапке")
    @Severity(SeverityLevel.NORMAL)
    void linkNavigatesToFavoritesPageTest() {
        mainPage
                .openPage()
                .shouldHaveFavoritesLink()
                .clickFavoritesLink();

        new FavoritesPage().verifyEmptyListHeader();
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Счетчик закладок не отображается, когда нет закладок")
    @Severity(SeverityLevel.MINOR)
    void favoritesCounterIsNotDisplayedWhenNoFavoritesTest() {
        mainPage
                .openPage()
                .verifyFavoritesCounterNotDisplayed();
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Счетчик закладок отображается, когда есть закладки")
    @Severity(SeverityLevel.MINOR)
    void favoritesCounterShowsCorrectNumberTest() {
        List<String> codes = getRandomBookCodes();
        addListToFavorites(token, codes);

        mainPage
                .openPage()
                .verifyFavoritesCounterValue(codes.size());
    }
}
