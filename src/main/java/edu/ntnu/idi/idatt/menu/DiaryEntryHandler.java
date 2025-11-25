package edu.ntnu.idi.idatt.menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
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
        TerminalUtils.clear();
        boolean inWriteTodaysEntry = true;
        while (inWriteTodaysEntry) {
            if (register.searchDays(authorName, LocalDate.now())) {
                System.out.println("""
                -----------------------------------------
                    This day already contains an entry.
                    are you sure you want to overwrite it?
                    press y to continue overwrite
                    press n to go back 
                ----------------------------------------         
                """);
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    System.out.println("----------------------------------------");
                    System.out.println("    What is on your mind today: ");
                    System.out.print("    ");
                    String content = scanner.nextLine();
                    int rating;
                    do {
                        System.out.println("    Please enter a rating (1-10): ");
                        System.out.print("    ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("    Invalid input! Please enter a number (1-10): ");
                            scanner.next(); // Clear the invalid input
                            System.out.print("    ");
                        }
                        rating = scanner.nextInt();
                    } while (rating < 1 || rating > 10);


                    scanner.nextLine(); // Consume newline
                    register.addDay(authorName, LocalDate.now().toString(), content, rating);
                    System.out.println("    Entry saved for today!");
                    System.out.println("----------------------------------------");
                    inWriteTodaysEntry = false;
                } else if (answer.equalsIgnoreCase("n")) {
                    inWriteTodaysEntry = false;
                } else {
                    System.out.println("invalid input returning...");
                    inWriteTodaysEntry = false;
                }
            } else {
                System.out.println("----------------------------------------");
                System.out.println("    What is on your mind today: ");
                System.out.print("    ");
                String content = scanner.nextLine();
                int rating;
                do {
                    System.out.println("    Please enter a rating (1-10): ");
                    System.out.print("    ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("    Invalid input! Please enter a number (1-10): ");
                        scanner.next(); // Clear the invalid input
                        System.out.print("    ");
                    }
                    rating = scanner.nextInt();
                } while (rating < 1 || rating > 10);

                scanner.nextLine(); // Consume newline
                register.addDay(authorName, LocalDate.now().toString(), content, rating);
                System.out.println("    Entry saved for today!");
                System.out.println("----------------------------------------");
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
        boolean inLookAtExistingDay = true;
        while (inLookAtExistingDay) {
            TerminalUtils.clear();
            System.out.println("----------------------------------------");
            register.getAuthorByName(authorName).printAll();
            System.out.println("    e. Exit");
            System.out.println("----------------------------------------");
            System.out.println("    Type in the date of the day you \n    want to look at (choose one from the list above):");
            System.out.print("    ");

            // Collect available dates for the author
            java.util.List<edu.ntnu.idi.idatt.objects.Day> days = register.getAuthorByName(authorName).getListDays();
            java.util.Set<String> validDates = new java.util.HashSet<>();
            for (edu.ntnu.idi.idatt.objects.Day d : days) {
                validDates.add(d.getDate());
            }

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("e")) {
                inLookAtExistingDay = false;
            } else if (!validDates.contains(choice)) {
                System.out.println("    Not a valid date. Please choose one of the listed dates or 'e' to exit.");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
                continue;
            } else {
                TerminalUtils.clear();
                System.out.println("----------------------------------------");
                System.out.println(choice + "          Rating: " + register.getAuthorByName(authorName).getDayRating(choice));
                System.out.println("----------------------------------------");
                System.out.println(register.getAuthorByName(authorName).readDay(choice) );
                System.out.println("----------------------------------------");
                System.out.println("e. Edit         b. back     d. delete");
                System.out.println("----------------------------------------");
                String eb = scanner.nextLine();
                if (eb.equalsIgnoreCase("e")) {
                    System.out.println("    Type in the new entry for this day: ");
                    System.out.print("    ");
                    String entry = scanner.nextLine();
                    int rating;
                    do {
                        System.out.println("    Please enter a rating (1-10): ");
                        System.out.print("    ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("    Invalid input! Please enter a number (1-10): ");
                            scanner.next(); // Clear the invalid input
                            System.out.print("    ");
                        }
                        rating = scanner.nextInt();
                    } while (rating < 1 || rating > 10);

                    scanner.nextLine(); // Consume newline
                    register.editDay(choice, entry, authorName, rating);
                    System.out.println("    Entry updated successfully!");
                    System.out.println("----------------------------------------");
                } else if (eb.equalsIgnoreCase("b")) {
                    return;
                } else if (eb.equalsIgnoreCase("d")) {
                    register.getAuthorByName(authorName).removeDay(choice);
                } else {
                    return;
                }
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
        TerminalUtils.clear();
        System.out.println("----------------------------------------");
        System.out.println("    Enter the date (YYYY-MM-DD): ");
        String date = null;
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
        System.out.println("    What is on your mind for " + date + ": ");
        System.out.print("    ");
        String content = scanner.nextLine();
        int rating;
        do {
            System.out.println("    Please enter a rating (1-10): ");
            System.out.print("    ");
            while (!scanner.hasNextInt()) {
                System.out.println("    Invalid input! Please enter a number (1-10): ");
                scanner.next(); // Clear the invalid input
                System.out.print("    ");
            }
            rating = scanner.nextInt();
        } while (rating < 1 || rating > 10);

        scanner.nextLine(); // Consume newline
        register.addDay(authorName, date, content, rating);
        System.out.println("    Entry saved for " + date + "!");
        System.out.println("----------------------------------------");
    }

    /**
     * clearterminal clears the terminal for a better viewing experience
     * called everytime user enters new menu
     */
    
    // Terminal clearing delegated to TerminalUtils.clear()
}