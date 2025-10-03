package seedu.address.model.person;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

/**
 * Represents a query object to find Person in the address book.
 * Guarantees: Field values are validated.
 * Remark: Details can be null and mutable.
 */
public class PersonQuery {

    // Identity fields
    private Optional<Set<Name>> name;
    private Optional<Phone> phone;
    private Optional<Email> email;

    // Data fields
    private Optional<Address> address;
    private final Optional<Set<Tag>> tags = Optional.of(new HashSet<>());
    private Optional<Rank> rank;

    /**
     * Represents empty query
     */
    private PersonQuery() {
        this(null, null, null, null, new HashSet<>(), null);
    }

    /**
     * Building person query at once
     */
    public PersonQuery(String[] args, Phone phone, Email email, Address address, Set<Tag> tags, Rank rank) {
        setName(args);
        this.phone = Optional.ofNullable(phone);
        this.email = Optional.ofNullable(email);
        this.address = Optional.ofNullable(address);
        this.tags.ifPresent(existingTags -> existingTags.addAll(tags));
        this.rank = Optional.ofNullable(rank);
    }

    /**
     * A default factory method to create PersonQuery object
     */
    public static PersonQuery build() {
        return new PersonQuery();
    }

    /**
     * @param args array of string of names to be filtered
     * @return reference to itself (for chaining)
     */
    public PersonQuery setName(String[] args) {
        if (args == null) {
            this.name = Optional.empty();
        } else {
            Set<Name> nameSet = Arrays.stream(args)
                    .map(s -> s.trim().toLowerCase())
                    .map(Name::new)
                    .collect(Collectors.toSet());
            this.name = Optional.ofNullable(nameSet);
        }
        return this;
    }

    /**
     * @param args array of {@code Name} to be filtered
     * @return reference to itself (for chaining)
     */
    public PersonQuery setName(Name[] args) {
        if (args == null) {
            this.name = Optional.empty();
        } else {
            Set<Name> nameSet = Arrays.stream(args)
                    .map(name -> new Name(name.toString().toLowerCase()))
                    .collect(Collectors.toSet());
            this.name = Optional.ofNullable(nameSet);
        }
        return this;
    }

    public PersonQuery setPhone(Phone phone) {
        this.phone = Optional.of(phone);
        return this;
    }

    public PersonQuery setEmail(Email email) {
        this.email = Optional.of(email);
        return this;
    }

    public PersonQuery setAddress(Address address) {
        this.address = Optional.of(address);
        return this;
    }

    public PersonQuery setRank(Rank rank) {
        this.rank = Optional.of(rank);
        return this;
    }

    public PersonQuery setTags(Set<Tag> tags) {
        this.tags.ifPresent(existingTags -> existingTags.addAll(tags));
        return this;
    }

    /**
     * A predicate function of type {@code Predicate<Person>} to filter persons in a collection (such as lists).
     * This function checks whether {@code Person} object follows the query given in {@code PersonQuery} object.
     * {@code Person} must satisfy all fields (e.g. {@code this.name}, {#code this.email}, ...)
     * in order for this function to return true.
     * <br/>
     * If {@code this.[FIELD]} is Nullable, {@code isCorrect[FIELD]} must always return true.
     *
     * @param person {@code Person} object to check
     * @return true if the person corresponds to the given query addressed above.
     */
    public boolean filter(Person person) {

        boolean isCorrectName = this.name.filter(name -> {
            Set<Name> nameKeyword = Arrays.stream(person.getName().toString().trim().split("\\s+"))
                    .map(s -> s.trim().toLowerCase())
                    .map(Name::new)
                    .collect(Collectors.toSet());
            return Collections.disjoint(name, nameKeyword);
        }).isEmpty();
        // TODO: Relax the condition for phone number, address, and email
        // TODO: Decouple the logic
        boolean isCorrectPhone = this.phone.filter(phone -> !person.getPhone().equals(phone)).isEmpty();
        boolean isCorrectAddress = this.address.filter(address -> !person.getAddress().equals(address)).isEmpty();
        boolean isCorrectEmail = this.email.filter(email -> !person.getEmail().equals(email)).isEmpty();
        boolean isCorrectRank = this.rank.filter(rank -> !person.getRank().equals(rank)).isEmpty();
        boolean isCorrectTags = this.tags.filter(tags -> !person.getTags().containsAll(tags)).isEmpty();
        return isCorrectName && isCorrectPhone && isCorrectAddress && isCorrectEmail && isCorrectRank
                && isCorrectTags;
    }

    /**
     * Chain the string with {@code ToStringBuilder} object
     */
    public String toString(ToStringBuilder builder) {
        this.name.ifPresent(n -> builder.add("name", n));
        this.phone.ifPresent(p -> builder.add("phone", p));
        this.email.ifPresent(e -> builder.add("email", e));
        this.address.ifPresent(a -> builder.add("address", a));
        this.tags.ifPresent(t -> builder.add("tags", t));
        this.rank.ifPresent(r -> builder.add("rank", r));
        return builder.toString();
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
        if (!(other instanceof PersonQuery)) {
            return false;
        }

        PersonQuery otherPerson = (PersonQuery) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && rank.equals(otherPerson.rank);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, rank);
    }
}
