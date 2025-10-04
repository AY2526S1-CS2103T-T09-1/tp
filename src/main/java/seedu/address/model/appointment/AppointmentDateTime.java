package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date and time of an appointment.
 * Format: dd-MM-yyyy or dd-MM-yyyy HHmm (24-hour time).
 */
public class AppointmentDateTime {

    public static final String MESSAGE_CONSTRAINTS =
        "DateTime must be in the format dd-MM-yyyy or dd-MM-yyyy HHmm, and must be valid calendar date/time.";
    // Very simple regex: date part mandatory, optional time part
    public static final String VALIDATION_REGEX =
            "^\\d{1,2}-\\d{1,2}-\\d{4}( \\d{4})?$";

    public final String value;

    public AppointmentDateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = dateTime;
    }

    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
        // NOTE: Later you can plug in LocalDate/LocalDateTime parsing for stricter checks
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentDateTime
                && value.equals(((AppointmentDateTime) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
