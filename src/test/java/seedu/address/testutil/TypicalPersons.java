package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_STABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
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

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // Sample appointments
    /*
    Use this in test cases
     */
    public static final Appointment DUMMY_APPT = new Appointment(
            new AppointmentId("0"),
            new Name("Dummy"),
            new AppointmentDateTime("20-10-2025 1400"),
            new AppointmentLength("30"),
            new AppointmentLocation("Dummy"),
            new AppointmentType("Dummy"),
            new AppointmentMessage("Dummy"),
            new AppointmentStatus("planned")
    );

    public static final Appointment DENTIST_APPT = new Appointment(
            new AppointmentId("1"),
            new Name("Benson Meier"),
            new AppointmentDateTime("20-10-2025 1400"),
            new AppointmentLength("60"),
            new AppointmentLocation("Dental Clinic"),
            new AppointmentType("Health"),
            new AppointmentMessage("Routine checkup"),
            new AppointmentStatus("planned")
    );

    public static final Appointment MEETING_APPT = new Appointment(
            new AppointmentId("2"),
            new Name("Alice Pauline"),
            new AppointmentDateTime("21-10-2025 1030"),
            new AppointmentLength("90"),
            new AppointmentLocation("NTU Library"),
            new AppointmentType("Meeting"),
            new AppointmentMessage("Project discussion"),
            new AppointmentStatus("planned")
    );

    public static final Appointment MEETING_BOB = new Appointment(
            new AppointmentId("3"),
            new Name("Bob Choo"),
            new AppointmentDateTime("20-10-2025 1400"),
            new AppointmentLength("60"),
            new AppointmentLocation("Dental Clinic"),
            new AppointmentType("Health"),
            new AppointmentMessage("Routine checkup"),
            new AppointmentStatus("planned")
    );

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends").withRank("stable")
            .withAppointments(MEETING_APPT)
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withRank("stable")
            .withAppointments(DENTIST_APPT)
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRank("stable").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withTags("friends").withRank("stable").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94823224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94821427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94821442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84832424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84823131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail("").withAddress("").withTags(VALID_TAG_FRIEND)
            .withRank("").build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRank(VALID_RANK_STABLE).withAppointments(MEETING_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Extract list of appointments from the given persons array
     */
    public static List<Appointment> extractAppointmentsFromPersons(List<Person> persons) {
        return persons.stream()
            .flatMap(person -> person.getAppointments().stream())
            .toList();
    }

    /**
     * Returns a typical address book
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        List<Person> samplePersons = getTypicalPersons();
        for (Person person : samplePersons) {
            ab.addPerson(person);
        }
        extractAppointmentsFromPersons(samplePersons)
            .forEach(ab::addAppointment);
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
