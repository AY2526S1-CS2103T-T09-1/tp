package seedu.address.model.appointment;

import java.util.UUID;

/**
 * Represents the ID of an appointment.
 * Format: any string.
 */
public class AppointmentId {
    public static final String NO_ID = "";
    private final String id;

    public AppointmentId() {
        this.id = UUID.randomUUID().toString().substring(0, 6);
    }

    public AppointmentId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AppointmentId) {
            return ((AppointmentId) other).id.equals(this.id);
        }
        return false;
    }
}
