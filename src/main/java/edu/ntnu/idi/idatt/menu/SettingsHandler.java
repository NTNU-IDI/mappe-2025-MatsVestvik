package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class SettingsHandler {
    private Scanner scanner;
    private AuthorRegister register;
    private MenuBoxes menu;

    public SettingsHandler(Scanner scanner, AuthorRegister register, MenuBoxes menu) {
        this.scanner = scanner;
        this.register = register;
        this.menu = menu;
    }

    public void settings(String authorName) {
        boolean inSetting = true;
        while (inSetting) {
            clearTerminal();
            System.out.println("""
                        ----------------------------------------
                            %s's Settings
                            1. Change Username
                            2. Change password
                            3. Delete account
                            4. Back
                        ----------------------------------------
                        """.formatted(authorName));

            int choice = scanner.nextInt();
            if (choice == 4) {
                inSetting = false;
            } else {
                boolean accountDeleted = settingsHandler(choice, authorName);
                if (accountDeleted) {
                    return; // Exit immediately if account was deleted
                }
            }
        }
    }

    public boolean settingsHandler(int choice, String authorName) {
        clearTerminal();
        switch (choice) {
            case 1 -> changeUsername(authorName);
            case 2 -> changePassword(authorName);
            case 3 -> {
                if (deleteAccount(authorName)) {
                    return true; // Account was deleted
                }
            }
            default -> System.out.println("Invalid input");
        }
        return false;
    }

    public void changeUsername(String authorName) {
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    New name: ");
        scanner.nextLine();
        String newName = scanner.nextLine();
        String oldName = authorName;

        register.getAuthorByName(oldName).setName(newName);
        menu.setAuthorName(newName);

        System.out.println("    Username changed from " + oldName + " to " + newName);
        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
    }

    public void changePassword(String authorName) {
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    Please enter your current pin: ");
        int triedPin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (register.getAuthorByName(authorName).checkPin(triedPin)) {
            int newPin = 0;
            boolean validPin = false;

            while (!validPin) {
                System.out.print("    Please enter a 4-digit pin: ");
                String pinInput = scanner.nextLine().trim();

                try {
                    newPin = Integer.parseInt(pinInput);

                    // Check if PIN is exactly 4 digits
                    if (pinInput.length() == 4 && newPin >= 1000 && newPin <= 9999) {
                        validPin = true;
                    } else {
                        System.out.println("    Error: PIN must be exactly 4 digits (0000-9999)!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("    Error: PIN must contain only numbers!");
                }
            }

            register.getAuthorByName(authorName).setPin(newPin);
            System.out.println("    PIN changed successfully!");

        } else {
            System.out.println("    Incorrect current PIN!");
        }

        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
    }

    public boolean deleteAccount(String authorName) {
        String username = "";
        boolean inDeleteAccount = true;
        while (inDeleteAccount) {
            clearTerminal();
            System.out.println("""
                ----------------------------------------
                    Warning you are about to delete
                    your account %s
                    Type your username to confirm:
                ----------------------------------------""".formatted(authorName));

            username = scanner.nextLine();
            if (!username.equals("")) {
                if (username.equals(authorName)) {
                    register.deleteAuthor(authorName);
                    System.out.println("Account deleted successfully!");
                    return true;
                } else if (username.equalsIgnoreCase("cancel")) {
                    System.out.println("Account deletion cancelled.");
                    return false;
                } else {
                    System.out.println("What you typed did not match your username. Type 'cancel' to go back.");
                    String cancel = scanner.nextLine();
                    if (cancel.equalsIgnoreCase("cancel")) {
                        return false;
                    }
                }
            }
        }
        return false;
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