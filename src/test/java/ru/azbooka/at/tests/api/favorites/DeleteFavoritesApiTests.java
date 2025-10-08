package ru.azbooka.at.tests.api.favorites;

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
import ru.azbooka.at.data.BooksTestData;
import ru.azbooka.at.tests.BaseTest;
import ru.azbooka.at.utils.allure.annotations.Layer;
import ru.azbooka.at.utils.allure.annotations.Microservice;

import java.util.ArrayList;
import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesEmpty;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesNotContain;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.addFavorite;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.deleteFavorite;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;

@Owner("sergivonavi")
@Layer("api")
@Microservice("FavoritesService")
@Tag("api")
@Epic("Аккаунт")
@Feature("Закладки")
@Story("Удаление из закладок")
@DisplayName("API: Удаление книг из закладок")
public class DeleteFavoritesApiTests extends BaseTest {
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
    @DisplayName("Удаление книги из закладок через API, когда в списке закладок только одна книга")
    @Severity(SeverityLevel.CRITICAL)
    void deleteBookFromFavoritesWhenListContainsOneBookTest() {
        String code = BooksTestData.getRandomBookCode();

        addFavorite(token, code);
        deleteFavorite(token, code);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesEmpty(responseDto);
    }

    @Tag("regress")
    @Test
    @DisplayName("Удаление книги из закладок через API, когда в списке закладок несколько книг")
    @Severity(SeverityLevel.CRITICAL)
    void deleteBookFromFavoritesWhenListContainsSeveralBooksTest() {
        List<String> list = BooksTestData.getRandomBookCodes(2, 4);
        String codeToDelete = BooksTestData.getRandomBookCodeIn(list);

        addListToFavorites(token, list);
        deleteFavorite(token, codeToDelete);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesNotContain(responseDto, codeToDelete);

        List<String> expectedList = new ArrayList<>(list);
        expectedList.remove(codeToDelete);
        assertFavoritesContain(responseDto, expectedList);
    }
}
