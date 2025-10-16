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

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add /n John Doe /p 98765432 /e johnd@example.com /a John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add /n NAME`, `NAME` is a parameter which can be used as `add /n John Doe`.

* Items in square brackets are optional.<br>
  e.g `/n NAME [/t TAG]` can be used as `/n John Doe /t friend` or as `/n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[/t TAG]…​` can be used as ` ` (i.e. 0 times), `/t friend`, `/t friend /t family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `/n NAME /p PHONE_NUMBER`, `/p PHONE_NUMBER /n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add /n NAME /p PHONE_NUMBER [/e EMAIL] [/a ADDRESS] [/r RANK] [/t TAG]…​`

Acceptable Values: 
* For name: Your input must be alphanumeric. 
* For phone number: Your input should only contain 8 digits starting with 6, 8 or 9. You may choose to include the `+65` country code at the start (not included in the 8 digits). <br> Eg. +65 98765432 or 9876 5432 (both acceptable). 
* For email: Your input should contain a `@` and have alphanumeric characters before and after. Special characters `+ - . _`  are allowed. Don't forget to include the domain!
* For address: Your input must be alphanumeric and only `# - , . ( ) / ; : &` symbols are accepted.
* For tag: Your input must be alphanumeric 
* For ranking priority: You can only input four types of priority, “stable” , “vulnerable” , “urgent” and “crisis” (all case-insensitive).

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add /n John Doe /p 98765432 /e johnd@example.com /a John street, block 123, #01-01 /r stable`
* `add /n Betsy Crowe /t friend /e betsycrowe@example.com /a Newgate Prison /p 91234567 /t criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit OLD_NAME [/n NEW_NAME] [/p PHONE] [/e EMAIL] [/a ADDRESS] [/r RANK] [/t TAG]…​`

* Edits the person at the specified `OLD_NAME`. The old name refers to the person's name before editing.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `/t` without specifying any tags after it.

Acceptable Values:
- Edit phone number: must be 8 digit integers, starting with 6, 8 or 9. Starting with country code (+65) is acceptable.
- Edit email address: must be in `[name]@[email].[domain]` , e.g. `alexyeoh@xyz.io`
- Combinations: Any combination is acceptable

Examples:
*  `edit John Doe /p 91234567 /e johndoe@example.com /rank urgent`  
Edits the phone number and email address of John Doe to be `91234567`, `johndoe@example.com` respectively
and ranks the contact as `urgent`.
*  `edit Betsy Crownerrr /n Betsy Crower /t`  
Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Look up clients by fields: `find`

```
find [/n KEYWORD_1 KEYWORD_2 ...] [/p PHONE] [/e EMAIL] [/a ADDRESS] [/t TAG] [/r RANK]
```
You can use this command to retrieve a list of clients that match the specified attributes:
- Name `/n KEYWORD_1 KEYWORD_2 ...` — lists all clients whose names contain any of the specified keywords.
The search is case-insensitive.
- Phone number `/p PHONE` - lists all clients whose phone numbers exactly match `PHONE`.
- Email `/e EMAIL` - lists all clients whose email exactly match `EMAIL`.
- Address `/r ADDRESS` - lists all clients whose address exactly match `ADDRESS`.
- Tag `/t TAG` - lists all clients whose tag contains `TAG`.
- Address `/r ADDRESS` - lists all clients whose rank exactly match `RANK`.

> [!NOTE]  
> The order of the attributes does not matter.
> If you haven't specified any attributes, the system will list all clients.


Example usage:

You want to list all urgent clients whose name contains "John" or "Doe.
```
find /n John Doe /r urgent
```
Example output (other fields omitted)
```
John Doe [rank: urgent]
Jane Doe [rank: urgent]
John Lock [rank: urgent]
```

You want to list all clients with tag patients and phone number 81234567.
```
find /p 81234567 /t patient
```
Example output (other fields omitted)
```
James Bond [81234567, tag: patient, friend]
John Doe [81234567, tag: patient]
```
### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add /n NAME /p PHONE_NUMBER [/e EMAIL] [/a ADDRESS] [/r RANK] [/t TAG]…​` <br> e.g., `add /n James Ho /p 92248444 /e jamesho@example.com /a 123, Clementi Rd, 1234665 /t friend /t colleague /r stable`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit OLD_NAME [/n NEW_NAME] [/p PHONE_NUMBER] [/e EMAIL] [/a ADDRESS] [/t TAG]…​`<br> e.g.,`edit James Tan /n James Lee /e jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
