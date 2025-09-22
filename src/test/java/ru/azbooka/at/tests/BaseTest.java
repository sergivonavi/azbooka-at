package ru.azbooka.at.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchSessionException;
import ru.azbooka.at.config.BrowserConfig;
import ru.azbooka.at.utils.report.AllureAttachments;

import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class BaseTest {
    private static final BrowserConfig browserConfig = ConfigFactory.create(BrowserConfig.class);

    @BeforeAll
    static void setUp() {
        Configuration.browser = browserConfig.browserName();
        Configuration.browserVersion = browserConfig.browserVersion();
        Configuration.browserSize = browserConfig.browserSize();

        Configuration.baseUrl = "https://azbooka.ru";
        RestAssured.baseURI = "https://api.azbooka.ru";
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void afterEach() {
        if (hasWebDriverStarted()) {
            try {
                attachArtifacts();
            } finally {
                Selenide.closeWebDriver();
            }
        }
    }

    private void attachArtifacts() {
        try {
            AllureAttachments.attachScreenshot("Final state");
            AllureAttachments.attachVideo();
            AllureAttachments.attachPageSource();
            AllureAttachments.attachBrowserConsoleLogs();
        } catch (NoSuchSessionException e) {
            System.out.println("WebDriver session already closed, skipping artifacts");
        }
    }
}
