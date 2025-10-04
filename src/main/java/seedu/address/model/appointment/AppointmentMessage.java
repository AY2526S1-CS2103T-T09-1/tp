package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents a message attached to an appointment.
 */
public class AppointmentMessage {

    public static final String NO_MESSAGE = "";

    public final String value;

    public AppointmentMessage(String msg) {
        requireNonNull(msg);
        this.value = msg.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentMessage
                && value.equals(((AppointmentMessage) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.isEmpty() ? "" : value;
    }
}
