package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("/n");
    public static final Prefix PREFIX_PHONE = new Prefix("/p");
    public static final Prefix PREFIX_EMAIL = new Prefix("/e");
    public static final Prefix PREFIX_ADDRESS = new Prefix("/a");
    public static final Prefix PREFIX_TAG = new Prefix("/t");
    public static final Prefix PREFIX_RANK = new Prefix("/r");

    // Appointment-related
    public static final Prefix PREFIX_APPOINTMENT = new Prefix("/appt");
    public static final Prefix PREFIX_LENGTH = new Prefix("/len");
    public static final Prefix PREFIX_LOCATION = new Prefix("/loc");
    public static final Prefix PREFIX_TYPE = new Prefix("/type");
    public static final Prefix PREFIX_MESSAGE = new Prefix("/msg");
    public static final Prefix PREFIX_STATUS = new Prefix("/status");
    public static final Prefix PREFIX_FLAG = new Prefix("-");
    public static final Prefix PREFIX_ID = new Prefix("/id");
}
