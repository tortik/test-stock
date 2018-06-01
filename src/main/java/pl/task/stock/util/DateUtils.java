package pl.task.stock.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static Set<String> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return LongStream.range(0, numOfDaysBetween)
                .mapToObj(startDate.isBefore(endDate) ? startDate::plusDays : startDate::minusDays)
                .map(DATE_FORMATTER::format)
                .collect(Collectors.toSet());
    }

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            //log exception here, console should have only answer
            return false;
        }
    }



    public static LocalDate transform(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

/*    public static String transform(LocalDate date) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return DATE_FORMATTER.DATE_FORMATTER(date);
    }*/


    private DateUtils() {
    }
}
