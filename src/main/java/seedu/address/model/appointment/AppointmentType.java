package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents the type of appointment (e.g., home-visit, clinic, meeting).
 */
public class AppointmentType {

    public static final String NO_TYPE = "";

    public final String value;

    public AppointmentType(String type) {
        requireNonNull(type);
        this.value = type.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentType
                && value.equals(((AppointmentType) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value.isEmpty() ? "" : value;
    }
}
