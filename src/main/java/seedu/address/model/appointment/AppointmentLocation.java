package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the location of an appointment.
 */
public class AppointmentLocation {

    public static final String MESSAGE_CONSTRAINTS =
        "Location can take any values but should not contain invalid symbols.";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum} ,.#\\-/()';&:]*$";
    public static final String NO_LOCATION = "";

    public final String value;

    public AppointmentLocation(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.value = location;
    }

    public static boolean isValidLocation(String test) {
        return test.equals("") || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.isEmpty() ? "" : value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentLocation
                && value.equals(((AppointmentLocation) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
