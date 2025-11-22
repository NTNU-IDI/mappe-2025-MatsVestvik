package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.service.Save;

public class MenuBoxes {
    Scanner scanner;
    private boolean running = true;
    AuthorRegister register;
    private String authorName;
    private LoginHandler loginHandler;
    private UserMenuHandler userMenuHandler;
    private SettingsHandler settingsHandler;

    public MenuBoxes(AuthorRegister register) {
        scanner = new Scanner(System.in);
        this.register = register;
        this.loginHandler = new LoginHandler(scanner, register, this);
        this.userMenuHandler = new UserMenuHandler(scanner, register, this);
        this.settingsHandler = new SettingsHandler(scanner, register, this);
    }

    public void setAuthorName(String authorName) {  
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
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

    public void welcome() {
        clearTerminal();
        while (running) {
            System.out.println("""
                ----------------------------------------
                    Welcome to this diary program
                    Choose an option:
                    1. Login existing user
                    2. Create new user
                    3. Exit
                ----------------------------------------
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            welcomeHandling(choice);
        }
    }

    public void welcomeHandling(int input) {
        switch (input) {
            case 1 -> loginHandler.login();
            case 2 -> loginHandler.createNewUser();
            case 3 -> exit();
            default -> error("Please press a valid key");
        }
    }

    public void error(String message) {
        System.out.println("""
                    ----------------------------------------
                        ERROR: """ + message + """
                        Press enter to continue
                    ----------------------------------------
                    """);
        scanner.nextLine();
        welcome();
    }

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