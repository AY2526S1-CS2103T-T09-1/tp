package seedu.address.model.util;

import java.time.LocalDateTime;

/**
 * Utility class for date and time operations, specifically for converting between
 * {@link LocalDateTime} objects and their string representations in the format "dd-MM-yyyy HHmm".
 */
public class DateTimeUtil {
    /**
     * Converts a string representation of a date and time to a {@link LocalDateTime} object.
     * <p>
     * The expected format is "dd-MM-yyyy HHmm". If the time part is omitted, it defaults to midnight ("0000").
     * </p>
     *
     * @param dateTime the string representation of the date and time
     * @return the corresponding {@link LocalDateTime} object
     * @throws IllegalArgumentException if the input string is not in the expected format
     */
    public static LocalDateTime localDateTimeFromString(String dateTime) {
        String[] parts = dateTime.split(" ");
        String datePart = parts[0];
        String timePart = parts.length > 1 ? parts[1] : "0000"; // Default to midnight if time is not provided

        String[] dateComponents = datePart.split("-");
        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        int hour = Integer.parseInt(timePart.substring(0, 2));
        int minute = Integer.parseInt(timePart.substring(2, 4));

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * Converts a {@link LocalDateTime} object to its string representation in the format "dd-MM-yyyy HHmm".
     *
     * @param dateTime the {@link LocalDateTime} object to convert
     * @return the string representation of the date and time
     */
    public static String stringFromLocalDateTime(LocalDateTime dateTime) {
        String day = String.format("%02d", dateTime.getDayOfMonth());
        String month = String.format("%02d", dateTime.getMonthValue());
        String year = String.valueOf(dateTime.getYear());
        String hour = String.format("%02d", dateTime.getHour());
        String minute = String.format("%02d", dateTime.getMinute());

        return String.format("%s-%s-%s %s%s", day, month, year, hour, minute);
    }
}
