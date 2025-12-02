package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.TerminalUtils;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class UserMenuHandler {

    /**
     * allows access to specific author abject and variables.
     */
    private Scanner scanner;
    private AuthorRegister register;
    private LoginHandler menu;
    private DiaryEntryHandler diaryHandler;
    public String authorName;

    /**
     * create usermenu with specified parameters.
     * 
     * @param scanner
     * @param register
     * @param menu
     */

    public UserMenuHandler(AuthorRegister register, LoginHandler menu) {
        this.scanner = ScannerManager.getScanner();
        this.register = register;
        this.menu = menu;
        this.diaryHandler = new DiaryEntryHandler(scanner, register);
    }

    /**
     * prompts user with options.
     * write entry with a date. look at days
     * settings takes user to SettingsHandler
     * logout takes user to LoginHandler
     * save and quit writes data to csv and closes scanner.
     * @param authorName
     * @return
     */

    public boolean showUserMenu(String authorName) {
        this.authorName = authorName;
        boolean inUserMenu = true;
        while (inUserMenu) {
            TerminalUtils.clear();
            System.out.println("----------------------------------------");
            System.out.println("    Welcome " + this.authorName);
            System.out.println("""
                        What do you want to do today?
                        1. Write todays entry
                        2. Look at my days
                        3. Add specific date
                        4. Settings
                        5. Logout
                        6. Save and quit
                    ----------------------------------------""");
            System.out.print("    ");
            while (!scanner.hasNextInt()) {
                System.out.println("    Invalid input! Enter a valid number: ");
                scanner.next(); // Clear the invalid input
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                inUserMenu = false;
                System.out.println("Logging out...");
                TerminalUtils.clear();
            } else if (choice == 6) {
                return true;
            } else {
                boolean shouldLogout = handleUserChoice(choice);

                // Refresh the displayed author name in case Settings changed it
                String current = menu.getAuthorName();
                if (current != null && !current.equals(this.authorName)) {
                    this.authorName = current;
                }

                if (shouldLogout) {
                    inUserMenu = false;
                }
            }
        }
        return false;
    }

    /**
     * handles input from previous menu. takes user to responsible handler.
     * @param choice
     * @return
     */

    private boolean handleUserChoice(int choice) {
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

    /**
     * clear terminal inbetween menuoutputs for readability.
     */
    // Terminal clearing delegated to TerminalUtils.clear()
}