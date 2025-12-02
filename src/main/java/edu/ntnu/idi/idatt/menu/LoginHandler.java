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
    public AuthorRegister register;
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
                Admin.printAdminScreen(register);
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
            UserMenuHandler userMenuHandler = new UserMenuHandler(register, this);
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