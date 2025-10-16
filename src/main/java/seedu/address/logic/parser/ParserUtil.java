package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentFlag;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Rank}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Rank} is invalid.
     */
    public static Rank parseRank(String rank) throws ParseException {
        requireNonNull(rank);
        String trimmedRank = rank.trim();
        if (!Rank.isValidRankName(trimmedRank)) {
            throw new ParseException(Rank.MESSAGE_CONSTRAINTS);
        }
        return new Rank(trimmedRank);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String dateTime} into an {@code AppointmentDateTime}.
     * Required field.
     */
    public static AppointmentDateTime parseAppointmentDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmed = dateTime.trim();
        if (!AppointmentDateTime.isValidDateTime(trimmed)) {
            throw new ParseException(AppointmentDateTime.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentDateTime(trimmed);
    }

    /**
     * Parses a {@code String length} into an {@code AppointmentLength}.
     * Optional; empty string means unspecified.
     */
    public static AppointmentLength parseAppointmentLength(String length) throws ParseException {
        if (length == null || length.trim().isEmpty()) {
            return new AppointmentLength(AppointmentLength.NO_LENGTH);
        }
        String trimmed = length.trim();
        if (!AppointmentLength.isValidLength(trimmed)) {
            throw new ParseException(AppointmentLength.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentLength(trimmed);
    }

    /**
     * Parses a {@code String location} into an {@code AppointmentLocation}.
     * Optional; empty string means no location.
     */
    public static AppointmentLocation parseAppointmentLocation(String location) throws ParseException {
        if (location == null || location.trim().isEmpty()) {
            return new AppointmentLocation(AppointmentLocation.NO_LOCATION);
        }
        String trimmed = location.trim();
        if (!AppointmentLocation.isValidLocation(trimmed)) {
            throw new ParseException(AppointmentLocation.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentLocation(trimmed);
    }

    /**
     * Parses a {@code String type} into an {@code AppointmentType}.
     * Optional; empty string means no type.
     */
    public static AppointmentType parseAppointmentType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return new AppointmentType(AppointmentType.NO_TYPE);
        }
        return new AppointmentType(type.trim());
    }

    /**
     * Parses a {@code String note} into an {@code AppointmentNote}.
     * Optional; empty string means no note.
     */
    public static AppointmentMessage parseAppointmentMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return new AppointmentMessage(AppointmentMessage.NO_MESSAGE);
        }
        return new AppointmentMessage(message.trim());
    }

    /**
     * Parses a {@code String status} into an {@code AppointmentStatus}.
     * Optional; empty string defaults to "PLANNED".
     */
    public static AppointmentStatus parseAppointmentStatus(String status) throws ParseException {
        if (status == null || status.trim().isEmpty()) {
            return new AppointmentStatus("planned");
        }
        String trimmed = status.trim().toLowerCase();
        if (!AppointmentStatus.isValidStatus(trimmed)) {
            throw new ParseException(AppointmentStatus.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentStatus(trimmed);
    }

    /**
     * Parses a {@code String flag} into an {@code AppointmentFlag}.
     * @param flag String to parse
     * @return An appointment flag
     * @throws ParseException
     */
    public static AppointmentFlag parseAppointmentFlag(String flag) throws ParseException {
        if (flag == null || flag.trim().isEmpty() || flag.length() > 1
                || !AppointmentFlag.isValidFlag(flag.charAt(0))) {
            throw new ParseException("Invalid Flag!");
        }

        char charFlag = flag.toLowerCase().charAt(0);

        return new AppointmentFlag(charFlag);
    }

    /**
     * Parses a {@code String flag} into an {@code AppointmentId}.
     * @param id String to parse
     * @return An appointment ID
     * @throws ParseException
     */
    public static AppointmentId parseAppointmentId(String id) throws ParseException {
        if (id.trim().isEmpty() || id == null) {
            throw new ParseException("Invalid ID!");
        }

        return new AppointmentId(id);
    }
}
