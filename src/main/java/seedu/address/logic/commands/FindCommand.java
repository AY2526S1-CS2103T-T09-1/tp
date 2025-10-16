package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonQuery;

/**
 * Lists all clients from the address book based on {@code PersonQuery}
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons whose fields"
        + " contain any of the given keywords.\n"
        + "Parameters: "
        + "[" + PREFIX_NAME + " NAME] "
        + "[" + PREFIX_PHONE + " PHONE] "
        + "[" + PREFIX_EMAIL + " EMAIL] "
        + "[" + PREFIX_ADDRESS + " ADDRESS] "
        + "[" + PREFIX_TAG + " TAG]... "
        + "[" + PREFIX_RANK + " RANK]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PHONE + " 91234567 "
        + PREFIX_EMAIL + " johndoe@example.com";

    private final PersonQuery query;

    /**
     * @param query details for filtering clients
     */
    public FindCommand(PersonQuery query) {
        requireNonNull(query);
        this.query = query;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(query::filter);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return query.equals(otherFindCommand.query);
    }

    @Override
    public String toString() {
        return query.toString(new ToStringBuilder(this));
    }
}
