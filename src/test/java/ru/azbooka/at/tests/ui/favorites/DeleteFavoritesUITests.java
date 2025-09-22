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

import java.util.ArrayList;
import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesEmpty;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesNotContain;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.addFavorite;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.BOOKS_TOTAL;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCode;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodeIn;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@Owner("sergivonavi")
@Layer("ui")
@Microservice("FavoritesService")
@Tag("ui")
@Epic("Аккаунт")
@Feature("Закладки")
@Story("Удаление из закладок")
@DisplayName("UI: Удаление книг из закладок")
public class DeleteFavoritesUITests extends BaseTest {
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
    @DisplayName("Удаление книги из закладок на странице книги, когда в списке закладок только одна книга, с переходом на страницу \"Закладки\"")
    @Severity(SeverityLevel.CRITICAL)
    void deleteFavoriteOnBookPageWhenListContainsOneBookTest() {
        String code = getRandomBookCode();
        addFavorite(token, code);

        bookPage
                .openPage(code)
                .clickDeleteFavoriteButton();

        bookPage
                .shouldHaveAddFavoriteButton()
                .shouldNotHaveDeleteFavoriteButton()
                .verifyFavoritesCounterNotDisplayed();

        favoritesPage
                .openPage()
                .verifyEmptyListHeader()
                .verifyFavoritesCounterNotDisplayed();
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Удаление книги из закладок на странице книги, когда в списке закладок несколько книг")
    @Severity(SeverityLevel.CRITICAL)
    void deleteFavoriteOnBookPageWhenListContainsSeveralBooksTest() {
        List<String> initialList = getRandomBookCodes(2, BOOKS_TOTAL);
        String codeToDelete = getRandomBookCodeIn(initialList);
        List<String> expectedList = new ArrayList<>(initialList);
        expectedList.remove(codeToDelete);

        addListToFavorites(token, initialList);

        bookPage
                .openPage(codeToDelete)
                .clickDeleteFavoriteButton();

        bookPage
                .shouldHaveAddFavoriteButton()
                .shouldNotHaveDeleteFavoriteButton()
                .verifyFavoritesCounterValue(expectedList.size());

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContain(responseDto, expectedList);
        assertFavoritesNotContain(responseDto, codeToDelete);
    }

    @Tags({@Tag("regress"), @Tag("smoke")})
    @Test
    @WithLogin
    @DisplayName("Удаление книги из закладок на странице \"Закладки\", когда в списке закладок только одна книга")
    @Severity(SeverityLevel.CRITICAL)
    void deleteFavoriteOnFavoritesPageWhenListContainsOneBookTest() {
        String code = getRandomBookCode();
        addFavorite(token, code);

        favoritesPage
                .openPage()
                .clickDeleteButtonByItemCode(code)
                .verifyEmptyListHeader()
                .verifyFavoritesCounterNotDisplayed();

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesEmpty(responseDto);
    }

    @Tag("regress")
    @Test
    @WithLogin
    @DisplayName("Удаление книги из закладок на странице \"Закладки\", когда в списке закладок несколько книг")
    @Severity(SeverityLevel.CRITICAL)
    void deleteFavoriteOnFavoritesPageWhenListContainsSeveralBooksTest() {
        List<String> initialList = getRandomBookCodes(2, BOOKS_TOTAL);
        String codeToDelete = getRandomBookCodeIn(initialList);
        List<String> expectedList = new ArrayList<>(initialList);
        expectedList.remove(codeToDelete);

        addListToFavorites(token, initialList);

        favoritesPage
                .openPage()
                .clickDeleteButtonByItemCode(codeToDelete)
                .shouldNotHaveItemWithCode(codeToDelete)
                .shouldHaveItemsWithCodes(expectedList)
                .verifyFavoritesCounterValue(expectedList.size());

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContain(responseDto, expectedList);
        assertFavoritesNotContain(responseDto, codeToDelete);
    }
}
