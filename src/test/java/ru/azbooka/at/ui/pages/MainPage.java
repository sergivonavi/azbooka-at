package ru.azbooka.at.ui.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class MainPage extends BasePage {

    @Step("Открываем главную страницу")
    public MainPage openPage() {
        open("/");
        return this;
    }
}
