package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.appointment.Appointment;

/** UI component that displays information of an {@code Appointment}. */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentCard.fxml";
    private static final DateTimeFormatter DATE_ONLY = DateTimeFormatter.ofPattern("d-M-uuuu");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("d-M-uuuu HHmm");
    private static final DateTimeFormatter TIME_OUT = DateTimeFormatter.ofPattern("HH:mm");

    public final Appointment appointment;

    @FXML private HBox appointmentCardRoot;
    @FXML private VBox calendarBox;
    @FXML private Label id;
    @FXML private Label month;
    @FXML private Label day;
    @FXML private Label year;
    @FXML private Label time;
    @FXML private VBox detailsBox;
    @FXML private Label status;
    @FXML private Label type;
    @FXML private Label appointmentLocation;
    @FXML private Label length;
    @FXML private Label message;

    /**
     * Represents an appointment card component
     */
    public AppointmentCard(Appointment appointment) {
        super(FXML);
        this.appointment = appointment;

        // Parse date/time
        String raw = appointment.getDateTime().toString().trim();
        LocalDate datePart;
        LocalTime timePart = null;

        if (raw.contains(" ")) {
            LocalDateTime ldt = LocalDateTime.parse(raw, DATE_TIME);
            datePart = ldt.toLocalDate();
            timePart = ldt.toLocalTime();
        } else {
            datePart = LocalDate.parse(raw, DATE_ONLY);
        }

        // Set calendar labels
        month.setText(datePart.getMonth().name().substring(0, 3).toUpperCase());
        day.setText(String.valueOf(datePart.getDayOfMonth()));
        year.setText(String.valueOf(datePart.getYear()));
        time.setText(timePart != null ? TIME_OUT.format(timePart) : "");

        // Set status with styling
        String apptStatus = appointment.getStatus().toString().toLowerCase();
        status.setText(apptStatus);
        status.getStyleClass().addAll("status-pill", "status-" + apptStatus);

        // Set details
        id.setText("ID: " + appointment.getId());
        type.setText("Type: " + appointment.getType());
        appointmentLocation.setText("Location: " + appointment.getLocation());
        length.setText("Duration: " + appointment.getLength());
        message.setText("Message: " + appointment.getMessage());
    }
}
