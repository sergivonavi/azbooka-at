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
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.azbooka.at.config.BrowserConfig;
import ru.azbooka.at.config.RemoteConfig;
import ru.azbooka.at.utils.RemoteUtils;
import ru.azbooka.at.utils.allure.report.AllureAttachments;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class BaseTest {
    private static final BrowserConfig browserConfig = ConfigFactory.create(BrowserConfig.class);
    private static final RemoteConfig remoteConfig = ConfigFactory.create(RemoteConfig.class);

    @BeforeAll
    static void setUp() {
        Configuration.browser = browserConfig.browserName();
        Configuration.browserVersion = browserConfig.browserVersion();
        Configuration.browserSize = browserConfig.browserSize();

        if (remoteConfig.isRemote()) {
            Configuration.remote = RemoteUtils.remoteUrl(
                    remoteConfig.username(),
                    remoteConfig.password(),
                    remoteConfig.domain()
            );

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(
                    "selenoid:options",
                    Map.of(
                            "enableVNC", true,
                            "enableVideo", true
                    )
            );
            Configuration.browserCapabilities = capabilities;
        }

        Configuration.baseUrl = "https://azbooka.ru";
        RestAssured.baseURI = "https://api.azbooka.ru";
        RestAssured.basePath = "/api/v3";
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void attachArtifacts() {
        try {
            AllureAttachments.attachScreenshot("Final state");
            if (remoteConfig.isRemote()) {
                String videoUrl = RemoteUtils.videoUrl(remoteConfig.domain(), Selenide.sessionId());
                AllureAttachments.attachVideo(videoUrl);
            }
            AllureAttachments.attachPageSource();
            AllureAttachments.attachBrowserConsoleLogs();
        } catch (NoSuchSessionException e) {
            System.out.println("WebDriver session already closed, skipping artifacts");
        }
    }
}
