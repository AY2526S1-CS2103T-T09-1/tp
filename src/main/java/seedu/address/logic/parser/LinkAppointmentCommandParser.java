package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.LinkAppointmentCommand;
import seedu.address.logic.commands.LinkAppointmentCreateCommand;
import seedu.address.logic.commands.LinkAppointmentEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentFlag;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentStatusType;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new LinkAppointmentCommand object.
 */
public class LinkAppointmentCommandParser implements Parser<LinkAppointmentCommand> {

    @Override
    public LinkAppointmentCommand parse(String args) throws ParseException {
        Appointment appointment;
        Name clientName;
        LinkAppointmentEditCommand.EditAppointmentDescriptor editAppointmentDescriptor =
                new LinkAppointmentEditCommand.EditAppointmentDescriptor();

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                PREFIX_FLAG, PREFIX_ID, PREFIX_NAME, PREFIX_APPOINTMENT, PREFIX_LENGTH,
                PREFIX_LOCATION, PREFIX_TYPE, PREFIX_MESSAGE, PREFIX_STATUS);

        if (args.trim().isEmpty() || !argMultimap.getValue(PREFIX_FLAG).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkAppointmentCommand.MESSAGE_USAGE));
        }

        validateCommand(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID, PREFIX_NAME, PREFIX_APPOINTMENT,
            PREFIX_LENGTH, PREFIX_LOCATION, PREFIX_TYPE, PREFIX_MESSAGE, PREFIX_STATUS);

        AppointmentFlag flag = ParserUtil.parseAppointmentFlag(argMultimap.getValue(PREFIX_FLAG).get());

        if (flag.value == 'c') {
            clientName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            appointment = createAppointment(clientName, argMultimap);
            return new LinkAppointmentCreateCommand(clientName, appointment);
        } else if (flag.value == 'e') {
            AppointmentId targetId = ParserUtil.parseAppointmentId(argMultimap.getValue(PREFIX_ID).get());
            setEditAppointDescriptor(editAppointmentDescriptor, argMultimap);
            if (!editAppointmentDescriptor.isAnyFieldEdited()) {
                throw new ParseException(LinkAppointmentCommand.MESSAGE_INVALID_EDIT_SYNTAX);
            }
            return new LinkAppointmentEditCommand(targetId, editAppointmentDescriptor);
        }
        return null;
    }

    public void setEditAppointDescriptor(
            LinkAppointmentEditCommand.EditAppointmentDescriptor editAppointmentDescriptor,
            ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            editAppointmentDescriptor.setDateTime(
                            ParserUtil.parseAppointmentDateTime(argMultimap.getValue(PREFIX_APPOINTMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editAppointmentDescriptor.setType(
                    ParserUtil.parseAppointmentType(argMultimap.getValue(PREFIX_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_LENGTH).isPresent()) {
            editAppointmentDescriptor.setLength(
                    ParserUtil.parseAppointmentLength(argMultimap.getValue(PREFIX_LENGTH).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editAppointmentDescriptor.setLocation(
                    ParserUtil.parseAppointmentLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_MESSAGE).isPresent()) {
            editAppointmentDescriptor.setMessage(
                    ParserUtil.parseAppointmentMessage(argMultimap.getValue(PREFIX_MESSAGE).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editAppointmentDescriptor.setStatus(
                    ParserUtil.parseAppointmentStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
    }

    private void validateCommand(ArgumentMultimap argMultimap) throws ParseException {
        AppointmentFlag flag = ParserUtil.parseAppointmentFlag(argMultimap.getValue(PREFIX_FLAG).get());
        switch (flag.value) {
        case 'c':
            if (!arePrefixesPresent(argMultimap, PREFIX_FLAG, PREFIX_NAME, PREFIX_APPOINTMENT)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        LinkAppointmentCommand.MESSAGE_USAGE));
            }
            break;
        case 'd':
            break;
        case 'e':
            if (!arePrefixesPresent(argMultimap, PREFIX_FLAG, PREFIX_ID)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        "Wrong flag"));
            }
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkAppointmentCommand.MESSAGE_USAGE));
        }
    }

    private Appointment createAppointment(Name clientName, ArgumentMultimap argMultimap) throws ParseException {
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
                argMultimap.getValue(PREFIX_STATUS).orElse(AppointmentStatusType.PLANNED.toString()));

        return new Appointment(clientName, dateTime, length, location, type, message, status);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
