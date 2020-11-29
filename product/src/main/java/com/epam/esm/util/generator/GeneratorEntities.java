package com.epam.esm.util.generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorEntities {

    private String GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate " +
            "(name, description, price, create_date, last_update_date, duration) " +
            "VALUES ('" + generatingRandomAlphabeticString() + "'," +
            " '" + generatingRandomAlphabeticString() + "'," +
            " '" + generateRandomDouble() + "'," +
            " '" + generateRandomLocalDateTime() + "'," +
            " '" + generateRandomLocalDateTime() + "'," +
            " '" + generateRandomDuration() + "');\n";

    private String TAG_QUERY = "insert into tag (name) VALUES ('" + generatingRandomAlphabeticString() + "');\n";

    private String GIFT_CERTIFICATE_TAGS_QUERY = "INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)" +
            " VALUES (" + generateRandomInteger() + ", " + generateRandomInteger() + ");\n";

    private String USER_QUERY = "INSERT INTO user (name) VALUES ('" + generatingRandomAlphabeticString() + "');\n";
    private String ORDER_QUERY = "INSERT INTO users_order (create_date, cost, user_id) " +
            "VALUES ('" + generateRandomLocalDateTime() + "'," +
            " '" + generateRandomDouble() + "'," +
            " '" + generateRandomInteger() + "');\n";

    private String ORDER_GIFT_CERTIFICATE_QUERY = "INSERT INTO users_order_gift_certificate (order_id, gift_certificate_Id) " +
            "VALUES ('" + generateRandomInteger() + "'," +
            " '" + generateRandomInteger() + "');\n";

    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static String generatingRandomAlphabeticString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static Double generateRandomDouble() {
        double min = 10;
        double max = 100;
        return min + (max - min) * new Random().nextDouble();
    }

    public static Integer generateRandomInteger() {
        Integer min = 1;
        Integer max = 1000;
        return new Random().nextInt(max - min) + min;
    }

    private static String generateRandomLocalDateTime() {
        LocalDateTime start = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 12);
        long days = ChronoUnit.DAYS.between(start, LocalDateTime.now());
        LocalDateTime randomDateTime = start
                .plusDays(new Random().nextInt((int) days + 1))
                .minusHours(new Random().nextInt((int) days + 1))
                .minusMinutes(new Random().nextInt((int) days + 1));
        return randomDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
    }

    private static String generateRandomDuration() {
        int daysMin = 10;
        int daysMax = 40;
        int randomDays = ThreadLocalRandom.current().nextInt(daysMin, daysMax + 1);
        return String.valueOf(Duration.ofDays(randomDays).toMillis());
    }

    private static String generateQuery() {
        return "INSERT INTO users_order_gift_certificate (order_id, gift_certificate_Id) " +
        "VALUES ('" + generateRandomInteger() + "'," +
                " '" + generateRandomInteger() + "');\n";
    }

    private static void writeQueryToFile(String fileName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        for (int i = 0; i < 1000; i++) {
            byte[] strToBytes = generateQuery().getBytes();
            outputStream.write(strToBytes);
        }
        outputStream.close();
    }

    public static void main(String[] args) throws IOException {
        writeQueryToFile("orderGIftCertificate.sql");
    }
}



