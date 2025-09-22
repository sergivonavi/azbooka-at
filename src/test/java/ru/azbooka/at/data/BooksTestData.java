package ru.azbooka.at.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BooksTestData {
    private static final List<Book> books = Arrays.asList(
            new Book("tainstvennyy-ostrov-fwns", "Таинственный остров", "Жюль Верн"),
            new Book("dvadtsat-tysyach-le-pod-vodoy-pdtx", "Двадцать тысяч лье под водой", "Жюль Верн"),
            new Book("deti-kapitana-granta", "Дети капитана Гранта", "Жюль Верн"),
            new Book("kapitanskaya-dochka-l8gy", "Капитанская дочка", "Александр Пушкин"),
            new Book("anna-karenina", "Анна Каренина", "Лев Толстой"),
            new Book("master-i-margarita-yt0e", "Мастер и Маргарита", "Михаил Булгаков"),
            new Book("prestyplenie-i-nakazanie", "Преступление и наказание", "Федор Достоевский"),
            new Book("tayna-tretey-planety", "Тайна третьей планеты", "Кир Булычев"),
            new Book("komu-na-rusi-zhit-khorosho-dk2z", "Кому на Руси жить хорошо", "Николай Некрасов"),
            new Book("strannik-i-ego-ten", "Странник и его тень", "Фридрих Ницше"),
            new Book("svecha-gorela-ouio", "Свеча горела", "Борис Пастернак"),
            new Book("kosmos-etsm", "Космос", "Эстебан Буро")
    );
    public static final int BOOKS_TOTAL = books.size();

    public static String getRandomBookCode() {
        return books.get(new Random().nextInt(books.size())).getCode();
    }

    private static List<Book> getRandomBooks(int count) {
        if (count < 1 || count > books.size()) {
            throw new IllegalArgumentException("Count must be between 1 and " + books.size() + ": " + count);
        }
        List<Book> shuffled = new ArrayList<>(books);
        Collections.shuffle(shuffled, new Random());
        return shuffled.subList(0, count);
    }

    public static List<String> getRandomBookCodes() {
        int count = new Random().nextInt(books.size()) + 1;
        return getRandomBookCodes(count);
    }

    public static List<String> getRandomBookCodes(int count) {
        return getRandomBooks(count).stream()
                .map(Book::getCode)
                .toList();
    }

    public static List<String> getRandomBookCodes(int min, int max) {
        if (min < 1) {
            throw new IllegalArgumentException("Min must be at least 1: " + min);
        }
        if (max > books.size()) {
            throw new IllegalArgumentException("Max cannot be greater than available books: " + max);
        }
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max: " + min + " > " + max);
        }

        int count = new Random().nextInt((max - min) + 1) + min;
        return getRandomBookCodes(count);
    }

    public static String getRandomBookCodeIn(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Provided list of list is empty or null");
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static String getRandomBookCodeNotIn(List<String> codes) {
        List<String> available = books.stream()
                .map(Book::getCode)
                .filter(code -> !codes.contains(code))
                .toList();

        if (available.isEmpty()) {
            throw new IllegalArgumentException("No available book codes outside of provided list");
        }

        return available.get(new Random().nextInt(available.size()));
    }

    public static List<Book> getBooksByCodes(List<String> codes) {
        return codes.stream()
                .map(code -> books.stream()
                        .filter(book -> book.getCode().equals(code))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Book not found: " + code)))
                .toList();
    }
}
