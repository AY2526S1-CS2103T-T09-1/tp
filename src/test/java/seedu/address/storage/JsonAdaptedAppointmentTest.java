package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.MEETING_APPT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.person.Name;

public class JsonAdaptedAppointmentTest {
    private static final String INVALID_DATE_TIME = "12-123-1234";
    private static final String INVALID_LENGTH = "-1";
    private static final String INVALID_LOCATION = "!!!@@@";
    private static final String INVALID_STATUS = "Flying";

    private static final Name VALID_NAME = ALICE.getName();
    private static final String VALID_ID = "123456";
    private static final String VALID_DATE_TIME = "06-10-2025 0930";
    private static final String VALID_LENGTH = "30";
    private static final String VALID_LOCATION = "Dental Clinic, Orchard";
    private static final String VALID_TYPE = "Consultation";
    private static final String VALID_MESSAGE = "Bring insurance card";
    private static final String VALID_STATUS = "planned";

    @Test
    public void toModelType_validDateTime_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(MEETING_APPT);
        assertEquals(MEETING_APPT, appt.toModelType(VALID_NAME));
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(
            VALID_ID, INVALID_DATE_TIME, VALID_LENGTH, VALID_LOCATION,
            VALID_TYPE, VALID_MESSAGE, VALID_STATUS);
        String expectedMessage = AppointmentDateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> appt.toModelType(VALID_NAME));
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(
            VALID_ID, null, VALID_LENGTH, VALID_LOCATION,
            VALID_TYPE, VALID_MESSAGE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            AppointmentDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class,
            expectedMessage, () -> appt.toModelType(VALID_NAME));
    }

    @Test
    public void toModelType_invalidLength_throwsIllegalValueException() {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(
            VALID_ID, VALID_DATE_TIME, INVALID_LENGTH, VALID_LOCATION,
            VALID_TYPE, VALID_MESSAGE, VALID_STATUS);
        String expectedMessage = AppointmentLength.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> appt.toModelType(VALID_NAME));
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(
            VALID_ID, VALID_DATE_TIME, VALID_LENGTH, INVALID_LOCATION,
            VALID_TYPE, VALID_MESSAGE, VALID_STATUS);
        String expectedMessage = AppointmentLocation.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, () -> appt.toModelType(VALID_NAME));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(
            VALID_ID, VALID_DATE_TIME, VALID_LENGTH, VALID_LOCATION,
            VALID_TYPE, VALID_MESSAGE, INVALID_STATUS);
        String expectedMessage = AppointmentStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> appt.toModelType(VALID_NAME));
    }
}
