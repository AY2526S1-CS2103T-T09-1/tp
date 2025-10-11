package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building {@code Appointment} objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_ID = "0";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_DATE_TIME = "12-10-2099 1430";
    public static final String DEFAULT_APPOINTMENT_LENGTH = "90";
    public static final String DEFAULT_APPOINTMENT_LOCATION = "Bukit Merah FSC";
    public static final String DEFAULT_APPOINTMENT_TYPE = "Health";
    public static final String DEFAULT_APPOINTMENT_MESSAGE = "Routine checkup";
    public static final String DEFAULT_APPOINTMENT_STATUS = "planned";

    private AppointmentId id;
    private Name clientName;
    private AppointmentDateTime dateTime;
    private AppointmentLength length;
    private AppointmentLocation location;
    private AppointmentType type;
    private AppointmentMessage message;
    private AppointmentStatus status;

    /**
     * Creates a {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        id = new AppointmentId(DEFAULT_ID);
        clientName = new Name(DEFAULT_NAME);
        dateTime = new AppointmentDateTime(DEFAULT_DATE_TIME);
        length = new AppointmentLength(DEFAULT_APPOINTMENT_LENGTH);
        location = new AppointmentLocation(DEFAULT_APPOINTMENT_LOCATION);
        type = new AppointmentType(DEFAULT_APPOINTMENT_TYPE);
        message = new AppointmentMessage(DEFAULT_APPOINTMENT_MESSAGE);
        status = new AppointmentStatus(DEFAULT_APPOINTMENT_STATUS);
    }

    /**
     * Initializes the {@code AppointmentBuilder} with the data of {@code appointment}.
     */
    public AppointmentBuilder(Appointment appointment) {
        id = appointment.getId();
        clientName = appointment.getClientName();
        dateTime = appointment.getDateTime();
        length = appointment.getLength();
        location = appointment.getLocation();
        type = appointment.getType();
        message = appointment.getMessage();
        status = appointment.getStatus();
    }

    /**
     * Sets the {@code clientName} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withName(String name) {
        this.clientName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = new AppointmentDateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code length} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withLength(String length) {
        this.length = new AppointmentLength(length);
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withLocation(String location) {
        this.location = new AppointmentLocation(location);
        return this;
    }

    /**
     * Sets the {@code type} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withType(String type) {
        this.type = new AppointmentType(type);
        return this;
    }

    /**
     * Sets the {@code message} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withMessage(String message) {
        this.message = new AppointmentMessage(message);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStatus(String status) {
        this.status = new AppointmentStatus(status);
        return this;
    }

    public Appointment build() {
        return new Appointment(id, clientName, dateTime, length, location, type, message, status);
    }
}
