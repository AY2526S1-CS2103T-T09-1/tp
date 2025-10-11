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
     * Building person query at once
     */
    public AppointmentQuery(AppointmentDateTimeQuery dateTime,
                            AppointmentType type,
                            AppointmentStatus status) {
        this.dateTime = Optional.ofNullable(dateTime);
        this.type = Optional.ofNullable(type);
        this.status = Optional.ofNullable(status);
    }

    /**
     * A default factory method to create PersonQuery object
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
     * Function object of type {@code Predicate<Appointment>}
     */
    public boolean filter(Appointment appointment) {
        boolean isCorrectDateTime = this.dateTime
            .filter(dateTime -> !dateTime.filter(appointment)).isEmpty();
        boolean isCorrectStatus = this.status
            .filter(status -> !appointment.getStatus().equals(status)).isEmpty();
        boolean isCorrectType = this.type
            .filter(type -> !appointment.getType().equals(type)).isEmpty();
        return isCorrectDateTime && isCorrectStatus && isCorrectType;
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
