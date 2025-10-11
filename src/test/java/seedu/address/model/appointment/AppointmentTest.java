package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.DENTIST_APPT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AppointmentBuilder;

public class AppointmentTest {
    @Test
    public void isSameAppointment() {
        // same object -> returns true
        assertTrue(DENTIST_APPT.isSameAppointment(DENTIST_APPT));

        // null -> returns false
        assertFalse(DENTIST_APPT.isSameAppointment(null));

        // same data -> returns true
        Appointment copiedAppointment = (new AppointmentBuilder(DENTIST_APPT)).build();
        assertTrue(DENTIST_APPT.isSameAppointment(copiedAppointment));

        Appointment editedAppointment;

        // different client name -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withName("Ben").build();
        assertFalse(DENTIST_APPT.isSameAppointment(editedAppointment));

        // different appointment timing -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withDateTime("12-10-2099 1200").build();
        assertFalse(DENTIST_APPT.isSameAppointment(editedAppointment));

        // Weak equality test
        // different length, location, type, message, status -> returns true
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withLength("25")
            .withLocation("NUS")
            .withType("Personal")
            .withMessage("-")
            .withStatus("confirmed").build();
        assertTrue(DENTIST_APPT.isSameAppointment(editedAppointment));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(DENTIST_APPT.equals(DENTIST_APPT));

        // null -> returns false
        assertFalse(DENTIST_APPT.equals(null));

        // same data -> returns true
        Appointment copiedAppointment = (new AppointmentBuilder(DENTIST_APPT)).build();
        assertTrue(DENTIST_APPT.equals(copiedAppointment));

        Appointment editedAppointment;

        // different client name -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withName("Ben").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different appointment timing -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withDateTime("12-10-2099 1200").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different length -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withLength("17").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different location -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withLocation("NUS").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different type -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withType("tech").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different message -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withMessage("default message").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));

        // different status -> returns false
        editedAppointment = (new AppointmentBuilder(DENTIST_APPT))
            .withStatus("confirmed").build();
        assertFalse(DENTIST_APPT.equals(editedAppointment));
    }

    @Test
    public void toStringMethod() {
        String expected = Appointment.class.getCanonicalName()
            + "{id=" + DENTIST_APPT.getId()
            + ", dateTime=" + DENTIST_APPT.getDateTime()
            + ", length=" + DENTIST_APPT.getLength()
            + ", location=" + DENTIST_APPT.getLocation()
            + ", type=" + DENTIST_APPT.getType()
            + ", note=" + DENTIST_APPT.getMessage()
            + ", status=" + DENTIST_APPT.getStatus() + "}";
        assertEquals(expected, DENTIST_APPT.toString());
    }
}
