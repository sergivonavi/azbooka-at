package ru.azbooka.at.api.assertions;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.FavoritesResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FavoritesAssertions {

    @Step("Проверяем, что список закладок пустой")
    public static void assertFavoritesEmpty(FavoritesResponseDto responseDto) {
        assertThat(responseDto.getCodes()).isEmpty();
    }

    @Step("Проверяем, что в списке закладок есть только книга с кодом: {code}")
    public static void assertFavoritesContainOnly(FavoritesResponseDto responseDto, String code) {
        assertThat(responseDto.getCodes()).containsExactly(code);
    }

    @Step("Проверяем, что список закладок содержит код: [{code}]")
    public static void assertFavoritesContain(FavoritesResponseDto responseDto, String code) {
        assertThat(responseDto.getCodes()).contains(code);
    }

    @Step("Проверяем, что список закладок содержит коды: {list}")
    public static void assertFavoritesContain(FavoritesResponseDto responseDto, List<String> list) {
        assertThat(responseDto.getCodes()).containsAll(list);
    }

    @Step("Проверяем, что список закладок не содержит код: [{code}]")
    public static void assertFavoritesNotContain(FavoritesResponseDto responseDto, String code) {
        assertThat(responseDto.getCodes()).doesNotContain(code);
    }
}
