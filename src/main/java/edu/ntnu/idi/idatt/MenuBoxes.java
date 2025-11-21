package main.java.edu.ntnu.idi.idatt;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuBoxes {

    Scanner scanner;
    private boolean running = true;
    AuthorRegister register;

    MenuBoxes(AuthorRegister register){
        scanner = new Scanner(System.in); // Initialize scanner once
        this.register = register;
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

    public void welcome(){
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

    public void welcomeHandling(int input){
        switch (input) {
            case 1 -> login();
            case 2 -> createNewUser();
            case 3 -> exit();
            default -> error("Please press a valid key");
        }
    }

    public void login(){
        clearTerminal();
        boolean isInLogin = true;
        while (isInLogin) {
            System.out.println("----------------------------------------");
            System.out.println("    Choose a user:");
            register.printAllAuthors();
            System.out.println("----------------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice > 0 && choice < register.getAuthors().size()+1){
                
                boolean exit = loginHandling(choice);
                if(exit){
                    exit();
                    return;
                }
            }
            else if(choice == register.getAuthors().size()+1){
                clearTerminal();
                isInLogin = false;
            }
            else{
                error("Not a valid input");
            }
        }
        
        
    }

    public boolean loginHandling(int input){
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
                            6. Save and quit
                        ----------------------------------------
                            """);

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (choice == 5) {
                    inUserMenu = false;
                    System.out.println("Logging out...");
                    clearTerminal();
                } 
                else if (choice == 6) {
                    exit();
                    return true;
                }
                else {
                    whatTodayHandling(choice, register.getAuthorName(input-1));
                }
            }
        }
        else{
            System.out.println("    entered pin was incorrect. Please try again");
        }
        return false;
    }

    public void whatTodayHandling(int choice, String author){
        switch (choice) {
            case 1 -> writeTodaysEntry(author);
            case 2 -> lookAtExistingDay(author);
            case 3 -> addSpecificDate(author);
            case 4 -> deleteAccount(author);
            default -> System.out.println("Not a valid button press");
        }
    }

    public void writeTodaysEntry(String author){
        clearTerminal();
        boolean inWriteTodaysEntry = true;
        while (inWriteTodaysEntry) {
            if(register.searchDays(author,LocalDate.now())){
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
                    System.out.print("    ");String content = scanner.nextLine();
                    register.addDay(author, LocalDate.now().toString(), content);
                    System.out.println("    Entry saved for today!");
                    System.out.println("----------------------------------------");
                    inWriteTodaysEntry = false;
                }
                else if (answer.equalsIgnoreCase("n")) {
                    inWriteTodaysEntry = false;
                }
                else{
                    System.out.println("invalid input returning...");
                    inWriteTodaysEntry = false;
                }
            }
            else{
                System.out.println("----------------------------------------");
                System.out.println("    What is on your mind today: ");
                System.out.print("    ");String content = scanner.nextLine();
                register.addDay(author, LocalDate.now().toString(), content);
                System.out.println("    Entry saved for today!");
                System.out.println("----------------------------------------");
                inWriteTodaysEntry = false;
            }
        }    
    }

    public void lookAtExistingDay(String author){
        clearTerminal();
        boolean inLookAtExistingDay = true;
        while (inLookAtExistingDay) {
            System.out.println("----------------------------------------");
            register.getAuthorByName(author).printAll();
            System.out.println("    E. Exit");
            System.out.println("----------------------------------------");
            System.out.print("    Type in the date of the day you want to edit: ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("e")){
                inLookAtExistingDay = false;
            }
            else{
                System.out.print("    Type in the new entry for this day: ");
                String entry = scanner.nextLine();
                register.editDay(choice, entry, author);
                System.out.println("    Entry updated successfully!");
                System.out.println("----------------------------------------"); 
            }
            
        }
        
    }

    public void addSpecificDate(String author){
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

    public void deleteAccount(String author){
        clearTerminal();
        System.out.println("""
                    Warning you are about to delete your account
                    Type your username to confirm
                """);
        String username = scanner.nextLine();
        if(username.equals(author)){
            register.deleteAuthor(author);
            System.out.println("    Account deleted");
        }
        else{
            System.out.println("    What you typed did not match your username");
        }
    }

    public void createNewUser(){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    Please enter your name: ");
        String name = scanner.nextLine();
        System.out.print("    ");register.addNewAuthor(name);
        System.out.println("    Welcome to the system " + name);
        System.out.println("----------------------------------------");
        clearTerminal();
    }

    public void error(String message){
        System.out.println("""
                    ----------------------------------------
                        ERROR: """ + message + """
                        Press enter to continue
                    ----------------------------------------
                    """);
        scanner.nextLine();
        welcome();
    }

    public void exit(){
        clearTerminal();
        running = false;
        Save save = new Save();
        save.saveToCSV(register.getAuthors());
        scanner.close();
        System.out.println("Goodbye!");
    }
}