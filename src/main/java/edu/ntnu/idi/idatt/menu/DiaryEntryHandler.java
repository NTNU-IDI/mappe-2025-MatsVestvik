package edu.ntnu.idi.idatt.menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import edu.ntnu.idi.idatt.CreateDay.EnterDay;
import edu.ntnu.idi.idatt.CreateDay.ValidEntry;
import edu.ntnu.idi.idatt.CreateDay.ValidRating;
import edu.ntnu.idi.idatt.CreateDay.ValidTitle;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.TerminalUtils;

public class DiaryEntryHandler {

    /**
     * Handles diary entry creation and management for a specific author.
     * Validates user input and delegates storage operations to the AuthorRegister.
     * Delegates Data storage functions to Author register.
     */

    private Scanner scanner;
    private AuthorRegister register;

    /**
     * Cunstructs DiaryEntryHandler with scanner and register
     * @param scanner
     * @param register
     */
    public DiaryEntryHandler(Scanner scanner, AuthorRegister register) {
        this.scanner = scanner;
        this.register = register;
    }

    /**
     * allows user to write todays entry. Prompt for already existing day
     * allows user to choose to overwrite
     * validation. ensures reating is in 1-10 range
     * 
     * @param authorName
     */

    public void writeTodaysEntry(String authorName) {
        TerminalUtils.clear(); //clear the terminal for clean output
        boolean inWriteTodaysEntry = true; //check for when to exit
        while (inWriteTodaysEntry) {
            //if the day already exists in authors list
            if (register.searchDays(authorName, LocalDate.now())) {
                //prompt user with existing day and options
                System.out.println("    You already have an entry for today.");
                editMenu(authorName, LocalDate.now().toString());
                inWriteTodaysEntry = false;
            } else {
                EnterDay.printValidDay(register, authorName, LocalDate.now().toString());
                inWriteTodaysEntry = false;
            }
        }
    }

    /**
     * Displays all existing days in author days list.
     * after selection allows edit back and deltion.
     * display content and rating for specific day
     * @param authorName
     */

    public void lookAtExistingDay(String authorName) {
        boolean inLookAtExistingDay = true; // check for when to exit
        while (inLookAtExistingDay) {
            TerminalUtils.clear(); //clear the terminal for clean output
            System.out.println("----------------------------------------");
            register.getAuthorByName(authorName).printAll();// print all days date for author
            System.out.println("    e. Exit");
            System.out.println("----------------------------------------");
            System.out.print("    Type in the date of the day you\n");
            System.out.println("    want to look at (choose one from the list above):");
            System.out.print("    ");

            // Collect available dates for the author and check if input it valid
            java.util.List<edu.ntnu.idi.idatt.objects.Day> days = register.getAuthorByName(authorName).getListDays();
            java.util.Set<String> validDates = new java.util.HashSet<>();
            for (edu.ntnu.idi.idatt.objects.Day d : days) {
                validDates.add(d.getDate());
            }

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("e")) {
                inLookAtExistingDay = false; // exit this menu
            } else if (!validDates.contains(choice)) {// check if date is in the list
                System.out.println("    Not a valid date.");
                System.out.println("    Please choose one of the listed dates or 'e' to exit.");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
                continue;
            } else {
                editMenu(authorName, choice);
                inLookAtExistingDay = false;
            }
        }
    }

    /**
     * edit menu allows user to edit day entry rating title
     * delete day or add to entry.
     * @param authorName
     * @param choice
     */
    public void editMenu(String authorName, String choice) {
        boolean inEditMenu = true;
        while (inEditMenu) {
            TerminalUtils.clear(); // clear the terminal for clean output
            //print out all infor for a day with options

            register.getAuthorByName(authorName).getDayByDate(choice).printDay();

            System.out.println("o. OverWrite   r. Edit rating  d. Delete");
            System.out.println("a. Add Entry   t. Edit title   b. Back ");
            System.out.println("----------------------------------------");
            System.out.print("    ");
            String eb = scanner.nextLine();
            //if edit it selected
            if (eb.equalsIgnoreCase("o")) {
                register.getAuthorByName(authorName).getDayByDate(choice).setEntry(
                ValidEntry.promptValidEntry());
            //for back
            } else if (eb.equalsIgnoreCase("b")) {
                inEditMenu = false;
                return;
            //for delete
            } else if (eb.equalsIgnoreCase("d")) {
                register.getAuthorByName(authorName).removeDay(choice);
                inEditMenu = false;
                System.out.println("    Day deleted! Returning...");

            } else if (eb.equalsIgnoreCase("r")) {
                int rating = ValidRating.promptValidRating();
                register.getAuthorByName(authorName).getDayByDate(choice).setRating(rating);
            } else if (eb.equalsIgnoreCase("t")) {
                String title = ValidTitle.promptValidTitle();
                register.getAuthorByName(authorName).getDayByDate(choice).setTitle(title);
            } else if (eb.equalsIgnoreCase("a")) {
                String entry = ValidEntry.promptValidEntry();
                register.getAuthorByName(authorName).getDayByDate(choice).addToEntry(entry);
            } else {
                System.out.println("    Invalid input! Returning...");
            }
        }
         
    }

    /**
     * Allows author to create a day object with any valid date
     * sanitation ensures only real dates.
     * prompts for a date an entry and a rating.
     * @param authorName
     */

    public void addSpecificDate(String authorName) {
        TerminalUtils.clear(); // clear the terminal for clean output.
        System.out.println("----------------------------------------");
        System.out.println("    Enter the date (YYYY-MM-DD): ");
        String date;
        while (true) {
            System.out.print("    ");
            String input = scanner.nextLine().trim();
            // allow user to cancel
            if (input.equalsIgnoreCase("e")) {
                System.out.println("    Cancelled. Returning...");
                System.out.println("----------------------------------------");
                return;
            }
            try {
                // parse to ensure format and validity
                LocalDate.parse(input);
                date = input;
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("    Invalid date format. Please use YYYY-MM-DD or type 'E' to cancel.");
            }
        }
        EnterDay.printValidDay(register, authorName, date);
    }
}