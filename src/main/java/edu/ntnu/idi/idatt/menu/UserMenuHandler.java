package edu.ntnu.idi.idatt.menu;

import java.time.LocalDate;
import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class UserMenuHandler {
    private Scanner scanner;
    private AuthorRegister register;
    private MenuBoxes menu;
    private DiaryEntryHandler diaryHandler;

    public UserMenuHandler(Scanner scanner, AuthorRegister register, MenuBoxes menu) {
        this.scanner = scanner;
        this.register = register;
        this.menu = menu;
        this.diaryHandler = new DiaryEntryHandler(scanner, register);
    }

    public boolean showUserMenu(String authorName) {
        boolean inUserMenu = true;
        while (inUserMenu) {
            clearTerminal();
            System.out.println("----------------------------------------");
            System.out.println("    Welcome " + authorName);
            System.out.println("""
                        What do you want to do today?
                        1. Write todays entry
                        2. Look at my days
                        3. Add specific date
                        4. Settings
                        5. Logout
                        6. Save and quit
                    ----------------------------------------
                        """);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                inUserMenu = false;
                System.out.println("Logging out...");
                clearTerminal();
            } else if (choice == 6) {
                return true;
            } else {
                boolean shouldLogout = handleUserChoice(choice, authorName);
                if (shouldLogout) {
                    inUserMenu = false;
                }
            }
        }
        return false;
    }

    private boolean handleUserChoice(int choice, String authorName) {
        switch (choice) {
            case 1 -> {
                diaryHandler.writeTodaysEntry(authorName);
                return false;
            }
            case 2 -> {
                diaryHandler.lookAtExistingDay(authorName);
                return false;
            }
            case 3 -> {
                diaryHandler.addSpecificDate(authorName);
                return false;
            }
            case 4 -> {
                SettingsHandler settingsHandler = new SettingsHandler(scanner, register, menu);
                boolean deleted = settingsHandler.settings(authorName);
                if (deleted) {
                    menu.setAuthorName(null);
                    return true;
                }
                return false;
            }
            default -> {
                System.out.println("Not a valid button press");
                return false;
            }
        }
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