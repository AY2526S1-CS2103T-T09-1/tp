package seedu.address.logic.commands;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentLength;
import seedu.address.model.appointment.AppointmentLocation;
import seedu.address.model.appointment.AppointmentMessage;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Edits an appointment and links it directly to a client (person).
 */
public class LinkAppointmentEditCommand extends LinkAppointmentCommand {
    public static final String MESSAGE_DUPLICATE_APPOINTMENTS = "This appointment already exists in the address book.";
    public static final String MESSAGE_SUCCESS = "New appointment linked to %1$s: %2$s";

    private final AppointmentId targetId;
    private final EditAppointmentDescriptor newAppt;

    /**
     * Constructor to create a LinkAppointmentEditCommand
     */
    public LinkAppointmentEditCommand(
            AppointmentId targetId,
            EditAppointmentDescriptor newAppt) {
        this.targetId = targetId;
        this.newAppt = new EditAppointmentDescriptor(newAppt);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ObservableList<Appointment> apptList = model.getFilteredAppointmentList();
        Appointment oldAppt = getAppt(targetId, apptList);
        Appointment editedAppt = createEditedAppt(oldAppt, newAppt);
        Name clientName = oldAppt.getClientName();
        Optional<Person> clientOpt = model.getFilteredPersonList().stream()
                .filter(p -> {
                    assert clientName != null;
                    return p.getName().fullName.equalsIgnoreCase(clientName.fullName);
                })
                .findFirst();

        if (clientOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_PERSON, clientName));
        }

        if (!oldAppt.isSameAppointment(editedAppt) && model.hasAppointment(editedAppt)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENTS);
        }

        Person client = clientOpt.get();
        model.setAppointmentWithPerson(oldAppt, editedAppt, client);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, editedAppt.getClientName().toString(), Messages.format(editedAppt))
        );
    }

    private Appointment getAppt(AppointmentId id, ObservableList<Appointment> apptList) throws CommandException {
        for (Appointment appt : apptList) {
            if (appt.getId().equals(id)) {
                return appt;
            }
        }
        throw new CommandException("Unable to find appointment with ID: " + id);
    }

    private Appointment createEditedAppt(Appointment oldAppt, EditAppointmentDescriptor newAppt) {
        assert oldAppt != null;

        Name clientName = oldAppt.getClientName();
        AppointmentDateTime dateTime = newAppt.getDateTime().orElse(oldAppt.getDateTime());
        AppointmentLength length = newAppt.getLength().orElse(oldAppt.getLength());
        AppointmentLocation location = newAppt.getLocation().orElse(oldAppt.getLocation());
        AppointmentType type = newAppt.getType().orElse(oldAppt.getType());
        AppointmentMessage message = newAppt.getMessage().orElse(oldAppt.getMessage());
        AppointmentStatus status = newAppt.getStatus().orElse(oldAppt.getStatus());

        return new Appointment(targetId, clientName, dateTime, length, location, type, message, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkAppointmentEditCommand)) {
            return false;
        }

        LinkAppointmentEditCommand otherCommand = (LinkAppointmentEditCommand) other;
        return otherCommand.targetId.equals(targetId) && otherCommand.newAppt.equals(newAppt);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private AppointmentId id;
        private AppointmentDateTime dateTime;
        private AppointmentLength length;
        private AppointmentLocation location;
        private AppointmentType type;
        private AppointmentMessage message;
        private AppointmentStatus status;

        public EditAppointmentDescriptor() {}

        /**
         * Constructor for EditAppointmentDescriptor
         * @param toCopy
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setId(toCopy.id);
            setDateTime(toCopy.dateTime);
            setLength(toCopy.length);
            setLocation(toCopy.location);
            setType(toCopy.type);
            setMessage(toCopy.message);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, dateTime, length, location, type, message, status);
        }

        //================ Setters ==================================
        public void setId(AppointmentId id) {
            this.id = id;
        }

        public void setDateTime(AppointmentDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public void setLength(AppointmentLength length) {
            this.length = length;
        }

        public void setType(AppointmentType type) {
            this.type = type;
        }

        public void setMessage(AppointmentMessage message) {
            this.message = message;
        }

        public void setStatus(AppointmentStatus status) {
            this.status = status;
        }

        public void setLocation(AppointmentLocation location) {
            this.location = location;
        }

        //====================== Getters ===================================
        public Optional<AppointmentId> getId() {
            return Optional.ofNullable(id);
        }

        public Optional<AppointmentDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public Optional<AppointmentLength> getLength() {
            return Optional.ofNullable(length);
        }

        public Optional<AppointmentMessage> getMessage() {
            return Optional.ofNullable(message);
        }

        public Optional<AppointmentStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public Optional<AppointmentLocation> getLocation() {
            return Optional.ofNullable(location);
        }

        public Optional<AppointmentType> getType() {
            return Optional.ofNullable(type);
        }
    }
}
