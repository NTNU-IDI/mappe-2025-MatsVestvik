package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class LoginHandler {
    private Scanner scanner;
    private AuthorRegister register;
    private MenuBoxes menu;
    public String authorName;

    public LoginHandler(Scanner scanner, AuthorRegister register, MenuBoxes menu) {
        this.scanner = scanner;
        this.register = register;
        this.menu = menu;
    }

    public void login() {
        boolean isInLogin = true;
        while (isInLogin) {
            clearTerminal();
            System.out.println("----------------------------------------");
            System.out.println("    Choose a user:");
            register.printAllAuthors();
            System.out.println("----------------------------------------");
            while (!scanner.hasNextInt()) {
                System.out.println("    Invalid input! Enter a valid number: ");
                scanner.next(); // Clear the invalid input
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 0 && choice < register.getAuthors().size() + 1) {
                String authorName = register.getAuthorName(choice - 1);
                this.authorName = authorName;
                menu.setAuthorName(authorName);
                boolean exit = loginHandling();
                if (exit) {
                    menu.exit();
                    return;
                }
            } else if (choice == register.getAuthors().size() + 1) {
                clearTerminal();
                isInLogin = false;
            } else {
                System.out.println("Not a valid input");
            }
        }
    }

    public boolean loginHandling() {
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.println("    You have selected " + authorName);
        System.out.println("    Please enter your pin:");
        System.out.println("----------------------------------------");
        while (!scanner.hasNextInt()) {
            clearTerminal();
            System.out.println("    Invalid input! Please enter a pin (0000-9999): ");
            scanner.next(); // Clear the invalid input
        }
        int ePin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (register.getAuthorByName(authorName).checkPin(ePin)) {
            UserMenuHandler userMenuHandler = new UserMenuHandler(scanner, register, menu);
            return userMenuHandler.showUserMenu(authorName);
        } else {
            System.out.println("    entered pin was incorrect. Please try again");
        }
        return false;
    }

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
        System.out.println("    Welcome to the system " + name);
        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
        clearTerminal();
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