package fr.thetiptop.app.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GameUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseLocalDateTime(String strDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(strDate, dtf);
    }

    public static int randomValue(int minValue, int maxValueExcluded) {
        Random random = new Random();
        return random.nextInt(minValue, maxValueExcluded);
    }
}
