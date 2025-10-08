package ru.azbooka.at.data;

import net.datafaker.Faker;

import java.util.Locale;

public class UserTestData {
    public static final String DEFAULT_AVATAR = "avatar/avatar_default.jpg";
    public static final String DEFAULT_FIRST_NAME = "Иван";
    public static final String DEFAULT_LAST_NAME = "Иванов";

    private static final String[] AVATARS = {
            "avatar/avatar1.jpg",
            "avatar/avatar2.jpeg",
            "avatar/avatar3.webp"
    };

    private static final Faker faker = new Faker(new Locale("ru"));

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomAvatar() {
        return faker.options().option(AVATARS);
    }
}
