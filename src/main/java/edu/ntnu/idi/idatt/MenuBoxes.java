package main.java.edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class MenuBoxes {

    Scanner scanner;
    private boolean running = true;

    MenuBoxes(){
        scanner = new Scanner(System.in); // Initialize scanner once
    }
    private void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If clearing fails, just print some empty lines
            System.out.println("\n".repeat(50));
        }
    }

    public void welcome(AuthorRegister register){
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
            welcomeHandling(choice, register);
        }
    }

    public void welcomeHandling(int input, AuthorRegister register){
        switch (input) {
            case 1 -> login(register);
            case 2 -> createNewUser(register);
            case 3 -> exit(register);
            default -> System.out.println("Something went wrong, please try again");
        }
    }

    public void login(AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.println("    Choose a user:");
        register.printAllAuthors();
        System.out.println("----------------------------------------");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        loginHandling(choice, register);
    }

    public void loginHandling(int input, AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.println("    You have selected " + register.getAuthorName(input-1));
        System.out.println("    Please enter your pin:");
        System.out.println("----------------------------------------");

        int ePin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (ePin == 1){ 
            
            boolean inUserMenu = true;
            while (inUserMenu) {
                clearTerminal();
                System.out.println("----------------------------------------");
                System.out.println("    Welcome " + register.getAuthorName(input-1));
                System.out.println("""
                            What do you want to do today?
                            1. Write todays entry
                            2. Look at my days
                            3. Add specific date
                            4. Delete my account
                            5. Logout
                        ----------------------------------------
                            """);

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (choice == 5) {
                    inUserMenu = false;
                    System.out.println("Logging out...");
                } else {
                    whatTodayHandling(choice, register.getAuthorName(input-1), register);
                }
            }
        }
        else{
            System.out.println("    entered pin was incorrect. Please try again");
        }
    }

    public void whatTodayHandling(int choice, String author, AuthorRegister register){
        switch (choice) {
            case 1 -> writeTodaysEntry(author, register);
            case 2 -> lookAtExistingDay(author, register);
            case 3 -> addSpecificDate(author, register);
            case 4 -> deleteAccount(author, register);
            default -> System.out.println("Something went wrong, please try again");
        }
    }

    public void writeTodaysEntry(String author, AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.println("      What is on your mind today: ");
        String content = scanner.nextLine();
        register.addDay(author, LocalDate.now().toString(), content);
        System.out.println("Entry saved for today!");
        System.out.println("----------------------------------------");
    }

    public void lookAtExistingDay(String author, AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        register.getAuthorByName(author).printAll();
        System.out.println("----------------------------------------");
        System.out.print("  Type in the date of the day you want to edit: ");
        String choice = scanner.nextLine();
        System.out.print("  Type in the new entry for this day: ");
        String entry = scanner.nextLine();
        register.editDay(choice, entry, author);
        System.out.println("Entry updated successfully!");
        System.out.println("----------------------------------------");
    }

    public void addSpecificDate(String author, AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("  Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.println("      What is on your mind for " + date + ": ");
        String content = scanner.nextLine();
        register.addDay(author, date, content);
        System.out.println("Entry saved for " + date + "!");
        System.out.println("----------------------------------------");
    }

    public void deleteAccount(String author, AuthorRegister register){
        clearTerminal();
        System.out.println("""
                    Warning you are about to delete your account
                    Type your username to confirm
                """);
        String username = scanner.nextLine();
        if(username.equals(author)){
            register.deleteAuthor(author);
            System.out.println("    Account delted");
        }
        else{
            System.out.println("    What you typed did not match your username");
        }
    }

    public void createNewUser(AuthorRegister register){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("  Please enter your name: ");
        String name = scanner.nextLine();
        register.addNewAuthor(name);
        System.out.println("    Welcome to the system " + name);
        System.out.println("----------------------------------------");
    }

    public void exit(AuthorRegister register){
        clearTerminal();
        running = false;
        Save.saveToCSV(register.getAuthors(), register.getDays());
        scanner.close();
        System.out.println("Goodbye!");
    }
}