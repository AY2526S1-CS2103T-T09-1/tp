package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_STABLE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_STABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.appointment.AppointmentDateTimeQuery;
import seedu.address.model.appointment.AppointmentQuery;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonQuery;
import seedu.address.model.person.Phone;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_trailingPreamble_failed() {
        String userInput = "trail";
        FindCommand expectedFindCommand = new FindCommand(PersonQuery.build());
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_success() {
        String userInput = "    ";
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    // Parse some field shows success
    @Test
    public void parse_someFields_success() {
        String userInput = EMAIL_DESC_AMY;
        userInput += RANK_DESC_STABLE;
        FindCommand expectedFindCommand =
                new FindCommand(PersonQuery.build()
                        .setEmail(new Email(VALID_EMAIL_AMY))
                        .setRank(new Rank(VALID_RANK_STABLE)));
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_allPersonFields_success() {
        String userInput = NAME_DESC_AMY + " " + VALID_NAME_BOB;
        userInput += PHONE_DESC_AMY;
        userInput += EMAIL_DESC_AMY;
        userInput += TAG_DESC_FRIEND;
        userInput += RANK_DESC_STABLE;
        String trimmedArgs = (VALID_NAME_AMY + " " + VALID_NAME_BOB).trim();
        String[] targetName = trimmedArgs.split("\\s+");
        FindCommand expectedFindCommand =
                new FindCommand(PersonQuery.build().setName(targetName)
                        .setPhone(new Phone(VALID_PHONE_AMY))
                        .setEmail(new Email(VALID_EMAIL_AMY))
                        .setTags(Set.of(new Tag(VALID_TAG_FRIEND)))
                        .setRank(new Rank(VALID_RANK_STABLE)));
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_multiplePersonRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // empty name failed
        userInput = " " + PREFIX_NAME;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);

        // multiple valid fields repeated
        userInput = PHONE_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_TAG, PREFIX_EMAIL));

        // multiple invalid values
        userInput = INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_dateTime_success() {
        String userInput;
        FindCommand expectedFindCommand;
        PersonQuery defaultPersonQuery = PersonQuery.build();
        AppointmentQuery query;
        // today
        userInput = " " + PREFIX_APPOINTMENT + " today";
        query = AppointmentQuery.build().setDateTime(AppointmentDateTimeQuery.today());
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
        // +3 days
        userInput = " " + PREFIX_APPOINTMENT + " +3";
        query = AppointmentQuery.build().setDateTime(AppointmentDateTimeQuery.withinRelativeDays(3));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
        // -3 days
        userInput = " " + PREFIX_APPOINTMENT + " -3";
        query = AppointmentQuery.build().setDateTime(AppointmentDateTimeQuery.withinRelativeDays(-3));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
        // only one day specified
        String dateTimeStr = "12-10-2025 1200";
        userInput = " " + PREFIX_APPOINTMENT + " " + dateTimeStr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        query = AppointmentQuery.build().setDateTime(new AppointmentDateTimeQuery(dateTime));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
        // both days specified
        String dateTimeStart = "12-10-2025 1200";
        String dateTimeEnd = "13-10-2025 1300";
        userInput = " " + PREFIX_APPOINTMENT + " " + dateTimeStr + " to " + dateTimeEnd;
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        query = AppointmentQuery.build().setDateTime(new AppointmentDateTimeQuery(
                LocalDateTime.parse(dateTimeStart, formatter),
                LocalDateTime.parse(dateTimeEnd, formatter)
        ));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_dateTime_failure() {
        String userInput;
        FindCommand expectedFindCommand;
        PersonQuery defaultPersonQuery = PersonQuery.build();
        AppointmentQuery query;
        // invalid keyword
        userInput = " " + PREFIX_APPOINTMENT + " not today";
        query = AppointmentQuery.build().setDateTime(AppointmentDateTimeQuery.today());
        assertParseFailure(parser, userInput, AppointmentDateTimeQuery.MESSAGE_CONSTRAINTS);
        // incorrect date format
        String dateTimeStr = "12-20-2015 1200";
        userInput = " " + PREFIX_APPOINTMENT + " " + dateTimeStr;
        assertParseFailure(parser, userInput, AppointmentDateTimeQuery.MESSAGE_CONSTRAINTS);
        // incorrect keyword
        dateTimeStr = "12-10-2025 1200 too 13-10-2025";
        userInput = " " + PREFIX_APPOINTMENT + " " + dateTimeStr;
        assertParseFailure(parser, userInput, AppointmentDateTimeQuery.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_status_success() {
        String userInput;
        FindCommand expectedFindCommand;
        PersonQuery defaultPersonQuery = PersonQuery.build();
        AppointmentQuery query;
        userInput = " " + PREFIX_STATUS + "planned";
        query = AppointmentQuery.build().setStatus(new AppointmentStatus("planned"));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_type_success() {
        String userInput;
        FindCommand expectedFindCommand;
        PersonQuery defaultPersonQuery = PersonQuery.build();
        AppointmentQuery query;
        userInput = " " + PREFIX_TYPE + "type";
        query = AppointmentQuery.build().setType(new AppointmentType("type"));
        expectedFindCommand = new FindCommand(defaultPersonQuery, query);
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }
}
