package ru.azbooka.at.tests.api.favorites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.FavoritesResponseDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.tests.BaseTest;

import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContainOnly;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.addFavorite;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCode;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodeNotIn;
import static ru.azbooka.at.data.BooksTestData.getRandomBookCodes;

@DisplayName("Добавление книг в закладки")
public class AddFavoritesApiTests extends BaseTest {
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
    @DisplayName("Добавление книги в закладки через API, когда список закладок пустой")
    void addBookToFavoritesWhenListEmptyTest() {
        String code = getRandomBookCode();
        addFavorite(token, code);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContainOnly(responseDto, code);
    }

    @Test
    @DisplayName("Добавление книги в закладки через API, когда список закладок не пустой")
    void addBookToFavoritesWhenListNotEmptyTest() {
        List<String> list = getRandomBookCodes(2, 4);
        String codeToAdd = getRandomBookCodeNotIn(list);

        addListToFavorites(token, list);
        addFavorite(token, codeToAdd);

        FavoritesResponseDto responseDto = getFavorites(token);
        assertFavoritesContain(responseDto, list);
        assertFavoritesContain(responseDto, codeToAdd);
    }
}
