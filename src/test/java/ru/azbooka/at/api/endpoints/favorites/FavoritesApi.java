package ru.azbooka.at.api.endpoints.favorites;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.FavoritesResponseDto;

import static ru.azbooka.at.api.specs.BaseSpecs.requestSpec;
import static ru.azbooka.at.api.specs.BaseSpecs.responseSpec;

public class FavoritesApi {

    @Step("Получаем список закладок")
    public static FavoritesResponseDto getFavorites(String token) {
        return requestSpec(token)
                .when()
                .get("/favorites")
                .then()
                .spec(responseSpec(200))
                .extract().as(FavoritesResponseDto.class);
    }

    @Step("Добавляем книгу с code [{code}] в закладки")
    public static void addFavorite(String token, String code) {
        requestSpec(token)
                .queryParam("book_slug", code)
                .when()
                .get("/favorites/add")
                .then()
                .spec(responseSpec(200));
    }

    @Step("Удаляем книгу с code [{code}] из закладок")
    public static void deleteFavorite(String token, String code) {
        requestSpec(token)
                .queryParam("book_slug", code)
                .when()
                .get("/favorites/delete")
                .then()
                .spec(responseSpec(200));
    }
}
