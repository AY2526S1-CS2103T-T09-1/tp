package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.Duration;

import seedu.address.model.util.DurationUtil;

/**
 * Represents the length of an appointment in minutes.
 */
public class AppointmentLength {

    public static final String MESSAGE_CONSTRAINTS =
            "Length must be a positive integer number of minutes (e.g. 30, 60, 90)";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";
    public static final String NO_LENGTH = "";

    public final Duration duration;

    /**
     * Construct an appointment length (duration)
     */
    public AppointmentLength(String durationString) {
        requireNonNull(durationString);
        checkArgument(isValidLength(durationString), MESSAGE_CONSTRAINTS);
        this.duration = DurationUtil.durationFromString(durationString);
    }

    /**
     * Checks if string is a valid length (0 is unacceptable)
     * @param test String to be checked
     * @return A boolean stating whether string is valid or not
     */
    public static boolean isValidLength(String test) {
        return test.equals(NO_LENGTH) || test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentLength
                && duration.equals(((AppointmentLength) other).duration));
    }

    @Override
    public int hashCode() {
        return duration.hashCode();
    }

    public String toString() {
        return DurationUtil.stringFromDuration(duration);
    }
}
