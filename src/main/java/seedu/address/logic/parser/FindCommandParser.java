package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
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
        /*
         * Even though all fields in {@code FindCommand} are optional,
         * the command should expect at least one field. If the user
         * want to list all clients without filters, please use {@code ListCommand} instead.
        */
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_RANK);
        // No duplicate fields
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_RANK);
        // No trailing preamble
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        PersonQuery query = PersonQuery.build();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            String trimmedArgs = name.toString().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
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
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            query = query.setAddress(address);
        }
        var tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        query.setTags(tags);
        if (argMultimap.getValue(PREFIX_RANK).isPresent()) {
            Rank rank = ParserUtil.parseRank(argMultimap
                    .getValue(PREFIX_RANK)
                    .orElse(RankType.NONE.toString()));
            query = query.setRank(rank);
        }
        return new FindCommand(query);
    }
}
