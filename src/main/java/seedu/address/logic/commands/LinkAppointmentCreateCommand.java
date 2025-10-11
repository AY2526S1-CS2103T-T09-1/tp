package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Creates an appointment and links it directly to a client (person).
 */
public class LinkAppointmentCreateCommand extends LinkAppointmentCommand {
    private final Name clientName;
    private Appointment appointment;

    /**
     * Constructs a LinkAppointmentCommand to link client with the specified appointment
     * The relationship between clientName and appointment is one to many.
     */
    public LinkAppointmentCreateCommand(
            Name clientName,
            Appointment appointment) {
        requireNonNull(clientName);
        requireNonNull(appointment);
        this.clientName = clientName;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Attach appointment to the model (global list)
        if (model.hasAppointment(appointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENTS);
        }

        // Find the client in the address book by name
        Optional<Person> clientOpt = model.getFilteredPersonList().stream()
                .filter(p -> {
                    assert clientName != null;
                    return p.getName().fullName.equalsIgnoreCase(clientName.fullName);
                })
                .findFirst();

        if (clientOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_PERSON, clientName));
        }

        Person client = clientOpt.get();
        model.addAppointmentWithPerson(appointment, client);

        return new CommandResult(String.format(MESSAGE_SUCCESS, clientName, Messages.format(appointment)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientName", clientName)
                .add("appointment", appointment)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkAppointmentCreateCommand)) {
            return false;
        }

        LinkAppointmentCreateCommand otherCommand = (LinkAppointmentCreateCommand) other;
        return otherCommand.clientName.equals(clientName) && otherCommand.appointment.equals(appointment);
    }
}
