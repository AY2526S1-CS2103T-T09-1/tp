package seedu.address.model.appointment;

import java.util.UUID;

public class AppointmentId {
    private final String id;
    public static final String NO_ID = "";

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
