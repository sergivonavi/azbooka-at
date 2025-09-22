package ru.azbooka.at.data;

import net.datafaker.Faker;

public class LoginTestData {
    private static final Faker faker = new Faker();

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.credentials().password(8, 16);
    }

    public static String getRandomToken() {
        return faker.hashing().sha256();
    }
}
