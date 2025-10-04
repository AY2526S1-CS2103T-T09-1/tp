package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the length of an appointment in minutes.
 */
public class AppointmentLength {

    public static final String MESSAGE_CONSTRAINTS =
        "Length must be a positive integer number of minutes (e.g. 30, 60, 90)";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";
    public static final String NO_LENGTH = "";

    public final String value;

    public AppointmentLength(String length) {
        requireNonNull(length);
        checkArgument(isValidLength(length), MESSAGE_CONSTRAINTS);
        this.value = length;
    }

    public static boolean isValidLength(String test) {
        return test.equals(NO_LENGTH) || test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentLength
                && value.equals(((AppointmentLength) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value.equals("") ? "" : value;
    }
}
