package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Superclass for all Link commands
 */
public abstract class LinkAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a new appointment to a client. "
            + "Parameters: "
            + "FLAG "
            + "/n CLIENT_NAME "
            + "[/id ID]"
            + "/appt DATE [TIME] "
            + "[/len MINUTES] "
            + "[/loc LOCATION] "
            + "[/type TYPE] "
            + "[/msg NOTES] "
            + "[/status planned|confirmed|completed|cancelled]\n"
            + "Example: " + COMMAND_WORD + " -c "
            + "/n Alex Wu "
            + "/appt 12-10-2025 1430 "
            + "/len 90 "
            + "/loc Bukit Merah FSC "
            + "/type home-visit "
            + "/msg Bring consent form "
            + "/status planned";

    public static final String MESSAGE_SUCCESS = "New appointment linked to %1$s: %2$s";
    public static final String MESSAGE_NO_SUCH_PERSON = "No client found with the name: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENTS = "This appointment already exists in the address book.";
    public static final String MESSAGE_INVALID_EDIT_SYNTAX = "Invalid Edit syntax. ";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}
