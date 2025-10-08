package ru.azbooka.at.api.helpers;

import io.qameta.allure.Step;
import ru.azbooka.at.api.endpoints.favorites.FavoritesApi;
import ru.azbooka.at.api.models.FavoritesResponseDto;

import java.util.List;

public class FavoritesApiHelper {

    @Step("Удаляем все книги из закладок пользователя")
    public static void deleteAllFavorites(String token) {
        FavoritesResponseDto responseDto = FavoritesApi.getFavorites(token);
        responseDto.getCodes().forEach(code -> FavoritesApi.deleteFavorite(token, code));
    }

    @Step("Добавляем в закладки несколько книг: {codes}")
    public static void addListToFavorites(String token, List<String> codes) {
        codes.forEach(code -> FavoritesApi.addFavorite(token, code));
    }
}
