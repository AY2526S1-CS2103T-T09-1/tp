package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDateTimeQuery;
import seedu.address.model.appointment.AppointmentQuery;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonQuery;
import seedu.address.model.person.Phone;
import seedu.address.model.rank.Rank;
import seedu.address.model.rank.RankType;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        }
        /*
         * Even though all fields in {@code FindCommand} are optional,
         * the command should expect at least one field. If the user
         * want to list all clients without filters, please use {@code ListCommand} instead.
         *
         * On the other hand, if the user wants to list all appointments,
         * minimally, the user must specify {@code /appt} keyword. (and left it empty)
         * Example:
         * `find /appt` today will list all appointments today.
         * `find` will list all clients with attached appointments.
         */
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                // Person-related fields
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_TAG, PREFIX_RANK,
                // Appointment-related fields
                PREFIX_APPOINTMENT, PREFIX_TYPE, PREFIX_STATUS);
        // No duplicate fields
        argMultimap.verifyNoDuplicatePrefixesFor(
            // Person-related fields
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_TAG, PREFIX_RANK,
            // Appointment-related fields
            PREFIX_APPOINTMENT, PREFIX_TYPE, PREFIX_STATUS);
        // No trailing preamble
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        }
        boolean isAppointmentPresent = argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()
                                || argMultimap.getValue(PREFIX_TYPE).isPresent()
                                || argMultimap.getValue(PREFIX_STATUS).isPresent();
        if (!isAppointmentPresent) {
            return new FindCommand(getPersonQuery(argMultimap));
        } else {
            return new FindCommand(getPersonQuery(argMultimap), getAppointmentQuery(argMultimap));
        }
    }

    /**
     * Obtain the {@code PersonQuery} instance from the given mappings
     */
    public PersonQuery getPersonQuery(ArgumentMultimap argMultimap) throws ParseException {
        PersonQuery query = PersonQuery.build();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            String trimmedArgs = name.toString().trim();
            String[] nameKeywords = trimmedArgs.split("\\s+");
            query = query.setName(nameKeywords);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            query = query.setPhone(phone);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            query = query.setEmail(email);
        }
        var tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        query.setTags(tags);
        if (argMultimap.getValue(PREFIX_RANK).isPresent()) {
            Rank rank = ParserUtil.parseRank(argMultimap
                .getValue(PREFIX_RANK)
                .orElse(RankType.NONE.toString()));
            query = query.setRank(rank);
        }
        return query;
    }

    /**
     * Obtain the {@code AppointmentQuery} instance from the given mappings
     */
    public AppointmentQuery getAppointmentQuery(ArgumentMultimap argMultimap) throws ParseException {
        AppointmentQuery query = AppointmentQuery.build();
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            AppointmentType type = ParserUtil.parseAppointmentType(
                argMultimap.getValue(PREFIX_TYPE).get());
            query = query.setType(type);
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            AppointmentStatus status = ParserUtil.parseAppointmentStatus(
                argMultimap.getValue(PREFIX_STATUS).get());
            query = query.setStatus(status);
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            AppointmentDateTimeQuery dateTime = ParserUtil.parseAppointmentDateTimeQuery(
                argMultimap.getValue(PREFIX_APPOINTMENT).get());
            query = query.setDateTime(dateTime);
        }
        return query;
    }
}
