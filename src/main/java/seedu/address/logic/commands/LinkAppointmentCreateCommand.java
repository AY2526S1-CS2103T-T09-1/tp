package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Creates an appointment and links it directly to a client (person).
 */
public class LinkAppointmentCreateCommand extends LinkAppointmentCommand {
    public static final String MESSAGE_FAIL =
            "Create flag: Links a new appointment to a client. \nParameters: "
                    + "link -c "
                    + PREFIX_NAME + "NAME "
                    + PREFIX_APPOINTMENT + "DATE TIME "
                    + PREFIX_LENGTH + "MINUTES "
                    + PREFIX_LOCATION + "LOCATION "
                    + "[" + PREFIX_TYPE + "TYPE] "
                    + "[" + PREFIX_MESSAGE + "NOTES] "
                    + "[" + PREFIX_STATUS + "planned|confirmed|completed|cancelled]\n"
                    + "Example: "
                    + MESSAGE_ADD_APPOINTMENT;

    private final Name clientName;
    private Appointment appointment;
    private AppointmentId appointmentId;

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
        this.appointmentId = null;
    }

    public LinkAppointmentCreateCommand setAppointmentId(AppointmentId appointmentId) {
        this.appointmentId = appointmentId;
        return this;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AppointmentId newId = (this.appointmentId == null) ? model.generateId() : this.appointmentId;

        this.appointment = new Appointment(newId, this.appointment.getClientName(), this.appointment.getDateTime(),
                this.appointment.getLength(), this.appointment.getLocation(), this.appointment.getType(),
                this.appointment.getMessage(), this.appointment.getStatus());

        // Attach appointment to the model (global list)
        if (model.hasAppointment(appointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENTS);
        }

        Appointment clashedAppointment = model.getClashedAppointment(appointment);
        if (clashedAppointment != null) {
            throw new CommandException(
                String.format(MESSAGE_CLASH_APPOINTMENTS_CREATE,
                    clashedAppointment.getId(), clashedAppointment.getDateTime(),
                        appointment.getDateTime()));
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
        return otherCommand.clientName.equals(clientName)
                && otherCommand.appointment.isSameAppointment(this.appointment);
    }
}
