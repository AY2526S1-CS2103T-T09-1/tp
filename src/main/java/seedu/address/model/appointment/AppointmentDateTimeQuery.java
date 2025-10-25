package seedu.address.model.appointment;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a query object to find {@code AppointmentDateTime}.
 */
public class AppointmentDateTimeQuery {
    public static final String MESSAGE_CONSTRAINTS =
        "Please enter a valid DateTime in one of the following formats:\n"
            + "• 'today' — for today's date\n"
            + "• '+N' or '-N' — where N is the number of days from today (no more than 4 digits)\n"
            + "• 'dd-MM-yyyy' — for a specific date\n"
            + "• 'dd-MM-yyyy (HHmm) to dd-MM-yyyy (HHmm)' — for a custom date range\n"
            + "Note: Time (HHmm) is optional. All dates must be valid calendar dates.";

    public static final String KEYWORD_TODAY = "today";
    private static final String VALIDATION_REGEX =
            "^(today|"
            + "[+-]\\d{1,4}" + "|\\d{2}-\\d{2}-\\d{4}( \\d{4})?|"
            + "\\d{2}-\\d{2}-\\d{4}( \\d{4})? to \\d{2}-\\d{2}-\\d{4}( \\d{4})?)$";
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates query object with the specified start and end DateTime.
     * If the argument is null, {@code LocalDateTime.MIN} and {@code LocalDateTime.MAX}
     * will be used instead.
     */
    public AppointmentDateTimeQuery(
        LocalDateTime start,
        LocalDateTime end
    ) {
        this.start = start == null ? LocalDateTime.MIN : start;
        this.end = end == null ? LocalDateTime.MAX : end;
    }

    /**
     * Create a query object wrt to the current Date.
     */
    public AppointmentDateTimeQuery(
        LocalDateTime current
    ) {
        if (current == null) {
            this.start = LocalDateTime.MIN;
            this.end = LocalDateTime.MAX;
        } else {
            this.start = current.toLocalDate().atStartOfDay();
            this.end = current.toLocalDate().atTime(23, 59, 59);
        }
    }

    public static AppointmentDateTimeQuery empty() {
        return new AppointmentDateTimeQuery(null, null);
    }

    public static boolean isValidDateTimeQuery(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a query object for today
     */
    public static AppointmentDateTimeQuery today() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        return new AppointmentDateTimeQuery(startOfDay, endOfDay);
    }

    /**
     * Returns a query object given the start time and duration
     */
    public static AppointmentDateTimeQuery interval(LocalDateTime start, Duration duration) {
        LocalDateTime later = start.plus(duration);
        return new AppointmentDateTimeQuery(start, later);
    }

    /**
     * Returns a query object with the specified duration.
     */
    public static AppointmentDateTimeQuery withinRelativeDays(long days) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        if (days >= 0) {
            return new AppointmentDateTimeQuery(startOfDay, startOfDay.plusDays(days));
        } else {
            return new AppointmentDateTimeQuery(startOfDay.minusDays(-days), endOfDay);
        }
    }

    public boolean isOverlapped(AppointmentDateTimeQuery query) {
        return query != null && start.isBefore(query.end) && query.start.isBefore(end);
    }

    /**
     * A predicate function of type {@code Predicate<AppointmentDateTime>} to filter
     * AppointmentDateTime in a collection.
     */
    public boolean filter(Appointment appointment) {
        var startTime = appointment.getDateTime().dateTime;
        var duration = appointment.getLength().duration;
        AppointmentDateTimeQuery query = interval(startTime, duration);
        return this.isOverlapped(query);
    }

    /**
     * Chain the string with {@code ToStringBuilder} object
     */
    public String toString(ToStringBuilder builder) {
        builder.add("startTime", this.start)
            .add("endTime", this.end);
        return builder.toString();
    }

    /**
     * Returns true if both appointment timings have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDateTimeQuery)) {
            return false;
        }

        AppointmentDateTimeQuery otherQuery = (AppointmentDateTimeQuery) other;
        return start.equals(otherQuery.start)
            && end.equals(otherQuery.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
