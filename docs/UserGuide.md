---
layout: page
title: User Guide
---

HeartLink is a **desktop app for managing contact details for Social Workers in Singapore. It is optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HeartLink can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
   > **How to check if you have Java `17` or above?**<br>
    Click on start and search for Command Prompt/Terminal. Type `java -version` and click enter. You will see the 
    java version installed on your computer. 

    > **Don't have Java 17?** <br>
    Install it [here](https://www.openlogic.com/openjdk-downloads)!

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T09-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for HeartLink.

4. Run HeartLink using the `java -jar HeartLink.jar` command.<br>
   > **How to run HeartLink?** (for beginners) <br>
   > 1. Open start and search for Command Prompt or Terminal.
   > 2. Type `cd [filename]` to navigate to the folder with your HeartLink jar. <br>
   >  E.g. If your jar is in `Users\(name)\Downloads` and you are currently
   >  in `Users\(name)`, you will type `cd Downloads` in the terminal)
   > 3. Type `java -jar HeartLink.jar` and HeartLink will open!

    A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
    ![Ui](images/Ui.png)
    

5. Try typing some command in the command box and press Enter to execute it. <br> E.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete John Doe` : Deletes `John Doe` from the list of contacts.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for more features with its details.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines 
  as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<br><br>
### List of commands:

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the acceptable inputs:**<br>

* **n/NAME**: This specifies the name of the client. Your input must be alphanumeric. <br> 
  E.g. `John Doe`  
* **p/PHONE_NUMBER**: This specifies the phone number of the client. Your input should only contain 8 digits starting with 6, 8 or 9. You may choose to include the `+65` country code at the start (not included in the 8 digits). <br>
  E.g. `+6598765432` or `98765432`.
* **e/EMAIL**: This specifies the email address of the client. Your input must be in this format `[LOCAL]@[DOMAIN].[TOP-LEVEL DOMAIN]`.
  The local and domain parts should be alphanumeric characters. <br> 
  E.g. `johndoe@example.com`
* **a/ADDRESS**: This specifies the address of the client. Your input must be alphanumeric. Special characters like `# - , . ( ) / ; : &` are accepted. <br>
  E.g. `John street, block 123, #01-01`
* **t/TAG**: This specifies the tag(s) of the client. Your input must be alphanumeric. <br>
  E.g. `friend` or `patient`
* **r/RANK**: This specifies the priority rank of the client. You can only input four types of priority, `stable` , `vulnerable` , `urgent` and `crisis` (all case-insensitive). <br>

</div>

### 1. Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### 2. Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [r/RANK] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/stable`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/91234567 t/criminal`

### 3. Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### 4. Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit OLD_NAME [n/NEW_NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/RANK] [t/TAG]…​`

* Edits the person at the specified `OLD_NAME`. The old name refers to the person's name before editing.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/ ` without specifying any tags after it.
* You can use any combinations of the fields.

Examples:
*  `edit John Doe p/91234567 e/johndoe@example.com r/urgent`  
Edits the phone number and email address of John Doe to be `91234567`, `johndoe@example.com` respectively
and ranks the contact as `urgent`.
*  `edit Betsy Crownerrr n/Betsy Crower t/`  
Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### 5. Look up clients by fields: `find`

```
find [n/KEYWORD_1 KEYWORD_2 ...] [p/PHONE] [e/EMAIL] [t/TAG] [r/RANK]
```
You can use this command to retrieve a list of clients that match the specified attributes:
- Name `n/KEYWORD_1 KEYWORD_2 ...` — lists all clients whose names contain any of the specified keywords.
The search is case-insensitive.
- Phone number `p/PHONE` - lists all clients whose phone numbers exactly match `PHONE`.
- Email `e/EMAIL` - lists all clients whose email exactly match `EMAIL`.
- Tag `t/TAG` - lists all clients whose tag contains `TAG`.
- Rank `r/RANK` - lists all clients whose rank exactly match `RANK`.

<div markdown="span" class="alert alert-info">:exclamation: **Remarks:**
The order of the attributes does not matter. If you haven't specified any attributes, the system will list all clients.
</div>

Example usage:

You want to list all urgent clients whose name contains "John" or "Doe.
```
find n/John Doe r/urgent
```

You want to list all clients with tag patients and phone number 81234567.
```
find p/81234567 t/patient
```

### 6. Managing Appointments with Clients : `link`

Adds, edits, or deletes appointments linked to clients.  
These commands allows social workers to record, track, and manage client appointments efficiently.

Here's the fields and their acceptable inputs relating to appointment:
* **n/NAME**: This specifies the client the appointment is linking to. Your input must be alphanumeric and should be an existing name in contact list.
* **appt/DATE [TIME]**: This specifies the date and time of the appointment. Your input must be a valid calendar date in the format `dd-MM-yyyy [HHmm]` or `d-MM-yyyy [HHmm]` where `HHmm` is optional in 24-hour time format
  <br> E.g. `04-07-2025` or `04-07-2025 0930`
* **id/APPOINTMENT_ID**: This is an unique ID of the appointment. You do NOT need to create an ID. It will be generated once the appointment is created.
* **len/MINUTES**: This specifies the length of appointment. Your input must be a positive integer, in **minutes** <br> E.g. `45`
* **loc/LOCATION**: This specifies the location of the appointment. Your input can contain letters, numbers, and symbols `, . # - / ( ) ' ; & :`. <br> E.g. `Blk 10 Tampines Ave 3 #02-15`
* **type/TYPE**: This specifies the type of the appointment. Any input is acceptable. <br> E.g. `House Visit`, `Follow-up Call`, `Counselling Session`.
* **msg/MESSAGE**: This specifies any note or reminder for the appointment. Any input is acceptable <br> E.g. `Bring consent form and medical report`.
* **status/STATUS**: This specifies the status of the appointment. You can only input four types of status, `planned` , `confirmed` , `completed` and `cancelled` (all case-insensitive).
  It is given the `planned` status by default.

#### i. Creating an appointment `link -c`

Creates a new appointment and links it to a client.
Each appointment automatically receives a unique `Appointment ID`.

**Format:** `link -c n/NAME appt/DATE [TIME] [len/MINUTES] [loc/LOCATION] [type/TYPE] [msg/MESSAGE] [status/STATUS]`

<div markdown="span" class="alert alert-info">:exclamation: **Remarks:**
- Both date and time can be specified, or just the date for all-day appointments.
- A random unique `Appointment ID` is generated automatically.
- All unspecified optional fields default to empty values or `"planned"` status.
- Note that fields in `[]` are optional!
</div>

**Examples:**
```
link -c n/Alex appt/15-12-2025 2359 len/60 loc/Alex House type/House Visit msg/Bring Consent Form status/confirmed
```
Creates a **House Visit** appointment with Alex on **15 Dec 2025, 11:59PM**, lasting **60 minutes**, with a message **“Bring Consent Form”**, marked as **confirmed**.

```
link -c n/Ben appt/10-01-2026
```
Creates an appointment with Ben on **10 Jan 2026** with unspecified duration, location, type, message and status set to **planned** by default.

#### ii. Editing an Appointment : `link -e`

Edits details of an existing appointment using its `Appointment ID`.

**Format:** `link -e id/APPOINTMENT_ID [appt/DATE [TIME]] [len/MINUTES] [loc/LOCATION] [type/TYPE] [msg/MESSAGE] [status/STATUS]`

<div markdown="span" class="alert alert-info">:exclamation: **Remarks:**
- You can edit multiple fields at once or just one.
- Only specified fields are updated, unspecified fields remain unchanged.
- The appointment ID (`id/`) can be seen from the client card display.
- Note that fields in `[]` are optional!
</div>

**Examples:**
```
link -e id/107f3db status/completed
```
Marks appointment with id `107f3db` as **completed**.

```
link -e id/1b9a395 appt/18-12-2025 1500 loc/Office msg/Rescheduled meeting
```
Edits appointment `1b9a395` to reschedule the date and time to **18 Dec 2025, 3:00PM**, with the new location **Office** and message **“Rescheduled meeting”**.

#### iii. Deleting an Appointment : `link -d`

Deletes an existing appointment from a client’s record.

**Format:** `link -d id/APPOINTMENT_ID`

<div markdown="span" class="alert alert-info">:exclamation: **Remarks:**
- Deleting an appointment cannot be undone.
- Once deleted, it will be removed from both the client’s appointment list and the database.
</div>

**Examples:**
```link -d id/1b9a395```
Deletes the appointment with ID `1b9a395`.

### 7. Look up appointments by fields `find`

```
find [appt/DATE [TIME]] [status/STATUS] [type/TYPE]
```

You can use this command to retrieve a list of appointments
that match the specified attributes.

#### i. Appointment meeting time `DATE [TIME]`

list all appointments that  overlap with the specified time. 
Here are the example usages.

List all appointments between 24th October 2025 10 - 11 am.
```
find appt/24-10-2025 1000 to 24-10-2025 1100
```
List all appointments on 4th July 2025.
```
find appt/04-07-2025
```
List today's appointment.
```
find appt/today
```

List all appointments in the upcoming three days.
```
find appt/+3
```

#### ii. Appointment status `STATUS`
List all appointments with the given status. For example,
you want to list all cancelled appointments, you can type
```
find status/cancelled
```

#### iii. Appointment type `TYPE`
List all appointments with the given type. For example,
you want to list all meetings with GIC. You can type
```
find type/GIC-Meeting
```

#### iv. Chaining commands
It is possible to chain these fields with [client fields](#head1234).
For example, if you want to find today's appointment for urgent clients, you can type
```
find r/urgent appt/today 
```

> Although the leading command to list appointments and clients is the same, the behavior differs depending on the fields provided.
If you include any appointment-related fields, such as `appt/`, `status/`, or `type/`, the system will list appointments.
However, providing client-related fields alone only trigger a client listing view instead.
For example,
> - `find r/urgent` will show list of all clients with their respective appointments.
> - `find r/urgent appt/today` will only show list of appointments.
>
> The order of the attributes does not matter. If you haven't specified any attributes, the system will list all clients.

### 8. Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### 9. Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### 10. Exiting the program : `exit`

Exits the program.

Format: `exit`

<br><br>
### Other features:

### Saving the data

HeartLink data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HeartLink data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. 

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If you are familiar with JSON, you are welcome to update data directly by editing that data file! <br>
However, do note that if the changes make the format invalid(e.g. values out of range, missing field), HeartLink will discard all data and start with an empty data file at the next run. 
Hence, it is recommended to take a backup of the file before editing it.<br>
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Follow the steps below:
1. Install the app in the other computer, say computer B.
2. On both computers, navigate to the folder with HeartLink. 
3. Navigate to a folder named `data` and you will see a file called `addressbook.json` <br> 
(If you don't see it in computer B, you can either run HeartLink once or create the file manually)
4. Copy the contents of `addressbook.json` file from computer A (where your data is) to computer B.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                 | Format, Examples                                                                                                                                                                                                     |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Client**         | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [r/RANK] [t/TAG]…​`<br>e.g. `add n/James Ho p/92248444 e/jamesho@example.com a/123 Clementi Rd t/friend r/stable`                                                   |
| **Edit Client**        | `edit OLD_NAME [n/NEW_NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RANK] [t/TAG]…​`<br>e.g. `edit John Doe p/91234567 e/johndoe@example.com r/urgent`                                                             |
| **Delete Client**      | `delete INDEX`<br>e.g. `delete 3`                                                                                                                                                                                    |
| **List Client**        | `list`                                                                                                                                                                                                               |
| **Find Clients**       | `find [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG] [r/RANK]`<br>e.g. `find n/Alex r/urgent`                                                                                                                                  |
| **Find Appointments**  | `find [appt/TIME] [status/STATUS] [type/TYPE]`<br>e.g. `find appt/today`                                                                                                                                             |
| **Edit Appointment**   | `link -e id/APPOINTMENT_ID [appt/DATE [TIME]] [len/MINUTES] [loc/LOCATION] [type/TYPE] [msg/MESSAGE] [status/STATUS]`<br>e.g. `link -e id/107f3db type/Friendly Chat loc/cafe msg/Bring gift`                        |
| **Delete Appointment** | `link -d id/APPOINTMENT_ID`<br>e.g. `link -d id/1b9a395`                                                                                                                                                             |
| **Add Appointment**    | `link -c n/NAME appt/DATE [TIME] [len/MINUTES] [loc/LOCATION] [type/TYPE] [msg/MESSAGE] [status/STATUS]`<br>e.g. `link -c n/Alex appt/15-12-2025 2359 type/House Visit loc/Alex House len/60 msg/Bring Consent Form` |
| **Clear All Entries**  | `clear`                                                                                                                                                                                                              |
| **Help**               | `help`                                                                                                                                                                                                               |
| **Exit Program**       | `exit`                                                                                                                                                                                                               |
