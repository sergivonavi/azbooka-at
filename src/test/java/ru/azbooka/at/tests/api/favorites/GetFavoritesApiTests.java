package ru.azbooka.at.tests.api.favorites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.azbooka.at.api.endpoints.auth.LoginApi;
import ru.azbooka.at.api.models.FavoritesResponseDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.data.BooksTestData;
import ru.azbooka.at.tests.BaseTest;

import java.util.List;

import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesContain;
import static ru.azbooka.at.api.assertions.FavoritesAssertions.assertFavoritesEmpty;
import static ru.azbooka.at.api.endpoints.favorites.FavoritesApi.getFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.addListToFavorites;
import static ru.azbooka.at.api.helpers.FavoritesApiHelper.deleteAllFavorites;

@DisplayName("Получение закладок")
public class GetFavoritesApiTests extends BaseTest {
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
    @DisplayName("Получение пустого списка закладок")
    void getFavoritesWhenEmptyTest() {
        FavoritesResponseDto responseDto = getFavorites(token);

        assertFavoritesEmpty(responseDto);
    }

    @Test
    @DisplayName("Получение не пустого списка закладок")
    void getFavoritesWhenNotEmptyTest() {
        List<String> list = BooksTestData.getRandomBookCodes(2, 4);
        addListToFavorites(token, list);

        FavoritesResponseDto responseDto = getFavorites(token);

        assertFavoritesContain(responseDto, list);
    }
}
