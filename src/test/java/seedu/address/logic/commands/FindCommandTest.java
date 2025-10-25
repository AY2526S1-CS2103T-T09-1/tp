package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_STABLE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WITH_SUBSTRING_NAME;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.DENTIST_APPT;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.MEETING_APPT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentDateTimeQuery;
import seedu.address.model.appointment.AppointmentQuery;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonQuery;
import seedu.address.model.person.Phone;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonQuery firstQuery = PersonQuery.build().setName(new String[]{"first"});
        AppointmentQuery firstAppointmentQuery = AppointmentQuery.build().setStatus(new AppointmentStatus("planned"));
        PersonQuery secondQuery = PersonQuery.build().setName(new String[]{"second"});
        AppointmentQuery secondAppointmentQuery = AppointmentQuery.build()
            .setDateTime(AppointmentDateTimeQuery.today());

        FindCommand findFirstCommand = new FindCommand(firstQuery, firstAppointmentQuery);
        FindCommand findSecondCommand = new FindCommand(secondQuery, secondAppointmentQuery);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstQuery, firstAppointmentQuery);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_emptyQuery_showAllPersons() {
        ObservableList<Person> originalList = expectedModel.getFilteredPersonList();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, originalList.size());
        PersonQuery emptyQuery = PersonQuery.build();
        FindCommand command = new FindCommand(emptyQuery);
        expectedModel.updateFilteredPersonList(emptyQuery::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleNames_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonQuery query = PersonQuery.build().setName(new String[]{"Kurz", "Elle", "Kunz"});
        FindCommand command = new FindCommand(query);
        expectedModel.updateFilteredPersonList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_phoneNumber_aliceFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonQuery query = PersonQuery.build().setPhone(new Phone("94351253"));
        FindCommand command = new FindCommand(query);
        expectedModel.updateFilteredPersonList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ALICE_WITH_SUBSTRING_NAME), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleTag_multipleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        PersonQuery query = PersonQuery.build().setTags(Set.of(new Tag("friends")));
        FindCommand command = new FindCommand(query);
        expectedModel.updateFilteredPersonList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ALICE_WITH_SUBSTRING_NAME), model.getFilteredPersonList());
    }

    @Test
    public void execute_rank_multipleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        PersonQuery query = PersonQuery.build().setRank(new Rank(VALID_RANK_STABLE));
        FindCommand command = new FindCommand(query);
        expectedModel.updateFilteredPersonList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ALICE_WITH_SUBSTRING_NAME),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_appointmentDateTime_aliceFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 1);
        AppointmentDateTimeQuery dateTimeQuery = AppointmentDateTimeQuery.interval(
            ALICE.getAppointments().get(0).getDateTime().dateTime,
            ALICE.getAppointments().get(0).getLength().duration);
        AppointmentQuery query = AppointmentQuery.build().setDateTime(dateTimeQuery);
        FindCommand command = new FindCommand(PersonQuery.build(), query);
        expectedModel.updateFilteredAppointmentList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE.getAppointments().get(0)), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_appointmentDateTime_noOneFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0);
        AppointmentDateTimeQuery dateTimeQuery = AppointmentDateTimeQuery.interval(
            LocalDateTime.MIN, Duration.ofDays(1));
        AppointmentQuery query = AppointmentQuery.build().setDateTime(dateTimeQuery);
        FindCommand command = new FindCommand(PersonQuery.build(), query);
        expectedModel.updateFilteredAppointmentList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_appointmentStatus_twoPeopleFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2);
        AppointmentQuery query = AppointmentQuery.build().setStatus(new AppointmentStatus("planned"));
        FindCommand command = new FindCommand(PersonQuery.build(), query);
        expectedModel.updateFilteredAppointmentList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING_APPT, DENTIST_APPT),
            model.getFilteredAppointmentList());
    }

    @Test
    public void execute_appointmentType_aliceFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 1);
        AppointmentQuery query = AppointmentQuery.build().setType(new AppointmentType("Meeting"));
        FindCommand command = new FindCommand(PersonQuery.build(), query);
        expectedModel.updateFilteredAppointmentList(query::filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE.getAppointments().get(0)), model.getFilteredAppointmentList());
    }

    @Test
    public void toStringMethod() {
        PersonQuery personQuery = PersonQuery.build().setName(new Name[]{ALICE.getName()})
            .setEmail(ALICE.getEmail())
            .setPhone(ALICE.getPhone())
            .setRank(ALICE.getRank());
        AppointmentQuery appointmentQuery = AppointmentQuery.build()
            .setDateTime(AppointmentDateTimeQuery.today())
            .setStatus(new AppointmentStatus("planned"));

        FindCommand findCommand = new FindCommand(personQuery, appointmentQuery);

        String expected = FindCommand.class.getCanonicalName()
            + "{personQuery=" + personQuery.toString(new ToStringBuilder("")) + ", "
            + "appointmentQuery=" + appointmentQuery.toString(new ToStringBuilder("")) + "}";
        assertEquals(expected, findCommand.toString());
    }
}
