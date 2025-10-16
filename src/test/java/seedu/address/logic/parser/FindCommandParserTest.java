package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_STABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.PersonQuery;
import seedu.address.model.person.Phone;
import seedu.address.model.rank.Rank;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_success() {
        String userInput = "    ";
        FindCommand expectedFindCommand = new FindCommand(PersonQuery.build());
        assertParseSuccess(parser, userInput, expectedFindCommand);
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

    // Parse all fields shows success
    @Test
    public void parse_allFields_success() {
        String userInput = NAME_DESC_AMY + " " + VALID_NAME_BOB;
        userInput += PHONE_DESC_AMY;
        userInput += ADDRESS_DESC_AMY;
        userInput += EMAIL_DESC_AMY;
        userInput += TAG_DESC_FRIEND;
        userInput += RANK_DESC_STABLE;
        String trimmedArgs = (VALID_NAME_AMY + " " + VALID_NAME_BOB).trim();
        String[] targetName = trimmedArgs.split("\\s+");
        FindCommand expectedFindCommand =
                new FindCommand(PersonQuery.build().setName(targetName)
                        .setPhone(new Phone(VALID_PHONE_AMY))
                        .setAddress(new Address(VALID_ADDRESS_AMY))
                        .setEmail(new Email(VALID_EMAIL_AMY))
                        .setTags(Set.of(new Tag(VALID_TAG_FRIEND)))
                        .setRank(new Rank(VALID_RANK_STABLE)));
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }
}
