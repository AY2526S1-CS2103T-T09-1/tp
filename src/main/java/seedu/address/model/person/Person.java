package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Rank rank;
    private final List<Appointment> appointments;

    /**
     * Creates a Person with no appointments.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Rank rank) {
        this(name, phone, email, address, tags, rank, Collections.emptyList());
    }

    /**
     * Creates a Person with the given appointments.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, Rank rank, List<Appointment> appointments) {
        requireAllNonNull(name, phone, email, address, tags, rank, appointments);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.rank = rank;
        this.appointments = new ArrayList<>(appointments);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable list of this person's appointments.
     */
    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    /**
     * Returns a new Person with the given appointment added.
     */
    public Person withAddedAppointment(Appointment appointment) {
        List<Appointment> updatedAppointments = new ArrayList<>(appointments);
        updatedAppointments.add(appointment);
        return new Person(name, phone, email, address, tags, rank, updatedAppointments);
    }

    /**
     * Returns a new Person with the given appointment removed.
     */
    public Person withRemovedAppointment(Appointment appointment) {
        List<Appointment> updatedAppointments = new ArrayList<>(appointments);
        updatedAppointments.remove(appointment);
        return new Person(name, phone, email, address, tags, rank, updatedAppointments);
    }

    /**
     * Returns a new Person with new Appointment updated to an old appointment
     */
    public Person withUpdatedAppointment(Appointment oldAppt, Appointment newAppt) {
        List<Appointment> updatedAppointments = new ArrayList<>(appointments);
        updatedAppointments = updatedAppointments.stream()
                .map(x -> x.equals(oldAppt) ? newAppt : x)
                .toList();
        return new Person(name, phone, email, address, tags, rank, updatedAppointments);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && rank.equals(otherPerson.rank)
                && appointments.equals(otherPerson.appointments);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, rank, appointments);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("rank", rank)
                .add("appointments", appointments)
                .toString();
    }

}
