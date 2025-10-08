package ru.azbooka.at.utils.allure.report;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.nio.charset.StandardCharsets;

import static org.openqa.selenium.logging.LogType.BROWSER;

public class AllureAttachments {

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(String name) {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] attachPageSource() {
        return WebDriverRunner.getWebDriver()
                .getPageSource()
                .getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String message) {
        return message;
    }

    public static void attachBrowserConsoleLogs() {
        // Workaround for Firefox
        // Otherwise fails with 'UnsupportedCommandException: HTTP method not allowed'
        // https://github.com/selenide/selenide/issues/2266
        // Open feature request: https://github.com/selenide/selenide/issues/1636

        String browser = Configuration.browser.toLowerCase();
        if (browser.contains("firefox")) {
            attachText("Browser console logs", "Browser console logs are not supported for Firefox");
            return;
        }

        try {
            String logs = String.join("\n", Selenide.getWebDriverLogs(BROWSER));
            attachText("Browser console logs", logs);
        } catch (WebDriverException e) {
            attachText("Browser console logs", "Failed to retrieve browser logs:\n" + e.getMessage());
        }
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String attachVideo(String url) {
        return "<html>" +
                "<body>" +
                "<video width='100%' height='100%' controls autoplay>" +
                "<source src='" + url + "' type='video/mp4'>" +
                "</video>" +
                "</body>" +
                "</html>";
    }
}
