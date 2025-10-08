package ru.azbooka.at.utils;

import com.codeborne.selenide.Selenide;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BrowserUtils {

    public static void open(String relativeUrl) {
        Selenide.open(relativeUrl);
        setTrailersShowed();
    }

    /**
     * Заполняет localStorage ключ "trailers_showed" значениями от 1000 до 9999,
     * чтобы сайт считал, что все видео-трейлеры уже были показаны.
     * Предотвращает появление баннера с видео-трейлерами во время тестов.
     */
    private static void setTrailersShowed() {
        String trailers = IntStream.rangeClosed(1000, 9999)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));

        Selenide.localStorage().setItem("trailers_showed", trailers);
    }
}
