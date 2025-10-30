package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DENTIST_APPT;
import static seedu.address.testutil.TypicalPersons.DUMMY_APPT;
import static seedu.address.testutil.TypicalPersons.MEETING_APPT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Failed tests (to be resolved in future versions)
 * Missing tests (Updated at 10/8/2025)
 * time clashes within the same client
 * time clashes between clients
 */
public class LinkAppointmentCreateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Person client = new PersonBuilder(ALICE).build();
        Appointment appt = new AppointmentBuilder()
                .withName(client.getName().toString()).withDateTime("12-10-3099 1430").withId("testing").build();
        LinkAppointmentCommand cmd = new LinkAppointmentCreateCommand(
                client.getName(), appt).setAppointmentId(new AppointmentId("testing"));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAppointmentWithPerson(appt, client);
        assertCommandSuccess(cmd, model, String.format(
                LinkAppointmentCommand.MESSAGE_SUCCESS, client.getName(),
                Messages.format(appt)), expectedModel);
    }

    @Test
    public void execute_duplicateAppointmentDifferentLength_failure() {
        Person client = new PersonBuilder(ALICE).build();
        Appointment appt = new AppointmentBuilder()
                .withName(client.getName().toString()).withDateTime("12-10-3099 1430")
                .withLength("90").withId("testing").build();
        LinkAppointmentCommand cmd = new LinkAppointmentCreateCommand(
                client.getName(), appt).setAppointmentId(new AppointmentId("testing"));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAppointmentWithPerson(appt, client);
        assertCommandSuccess(cmd, model, String.format(
                LinkAppointmentCommand.MESSAGE_SUCCESS, client.getName(),
                Messages.format(appt)), expectedModel);

        Appointment modifiedLengthAppointment = new AppointmentBuilder()
                .withName(client.getName().toString()).withDateTime("12-10-3099 1430").withLength("91").build();
        cmd = new LinkAppointmentCreateCommand(
                client.getName(), modifiedLengthAppointment);

        assertCommandFailure(cmd, model, LinkAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENTS);
    }

    @Test
    public void execute_notFoundName_failure() {
        LinkAppointmentCommand cmd = new LinkAppointmentCreateCommand(
                new Name("random name"), DUMMY_APPT);
        assertCommandFailure(cmd, model,
                String.format(LinkAppointmentCommand.MESSAGE_NO_SUCH_PERSON, "random name"));
    }

    @Test
    public void execute_clientNotInList_personNotFoundException() {
        Person person = new PersonBuilder().withName("test").build();
        Appointment appt = new Appointment(
                person.getName(),
                new AppointmentDateTime("26-10-2025 1030"), // different timing
                new AppointmentLength("90"),
                new AppointmentLocation("NTU Library"),
                new AppointmentType("Meeting"),
                new AppointmentMessage("Project discussion"),
                new AppointmentStatus("planned")
        );
        LinkAppointmentCommand cmd = new LinkAppointmentCreateCommand(
                person.getName(), appt);
        assertCommandFailure(cmd, model,
                String.format(LinkAppointmentCommand.MESSAGE_NO_SUCH_PERSON, "test"));
    }

    @Test
    public void execute_duplicateAppointments_duplicateAppointmentException() {
        Person client = new PersonBuilder(ALICE).build();
        assert !ALICE.getAppointments().isEmpty();
        Appointment firstAliceAppointment = new AppointmentBuilder(ALICE.getAppointments().get(0)).build();
        LinkAppointmentCommand cmd = new LinkAppointmentCreateCommand(
                client.getName(), firstAliceAppointment);
        assertCommandFailure(cmd, model, LinkAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENTS);
    }

    @Test
    public void execute_overlappingConfirmedAppointments_clashAppointmentException() {
        Person client = new PersonBuilder(ALICE).build();
        assert !ALICE.getAppointments().isEmpty();
        Appointment firstAliceAppointment = new AppointmentBuilder(ALICE.getAppointments().get(0))
                .withId("testing")
                .withDateTime("12-01-2020 1200")
                .withLength("70")
                .withStatus("confirmed").build();
        Appointment secondAliceAppointment = new AppointmentBuilder(ALICE.getAppointments().get(0))
                .withId("testing II")
                .withDateTime("12-01-2020 1300")
                .withLength("30")
                .withStatus("confirmed").build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkAppointmentCommand firstCmd = new LinkAppointmentCreateCommand(
                client.getName(), firstAliceAppointment).setAppointmentId(new AppointmentId("testing"));
        expectedModel.addAppointmentWithPerson(firstAliceAppointment, client);
        assertCommandSuccess(firstCmd, model, String.format(
                LinkAppointmentCommand.MESSAGE_SUCCESS, client.getName(),
                Messages.format(firstAliceAppointment)), expectedModel);

        LinkAppointmentCommand secondCmd = new LinkAppointmentCreateCommand(
                client.getName(), secondAliceAppointment);
        assertCommandFailure(secondCmd, model,
                String.format(LinkAppointmentCommand.MESSAGE_CLASH_APPOINTMENTS_CREATE,
                        firstAliceAppointment.getId(), firstAliceAppointment.getDateTime(),
                        secondAliceAppointment.getDateTime()));
    }

    @Test
    public void execute_overlappingNotAppointments_noExceptionThrown() {
        Person client = new PersonBuilder(ALICE).build();
        assert !ALICE.getAppointments().isEmpty();
        Appointment firstAliceAppointment = new AppointmentBuilder(ALICE.getAppointments().get(0))
                .withId("testing")
                .withDateTime("12-01-2020 1200")
                .withLength("70")
                .withStatus("confirmed").build();
        Appointment secondAliceAppointment = new AppointmentBuilder(ALICE.getAppointments().get(0))
                .withId("testing II")
                .withDateTime("12-01-2020 1300")
                .withLength("30")
                .withStatus("planned").build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkAppointmentCommand firstCmd = new LinkAppointmentCreateCommand(
                client.getName(), firstAliceAppointment).setAppointmentId(new AppointmentId("testing"));
        expectedModel.addAppointmentWithPerson(firstAliceAppointment, client);
        assertCommandSuccess(firstCmd, model, String.format(
                LinkAppointmentCommand.MESSAGE_SUCCESS, client.getName(),
                Messages.format(firstAliceAppointment)), expectedModel);

        LinkAppointmentCommand secondCmd = new LinkAppointmentCreateCommand(
                client.getName(), secondAliceAppointment).setAppointmentId(new AppointmentId("testing II"));
        expectedModel.addAppointmentWithPerson(secondAliceAppointment,
                client.withAddedAppointment(firstAliceAppointment));
        assertCommandSuccess(secondCmd, model, String.format(
                LinkAppointmentCommand.MESSAGE_SUCCESS, client.getName(),
                Messages.format(secondAliceAppointment)), expectedModel);
    }

    @Test
    public void equals() {
        LinkAppointmentCommand aliceMeeting = new LinkAppointmentCreateCommand(
                ALICE.getName(), MEETING_APPT);
        LinkAppointmentCommand bobMeeting = new LinkAppointmentCreateCommand(
                BOB.getName(), DENTIST_APPT);
        // same object -> returns true
        assertTrue(aliceMeeting.equals(aliceMeeting));
        // same values -> returns true
        LinkAppointmentCommand aliceMeetingCopied = new LinkAppointmentCreateCommand(
                ALICE.getName(), MEETING_APPT);
        assertTrue(aliceMeeting.equals(aliceMeetingCopied));
        // different values -> returns false
        assertFalse(aliceMeeting.equals(bobMeeting));
        // null -> returns false
        assertFalse(aliceMeeting.equals(null));
    }

    @Test
    public void toStringMethod() {
        LinkAppointmentCommand linkCommand = new LinkAppointmentCreateCommand(
                ALICE.getName(), MEETING_APPT);
        String expected = LinkAppointmentCreateCommand.class.getCanonicalName()
                + "{clientName=" + ALICE.getName() + ", appointment="
                + MEETING_APPT + "}";
        assertEquals(expected, linkCommand.toString());
    }
}
