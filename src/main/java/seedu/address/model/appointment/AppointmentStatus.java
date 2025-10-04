package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the status of an appointment.
 * Must be one of planned, confirmed, completed, cancelled.
 */
public class AppointmentStatus {

    public static final String MESSAGE_CONSTRAINTS =
        "Status must be one of: planned, confirmed, completed, cancelled";
    public static final String VALIDATION_REGEX = "planned|confirmed|completed|cancelled";

    public static final String PLANNED = "planned";

    public final String value;

    public AppointmentStatus(String status) {
        requireNonNull(status);
        String normalized = status.toLowerCase();
        checkArgument(isValidStatus(normalized), MESSAGE_CONSTRAINTS);
        this.value = normalized;
    }

    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentStatus
                && value.equals(((AppointmentStatus) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value;
    }
}
