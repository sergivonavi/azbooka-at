package ru.azbooka.at.utils;

import org.openqa.selenium.remote.SessionId;

public class RemoteUtils {

    public static String remoteUrl(String username, String password, String domain) {
        return String.format("https://%s:%s@%s/wd/hub", username, password, domain);
    }

    public static String videoUrl(String domain, SessionId sessionId) {
        return String.format("https://%s/video/%s.mp4", domain, sessionId);
    }
}
