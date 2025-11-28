package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.service.Save;
import edu.ntnu.idi.idatt.util.TerminalUtils;

public class LoginHandler {

    /**
     * handles login screen where user can choose account or create
     * also handles admin operations. with extra functionality
     * also handles new author creation.
     */

    Scanner scanner;
    private boolean running = true; // check if the system should continue in loop
    AuthorRegister register;
    private String authorName;

    /**
     * creates the loginhandler with specified scanner and register.
     * @param scanner
     * @param register
     */

    public LoginHandler(Scanner scanner, AuthorRegister register) {
        this.scanner = scanner;
        this.register = register;
    }

    /**
     * shows login option.
     * display all authors numbered that are currently in the system for easy selection
     * display create new user option, admin and save and exit.
     */

    public void login() {
        boolean isInLogin = true;
        while (isInLogin && running) { //check if the program should exit
            TerminalUtils.clear(); //clear the terminal for clean output
            System.out.println("----------------------------------------");
            System.out.println("    Choose a user:");
            register.printAllAuthors();
            System.out.println("----------------------------------------");
            //print other options with correct numbering
            System.out.println("    "+(register.getAuthors().size() + 1)+". Create new user");
            System.out.println("    " + (register.getAuthors().size() + 2)+". Admin");
            System.out.println("    " + (register.getAuthors().size() + 3) + ". Save and Exit");
            System.out.print("----------------------------------------\n    ");
            //ensure that input is an int
            while (!scanner.hasNextInt()) {
                System.out.println("    Invalid input! Enter a valid number: ");
                scanner.next(); // Clear the invalid input
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            //if choice was an author take them to personal loginscreen
            if (choice > 0 && choice < register.getAuthors().size() + 1) {
                String authorName = register.getAuthorName(choice - 1);
                this.authorName = authorName;
                setAuthorName(authorName);
                boolean exit = loginHandling();
                if (exit) {
                    exit();
                    return;
                }
            //check if selection is non author
            } else if (choice == register.getAuthors().size() + 1) {
                createNewUser();
            } else if(choice == register.getAuthors().size() + 2){
                extras();
            }else if (choice == register.getAuthors().size() + 3) {
                TerminalUtils.clear();
                exit();
            } else {
                System.out.println("Not a valid input");
            }
        }
    }

    /**
     * handles password verification. prompts for an input and uses chackpin to verify
     * 
     * @return
     */

    public boolean loginHandling() {
        TerminalUtils.clear(); // clear the terminal. clean
        System.out.println("----------------------------------------");
        System.out.println("    You have selected " + authorName);
        System.out.print("    Please enter your pin: ");
        
        //ensure input is int
        while (!scanner.hasNextInt()) {
            TerminalUtils.clear();
            System.out.println("    Invalid input! Please enter a pin (0000-9999): ");
            scanner.next(); // Clear the invalid input
        }
        int ePin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        //check if pin match
        if (register.getAuthorByName(authorName).checkPin(ePin)) {
            UserMenuHandler userMenuHandler = new UserMenuHandler(scanner, register, this);
            return userMenuHandler.showUserMenu(authorName);
        } else {
            System.out.println("    entered pin was incorrect. Please try again");
        }
        return false;
    }

    /**
     * function that can be called from other classes to ensure the author name is updated
     * @param authorName
     */

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    /**
     * prompts user for name and pin. verifies there is a name and and valid pin
     */

    public void createNewUser() {
        TerminalUtils.clear(); // clear terminal, clean output
        System.out.println("----------------------------------------");
        System.out.print("    Please enter your name: ");
        String name = scanner.nextLine().trim();

        // Validate that name is not empty
        while (name.isEmpty()) {
            System.out.println("    Error: Name cannot be empty!");
            System.out.print("    Please enter your name: ");
            name = scanner.nextLine().trim();
        }

        int pin = 0;
        boolean validPin = false;

        while (!validPin) {
            System.out.print("    Please enter a 4-digit pin: ");
            String pinInput = scanner.nextLine().trim();

            try {
                pin = Integer.parseInt(pinInput);

                // Check if PIN is exactly 4 digits
                if (pinInput.length() == 4 && pin >= 1000 && pin <= 9999) {
                    validPin = true;
                } else {
                    System.out.println("    Error: PIN must be exactly 4 digits (0000-9999)!");
                }
            } catch (NumberFormatException e) {
                System.out.println("    Error: PIN must contain only numbers!");
            }
        }

        register.addNewAuthor(name, pin);
        System.out.println("\n    Welcome to the system " + name);
        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
        TerminalUtils.clear();
    }

    /**
     * contains functions that are reserved to admin
     * Allows search in all diaries. 
     * allows display of all statistics.
     */

    public void extras(){
        TerminalUtils.clear();
        System.out.print("""
                ----------------------------------------
                    Enter Admin password:  """);
        
        while (!scanner.hasNextInt()) {
            TerminalUtils.clear();
            System.out.print("    Invalid input! \nPlease enter a pin (0000-9999): ");
            scanner.next(); // Clear the invalid input
        }
        int ePin = scanner.nextInt();
        scanner.nextLine();
        if(ePin == 1234){
            System.out.println("""
                ----------------------------------------
                    1. Statistics
                    2. Search Keyword
                    3. Search in timespan
                    4. Read all
                    5. Exit
                ----------------------------------------""");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice == 1){
                TerminalUtils.clear();
                register.getStatisticsAll();
                System.out.println("e.Exit");
                scanner.nextLine();
            }
            else if(choice == 2){
                // Search all diaries for a keyword
                TerminalUtils.clear();
                System.out.println("----------------------------------------");
                System.out.print("    Enter keyword to search for (or blank to cancel): ");
                String keyword = scanner.nextLine().trim();
                if (keyword.isEmpty()) {
                    // cancel
                    System.out.println("    Cancelled. Press enter to continue...");
                    scanner.nextLine();
                } else {
                    java.util.List<String> results = register.searchEntries(keyword);
                    TerminalUtils.clear();
                    System.out.println("----------------------------------------");
                    if (results.isEmpty()) {
                        System.out.println("    No diary entries found containing '" + keyword + "'.");
                    } else {
                        System.out.println("    Results for '" + keyword + "':");
                        for (String line : results) {
                            System.out.println("    " + line);
                        }
                    }
                    System.out.println("----------------------------------------");
                    System.out.println("    Press enter to continue...");
                    scanner.nextLine();
                }
                return;
            }
            else if(choice == 3){
                TerminalUtils.clear();
                System.out.println("----------------------------------------");

                // Read and validate start date
                java.time.LocalDate start = null;
                while (start == null) {
                    System.out.print("    Enter start date (YYYY-MM-DD) or 'E' to cancel: ");
                    String startInput = scanner.nextLine().trim();
                    if (startInput.equalsIgnoreCase("e")) {
                        System.out.println("    Cancelled. Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    try {
                        start = java.time.LocalDate.parse(startInput);
                    } catch (java.time.format.DateTimeParseException ex) {
                        System.out.println("    Invalid date format. Please use YYYY-MM-DD.");
                    }
                }

                // Read and validate end date
                java.time.LocalDate end = null;
                while (end == null) {
                    System.out.print("    Enter end date (YYYY-MM-DD) or 'E' to cancel: ");
                    String endInput = scanner.nextLine().trim();
                    if (endInput.equalsIgnoreCase("e")) {
                        System.out.println("    Cancelled. Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    try {
                        end = java.time.LocalDate.parse(endInput);
                    } catch (java.time.format.DateTimeParseException ex) {
                        System.out.println("    Invalid date format. Please use YYYY-MM-DD.");
                    }
                }

                if (end.isBefore(start)) {
                    System.out.println("    End date must be the same or after start date. Press enter to continue...");
                    scanner.nextLine();
                    return;
                }

                // Prompt for optional keyword (empty = match all)
                System.out.print("    Enter keyword to search for within timespan ");
                System.out.print("(blank = all): ");
                String keyword = scanner.nextLine().trim();

                String startStr = start.toString();
                String endStr = end.toString();
                java.util.List<String> results = register.searchEntriesInTimeSpan(keyword, startStr, endStr);
                TerminalUtils.clear();
                System.out.println("----------------------------------------");
                if (results.isEmpty()) {
                    System.out.println("    No diary entries found in the given timespan");
                } else {
                    System.out.println("    Results:");
                    for (String line : results) {
                        System.out.println("    " + line);
                    }
                }
                System.out.println("----------------------------------------");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
            }
            else if(choice == 4){
                TerminalUtils.clear();
                register.printAllDiaries();
                System.out.println("----------------------------------------");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
            }
            else if (choice == 5){
                return;
            }
            else{
                return;
            }

        }
        else{
            return;
        }
        
    }

    /**
     * exit closes the scanner and saves entries to csv
     */

    public void exit() {
        TerminalUtils.clear();
        running = false;
        Save save = new Save();
        System.out.println("Saving data...");
        save.saveToCSV(register.getAuthors());
        System.out.println("Save completed.");
        scanner.close();
        System.out.println("Goodbye!");
    }
}