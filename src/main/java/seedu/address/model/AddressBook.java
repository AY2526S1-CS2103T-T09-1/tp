package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTimeQuery;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentQuery;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueAppointmentList appointments;
    private List<AppointmentId> idList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        appointments = new UniqueAppointmentList();
        idList = new ArrayList<>();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);

        for (Appointment appt : appointments) {
            idList.add(appt.getId());
        }
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        idList = new ArrayList<>();
        setPersons(newData.getPersonList());
        setAppointments(newData.getAppointmentList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        // remove associated appointments
        key.getAppointments().forEach(appt -> {
            appointments.remove(appt);
            idList.remove(appt.getId());
        });
        persons.remove(key);
    }

    //// appointment-level operations

    /**
     * Check whether the AddressBook contains the appointment or not
     * Note that AppointmentId field is omitted for comparison
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.containsQuery(appt ->
                appt.getClientName().equals(appointment.getClientName())
                        && appt.getDateTime().equals(appointment.getDateTime())
                        && appt.getStatus().equals(appointment.getStatus())
        ) != null;
    }

    /**
     * Check whether the AddressBook clashes with other appointment or not.
     * Only confirmed appointment is checked.
     */
    public Appointment getClashedAppointment(Appointment appointment) {
        // Construct an appointment query
        if (!appointment.getStatus().equals(new AppointmentStatus("confirmed"))) {
            return null;
        }
        AppointmentQuery query = AppointmentQuery.build()
                .setDateTime(AppointmentDateTimeQuery.interval(appointment.getDateTime().dateTime,
                        appointment.getLength().duration))
                .setStatus(new AppointmentStatus("confirmed"));
        return appointments.containsQuery(appt ->
                query.filter(appt)
                        && appt.getClientName().equals(appointment.getClientName())
                        && !appt.getId().equals(appointment.getId()));
    }

    /**
     * Adds an appointment to the {@code AddressBook} and update the
     * {@code persons} list if applicable.
     */
    public void addAppointment(Appointment a) {
        appointments.add(a);
        idList.add(a.getId());
    }

    /**
     * Removes an appointment from the {@code AddressBook} and update the
     * {@code persons} list if applicable.
     */
    public void removeAppointment(Appointment a) {
        appointments.remove(a);
        idList.remove(a.getId());
    }

    /**
     * Updates an appointment to the {@code AddressBook} and update the
     * {@code persons} list if applicable.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);
        appointments.setAppointment(target, editedAppointment);
        idList.remove(target.getId());
        idList.add(editedAppointment.getId());
    }

    /**
     * Generates a random and unique {@code AppointmentID}
     */
    public AppointmentId generateId() {
        AppointmentId tempId = new AppointmentId(UUID.randomUUID().toString().substring(0, 7));
        while (idList.contains(tempId)) {
            tempId = new AppointmentId(UUID.randomUUID().toString().substring(0, 7));
        }
        return tempId;
    }

    /// / util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("appointments", appointments)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public List<AppointmentId> getIdList() {
        return this.idList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && appointments.equals(otherAddressBook.appointments)
                && idList.equals(otherAddressBook.idList);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() + appointments.hashCode();
    }
}
