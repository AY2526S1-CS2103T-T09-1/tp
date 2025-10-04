package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.LinkAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new LinkAppointmentCommand object.
 */
public class LinkAppointmentCommandParser implements Parser<LinkAppointmentCommand> {

    @Override
    public LinkAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_APPOINTMENT, PREFIX_LENGTH,
                        PREFIX_LOCATION, PREFIX_TYPE, PREFIX_MESSAGE, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_APPOINTMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkAppointmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_APPOINTMENT,
                PREFIX_LENGTH, PREFIX_LOCATION, PREFIX_TYPE, PREFIX_MESSAGE, PREFIX_STATUS);

        Name clientName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        AppointmentDateTime dateTime =
                ParserUtil.parseAppointmentDateTime(argMultimap.getValue(PREFIX_APPOINTMENT).get());

        AppointmentLength length = ParserUtil.parseAppointmentLength(
                argMultimap.getValue(PREFIX_LENGTH).orElse(AppointmentLength.NO_LENGTH));
        AppointmentLocation location = ParserUtil.parseAppointmentLocation(
                argMultimap.getValue(PREFIX_LOCATION).orElse(AppointmentLocation.NO_LOCATION));
        AppointmentType type = ParserUtil.parseAppointmentType(
                argMultimap.getValue(PREFIX_TYPE).orElse(AppointmentType.NO_TYPE));
        AppointmentMessage message = ParserUtil.parseAppointmentMessage(
                argMultimap.getValue(PREFIX_MESSAGE).orElse(AppointmentMessage.NO_MESSAGE));
        AppointmentStatus status = ParserUtil.parseAppointmentStatus(
                argMultimap.getValue(PREFIX_STATUS).orElse(AppointmentStatus.PLANNED));

        Appointment appointment = new Appointment(
                clientName,
                dateTime,
                length,
                location,
                type,
                message,
                status
        );

        return new LinkAppointmentCommand(clientName, appointment);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
