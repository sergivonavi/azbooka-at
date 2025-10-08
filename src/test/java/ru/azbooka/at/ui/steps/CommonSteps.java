package ru.azbooka.at.ui.steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.refresh;

public class CommonSteps {

    @Step("Обновляем страницу")
    public static void refreshPage() {
        refresh();
    }
}
