package seedu.address.storage;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;
import seedu.address.model.util.DateTimeUtil;
import seedu.address.model.util.DurationUtil;

/**
 * Jackson-friendly version of {@link Appointment}.
 * <p>
 * Note: We do NOT store clientName here since the appointment is nested under a Person.
 * During deserialization, the parent {@link JsonAdaptedPerson} passes the person's Name in.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String id;
    private final String dateTime;
    private final String length;
    private final String location;
    private final String type;
    private final String message;
    private final String status;

    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("id") String id,
                                  @JsonProperty("dateTime") String dateTime,
                                  @JsonProperty("length") String length,
                                  @JsonProperty("location") String location,
                                  @JsonProperty("type") String type,
                                  @JsonProperty("message") String message,
                                  @JsonProperty("status") String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.length = length;
        this.location = location;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    /**
     * Converts a model {@code Appointment} to a Jackson-friendly form.
     */
    public JsonAdaptedAppointment(Appointment src) {
        this.id = src.getId().toString();
        this.dateTime = DateTimeUtil.stringFromLocalDateTime(src.getDateTime().dateTime);
        this.length = DurationUtil.stringFromDuration(src.getLength().duration);
        this.location = src.getLocation().value;
        this.type = src.getType().value;
        this.message = src.getMessage().value;
        this.status = src.getStatus().value.toString();
    }

    /**
     * Converts this adapted object back into the model's {@code Appointment},
     * using the parent's person name as the client name.
     */
    public Appointment toModelType(Name ownerName) throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentId.class.getSimpleName()));
        }
        final AppointmentId modelId = new AppointmentId(id);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentDateTime.class.getSimpleName()));
        }
        if (!AppointmentDateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(AppointmentDateTime.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDateTime modelDateTime = new AppointmentDateTime(dateTime);

        final String len = (length == null) || length.equals("0")
                ? AppointmentLength.NO_LENGTH
                : length.trim();
        if (!AppointmentLength.isValidLength(len)) {
            throw new IllegalValueException(AppointmentLength.MESSAGE_CONSTRAINTS);
        }
        final AppointmentLength modelLength = new AppointmentLength(len);

        final AppointmentLocation modelLocation = new AppointmentLocation(
                Objects.requireNonNullElse(location, "")
        );

        final AppointmentType modelType = new AppointmentType(
                Objects.requireNonNullElse(type, ""));

        final AppointmentMessage modelMessage = new AppointmentMessage(
                Objects.requireNonNullElse(message, ""));

        final String stat = (status == null) ? "planned" : status.trim().toLowerCase();
        if (!AppointmentStatus.isValidStatus(stat)) {
            throw new IllegalValueException(AppointmentStatus.MESSAGE_CONSTRAINTS);
        }
        final AppointmentStatus modelStatus = new AppointmentStatus(stat);

        return new Appointment(modelId, ownerName, modelDateTime,
                modelLength, modelLocation, modelType, modelMessage, modelStatus);
    }
}
