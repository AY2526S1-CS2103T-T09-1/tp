package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Represents an Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private final Name clientName;
    private final AppointmentId id;
    private final AppointmentDateTime dateTime;
    private final AppointmentLength length;
    private final AppointmentLocation location;
    private final AppointmentType type;
    private final AppointmentMessage message;
    private final AppointmentStatus status;

    /**
     * Every field must be present and not null.
     * A random id will be created
     */
    public Appointment(Name clientName,
                       AppointmentDateTime dateTime,
                       AppointmentLength length,
                       AppointmentLocation location,
                       AppointmentType type,
                       AppointmentMessage message,
                       AppointmentStatus status) {
        requireAllNonNull(clientName, dateTime, length, location, type, message, status);
        this.id = new AppointmentId();
        this.clientName = clientName;
        this.dateTime = dateTime;
        this.length = length;
        this.location = location;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    /**
     * Constructor for Appointment with an ID already created
     * @param id ID for Appointment
     */
    public Appointment(AppointmentId id,
                       Name clientName,
                       AppointmentDateTime dateTime,
                       AppointmentLength length,
                       AppointmentLocation location,
                       AppointmentType type,
                       AppointmentMessage message,
                       AppointmentStatus status) {
        requireAllNonNull(id, clientName, dateTime, length, location, type, message, status);
        this.id = id;
        this.clientName = clientName;
        this.dateTime = dateTime;
        this.length = length;
        this.location = location;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public AppointmentId getId() {
        return this.id;
    }

    public Name getClientName() {
        return clientName;
    }

    public AppointmentDateTime getDateTime() {
        return dateTime;
    }

    public AppointmentLength getLength() {
        return length;
    }

    public AppointmentLocation getLocation() {
        return location;
    }

    public AppointmentType getType() {
        return type;
    }

    public AppointmentMessage getMessage() {
        return message;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Returns true if both appointments have the same client and date/time.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment other) {
        if (other == this) {
            return true;
        }
        return other != null
                && other.getId().equals(getId())
                && other.getClientName().equals(getClientName())
                && other.getDateTime().equals(getDateTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return clientName.equals(otherAppointment.clientName)
                && dateTime.equals(otherAppointment.dateTime)
                && length.equals(otherAppointment.length)
                && location.equals(otherAppointment.location)
                && type.equals(otherAppointment.type)
                && message.equals(otherAppointment.message)
                && status.equals(otherAppointment.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, length, location, type, message, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("dateTime", dateTime)
                .add("length", length)
                .add("location", location)
                .add("type", type)
                .add("note", message)
                .add("status", status)
                .toString();
    }
}
