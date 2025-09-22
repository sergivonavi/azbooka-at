package ru.azbooka.at.utils.report;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
        attachText(
                "Browser console logs",
                String.join("\n", Selenide.getWebDriverLogs(BROWSER))
        );
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
