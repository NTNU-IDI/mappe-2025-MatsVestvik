package edu.ntnu.idi.idatt.menu;

import java.time.LocalDate;
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
                ----------------------------------------
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
                    System.out.println("    Rate this day (1-10): ");
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
                System.out.println("    Rate this day (1-10): ");
                while (!scanner.hasNextInt()) {
                    clearTerminal();
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
            }
        }
    }

    public void lookAtExistingDay(String authorName) {
        boolean inLookAtExistingDay = true;
        while (inLookAtExistingDay) {
            clearTerminal();
            System.out.println("----------------------------------------");
            register.getAuthorByName(authorName).printAll();
            System.out.println("    E. Exit");
            System.out.println("----------------------------------------");
            System.out.println("    Type in the date of the day you \n    want to look at:");
            System.out.print("    ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("e")) {
                inLookAtExistingDay = false;
            } else {
                clearTerminal();
                System.out.println("----------------------------------------");
                System.out.println("    " + choice + "          Rating: " + register.getAuthorByName(authorName).getDayRating(choice));
                System.out.println(register.getAuthorByName(authorName).readDay(choice) + "\n\n");
                System.out.println("    Edit: e         Back: b");
                System.out.println("----------------------------------------");
                String eb = scanner.nextLine();
                if (eb.equalsIgnoreCase("e")) {
                    System.out.print("    Type in the new entry for this day: ");
                    String entry = scanner.nextLine();
                    System.out.println("    Rate this day (1-10): ");
                    while (!scanner.hasNextInt()) {
                        clearTerminal();
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
        System.out.print("    ");String date = scanner.nextLine();
        System.out.println("    What is on your mind for " + date + ": ");
        System.out.print("    ");
        String content = scanner.nextLine();
        System.out.println("    Rate this day (1-10): ");
        while (!scanner.hasNextInt()) {
            clearTerminal();
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
        register.addDay(authorName, date, content, rating);
        System.out.println("Entry saved for " + date + "!");
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