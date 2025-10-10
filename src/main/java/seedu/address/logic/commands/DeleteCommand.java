package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name used in the displayed person list.\n"
            + "Parameters: NAME \n"
            + "Example: " + COMMAND_WORD + " John Doe"
            + "Note: The name is case sensitive.";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Name targetName;

    public DeleteCommand(Name nameToDelete) {
        requireNonNull(nameToDelete);
        this.targetName = nameToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // get person from nameQuery - i.e. get person from lastShownList whose name matches nameToDelete
        List<Person> matchedPersons = lastShownList.stream()
                .filter(person -> person.getName().containsName(targetName))
                .toList();
        if (matchedPersons.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_DOES_NOT_EXIST);
        }

        if (matchedPersons.size() > 1) {
            String[] names = matchedPersons.stream()
                    .map(person -> person.getName().fullName)
                    .toArray(String[]::new);
            throw new CommandException(Messages.MESSAGE_MULTIPLE_PERSONS_FOUND_NAME + String.join(", ", names));
        }

        assert matchedPersons.size() == 1 : "There should be exactly one matched person at this point.";
        Person personToDelete = matchedPersons.get(0);
        logger.info("Deleting person: " + matchedPersons.get(0));
        // Only allow deletions of people who are in the filtered list, i.e. currently shown to the user.
        if (!lastShownList.contains(matchedPersons.get(0))) {
            throw new CommandException(Messages.MESSAGE_PERSON_DOES_NOT_EXIST);
        }
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
