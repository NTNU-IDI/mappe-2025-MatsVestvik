package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.service.Save;

public class LoginHandler {

    Scanner scanner;
    private boolean running = true;
    AuthorRegister register;
    private String authorName;
    private UserMenuHandler userMenuHandler;
    private SettingsHandler settingsHandler;

    /*
    Login handler is where you choose your author object. This allows you to gain
    access to other functions. This is a security step.
    */

    public LoginHandler(Scanner scanner, AuthorRegister register) {
        this.scanner = scanner;
        this.register = register;
        this.userMenuHandler = new UserMenuHandler(scanner, register, this);
        this.settingsHandler = new SettingsHandler(scanner, register, this);
    }

    /*
    Login shows you all the author currently in the system. You are also shown the ability
    to create a new author or save and quit. Save and quit writes all data to seperate csv files
    for all author. 
    */

    public void login() {
        boolean isInLogin = true;
        while (isInLogin && running) {
            clearTerminal();
            System.out.println("----------------------------------------");
            System.out.println("    Choose a user:");
            register.printAllAuthors();
            System.out.println("    "+(register.getAuthors().size() + 1)+". Create new user");
            System.out.println("    " + (register.getAuthors().size() + 2) + ". Save and Exit");
            System.out.print("----------------------------------------\n    ");
            while (!scanner.hasNextInt()) {
                System.out.println("    Invalid input! Enter a valid number: ");
                scanner.next(); // Clear the invalid input
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 0 && choice < register.getAuthors().size() + 1) {
                String authorName = register.getAuthorName(choice - 1);
                this.authorName = authorName;
                setAuthorName(authorName);
                boolean exit = loginHandling();
                if (exit) {
                    exit();
                    return;
                }
            } else if (choice == register.getAuthors().size() + 1) {
                createNewUser();
            } else if (choice == register.getAuthors().size() + 2) {
                clearTerminal();
                exit();
            } else {
                System.out.println("Not a valid input");
            }
        }
    }

    /*
    Login handling prompts you to enter a pin. We then take that pin in pass it into
    the check pin function to see if it is the correct pin of the user.
    After this check user gains acces to the days of this author.
    */

    public boolean loginHandling() {
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.println("    You have selected " + authorName);
        System.out.print("    Please enter your pin: ");
        
        while (!scanner.hasNextInt()) {
            clearTerminal();
            System.out.println("    Invalid input! Please enter a pin (0000-9999): ");
            scanner.next(); // Clear the invalid input
        }
        int ePin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (register.getAuthorByName(authorName).checkPin(ePin)) {
            UserMenuHandler userMenuHandler = new UserMenuHandler(scanner, register, this);
            return userMenuHandler.showUserMenu(authorName);
        } else {
            System.out.println("    entered pin was incorrect. Please try again");
        }
        return false;
    }

    /*
    This funtion ensures that the author name is updated.
    */

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    /*
    Here you are prompted to enter name and pin for a new author abject. this function
    is also responsible for sinitizing the input for good user feedback */

    public void createNewUser() {
        clearTerminal();
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
        clearTerminal();
    }

    /*
    clear terminal clears the terminal
     */

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

    /*
    exit is a function that closes the scanner and calls the save to csv method to write date.
    */

    public void exit() {
        clearTerminal();
        running = false;
        Save save = new Save();
        System.out.println("Saving data...");
        save.saveToCSV(register.getAuthors());
        System.out.println("Save completed.");
        scanner.close();
        System.out.println("Goodbye!");
    }
}