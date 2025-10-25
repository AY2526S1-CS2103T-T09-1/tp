package seedu.address.model.appointment;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Represents a query object to find Appointment in the address book.
 * Guarantees: Field values are validated.
 * Remark: Details can be null and mutable.
 */
public class AppointmentQuery {
    private Optional<AppointmentStatus> status;
    private Optional<AppointmentType> type;
    private Optional<AppointmentDateTimeQuery> dateTime;

    /**
     * Represents empty query
     */
    private AppointmentQuery() {
        this(null, null, null);
    }

    /**
     * Building appointment query at once
     */
    public AppointmentQuery(AppointmentDateTimeQuery dateTime,
                            AppointmentType type,
                            AppointmentStatus status) {
        this.dateTime = Optional.ofNullable(dateTime);
        this.type = Optional.ofNullable(type);
        this.status = Optional.ofNullable(status);
    }

    /**
     * A default factory method to create AppointmentQuery object
     */
    public static AppointmentQuery build() {
        return new AppointmentQuery();
    }

    public AppointmentQuery setDateTime(AppointmentDateTimeQuery dateTime) {
        this.dateTime = Optional.ofNullable(dateTime);
        return this;
    }

    public AppointmentQuery setType(AppointmentType type) {
        this.type = Optional.ofNullable(type);
        return this;
    }

    public AppointmentQuery setStatus(AppointmentStatus status) {
        this.status = Optional.ofNullable(status);
        return this;
    }

    /**
     * Tests whether the given appointment matches this query.
     * @param appointment The appointment to test
     * @return true if the appointment matches all non-empty filter criteria
     */
    public boolean filter(Appointment appointment) {
        boolean matchesDateTime = dateTime
            .map(dt -> dt.filter(appointment))
            .orElse(true);
        boolean matchesStatus = status
            .map(s -> appointment.getStatus().equals(s))
            .orElse(true);
        boolean matchesType = type
            .map(t -> appointment.getType().equals(t))
            .orElse(true);
        return matchesDateTime && matchesStatus && matchesType;
    }

    /**
     * Chain the string with {@code ToStringBuilder} object
     */
    public String toString(ToStringBuilder builder) {
        this.dateTime.ifPresent(n -> builder.add("dateTime", n));
        this.status.ifPresent(p -> builder.add("status", p));
        this.type.ifPresent(e -> builder.add("type", e));
        return builder.toString();
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentQuery)) {
            return false;
        }

        AppointmentQuery otherQuery = (AppointmentQuery) other;
        return dateTime.equals(otherQuery.dateTime)
            && status.equals(otherQuery.status)
            && type.equals(otherQuery.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, status, type);
    }
}
