package edu.ntnu.idi.idatt.menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class DiaryEntryHandler {
    private Scanner scanner;
    private AuthorRegister register;

    public DiaryEntryHandler(Scanner scanner, AuthorRegister register) {
        this.scanner = scanner;
        this.register = register;
    }

    public void writeTodaysEntry(String authorName) {
        clearTerminal();
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
                    System.out.print("    Rate this day (1-10): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("    Invalid input! Please enter a number (1-10): ");
                        scanner.next(); // Clear the invalid input
                    }
                    int rating = scanner.nextInt();

                    // Clamp the rating between 1-10
                    if (rating > 10) {
                        rating = 10;
                    } else if (rating < 1) {
                        rating = 1;
                    }
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
                System.out.print("    Rate this day (1-10): ");
                while (!scanner.hasNextInt()) {
                    clearTerminal();
                    System.out.print("    Invalid input! Please enter a number (1-10): ");
                    scanner.next(); // Clear the invalid input
                }
                int rating = scanner.nextInt();

                scanner.nextLine(); // Consume newline
                register.addDay(authorName, LocalDate.now().toString(), content, rating);
                System.out.println("    Entry saved for today!");
                System.out.println("----------------------------------------");
                inWriteTodaysEntry = false;
            }
        }
    }

    public void lookAtExistingDay(String authorName) {
        boolean inLookAtExistingDay = true;
        while (inLookAtExistingDay) {
            clearTerminal();
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
                clearTerminal();
                System.out.println("----------------------------------------");
                System.out.println(choice + "          Rating: " + register.getAuthorByName(authorName).getDayRating(choice));
                System.out.println("----------------------------------------");
                System.out.println(register.getAuthorByName(authorName).readDay(choice) );
                System.out.println("----------------------------------------");
                System.out.println("    Edit: e         Back: b");
                System.out.println("----------------------------------------");
                String eb = scanner.nextLine();
                if (eb.equalsIgnoreCase("e")) {
                    System.out.println("    Type in the new entry for this day: ");
                    System.out.print("    ");
                    String entry = scanner.nextLine();
                    System.out.print("    Rate this day (1-10): ");
                    while (!scanner.hasNextInt()) {
                        clearTerminal();
                        System.out.print("    Invalid input! Please enter a number (1-10): ");
                        scanner.next(); // Clear the invalid input
                    }
                    int rating = scanner.nextInt();

                    scanner.nextLine(); // Consume newline
                    register.editDay(choice, entry, authorName, rating);
                    System.out.println("    Entry updated successfully!");
                    System.out.println("----------------------------------------");
                } else if (eb.equalsIgnoreCase("b")) {
                    return;
                } else {
                    return;
                }
            }
        }
    }

    public void addSpecificDate(String authorName) {
        clearTerminal();
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
        System.out.print("    Rate this day (1-10): ");
        while (!scanner.hasNextInt()) {
            clearTerminal();
            System.out.println("    Invalid input! Please enter a number (1-10): ");
            scanner.next(); // Clear the invalid input
        }
        int rating = scanner.nextInt();

        scanner.nextLine(); // Consume newline
        register.addDay(authorName, date, content, rating);
        System.out.println("    Entry saved for " + date + "!");
        System.out.println("----------------------------------------");
    }

    private void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }
}