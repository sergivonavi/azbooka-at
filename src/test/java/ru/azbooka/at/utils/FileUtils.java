package ru.azbooka.at.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class FileUtils {

    public static File getFile(String resourcePath) {
        try {
            URL resource = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(resourcePath);
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getContentType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                throw new IllegalArgumentException("Cannot determine content type for file: " + file.getName());
            }
            return contentType;
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine content type for file: " + file.getName(), e);
        }
    }
}
