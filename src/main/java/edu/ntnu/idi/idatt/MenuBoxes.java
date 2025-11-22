package edu.ntnu.idi.idatt;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuBoxes {

    Scanner scanner;
    private boolean running = true;
    AuthorRegister register;
    private String authorName;

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
                this.authorName = register.getAuthorName(choice-1);
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
        System.out.println("    You have selected " + authorName);
        System.out.println("    Please enter your pin:");
        System.out.println("----------------------------------------");

        
        int ePin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (register.getAuthorByName(authorName).checkPin(ePin)){     
            
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
                } 
                else if (choice == 6) {
                    return true;
                }
                else {
                    whatTodayHandling(choice);
                }
            }
        }
        else{
            System.out.println("    entered pin was incorrect. Please try again");
        }
        return false;
    }

    public void whatTodayHandling(int choice){
        switch (choice) {
            case 1 -> writeTodaysEntry();
            case 2 -> lookAtExistingDay();
            case 3 -> addSpecificDate();
            case 4 -> settings();
            default -> System.out.println("Not a valid button press");
        }
    }

    public void writeTodaysEntry(){
        clearTerminal();
        boolean inWriteTodaysEntry = true;
        while (inWriteTodaysEntry) {
            if(register.searchDays(authorName,LocalDate.now())){
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
                    register.addDay(authorName, LocalDate.now().toString(), content);
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
                register.addDay(authorName, LocalDate.now().toString(), content);
                System.out.println("    Entry saved for today!");
                System.out.println("----------------------------------------");
                inWriteTodaysEntry = false;
            }
        }    
    }

    public void lookAtExistingDay(){
        clearTerminal();
        boolean inLookAtExistingDay = true;
        while (inLookAtExistingDay) {
            System.out.println("----------------------------------------");
            register.getAuthorByName(authorName).printAll();
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
                register.editDay(choice, entry, authorName);
                System.out.println("    Entry updated successfully!");
                System.out.println("----------------------------------------"); 
            }
            
        }
        
    }

    public void addSpecificDate(){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.println("    What is on your mind for " + date + ": ");
        System.out.println("    ");String content = scanner.nextLine();
        register.addDay(authorName, date, content);
        System.out.println("Entry saved for " + date + "!");
        System.out.println("----------------------------------------");
    }

    public void settings(){
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
            if (choice == 4){
                inSetting = false;
            }
            else{
                boolean accountDeleted = settingsHandler(choice);
                if (accountDeleted) {
                    return; // Exit immediately if account was deleted
                }
            }
        }
    }

    public boolean settingsHandler(int choice){
        clearTerminal();
        switch (choice) {
            case 1 -> changeUsername();
            case 2 -> changePassword();
            case 3 ->  {
                if(deleteAccount()) {
                    return true; // Account was deleted
                }
            }
            default -> System.out.println("Invalid input");
        }
        return false;
    }

    public void changeUsername(){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    New name: ");
        String newName = scanner.nextLine();
        String oldName = this.authorName; // Store the old name
     
        register.getAuthorByName(oldName).setName(newName);

        this.authorName = newName;
        
        System.out.println("    Username changed from " + oldName + " to " + newName);
        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
    }

    public void changePassword(){
        clearTerminal();
        System.out.println("----------------------------------------");
        System.out.print("    Please enter your current pin: ");            
        int triedPin = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if(register.getAuthorByName(authorName).checkPin(triedPin)){
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

    public boolean deleteAccount(){
        String username = "";
        boolean inDeleteAccount = true;
        while (inDeleteAccount) {
            clearTerminal();
            System.out.println("""
                ----------------------------------------
                    Warning you are about to delete
                    your account
                    Type your username to confirm:
                ----------------------------------------
                        """);
            
            username = scanner.nextLine();
            if(!username.equals("")){
                if(username.equals(authorName)){
                    register.deleteAuthor(authorName);
                    System.out.println("Account deleted successfully!");
                    return true;
                }
                else if(username.equalsIgnoreCase("cancel")){
                    System.out.println("Account deletion cancelled.");
                    return false;
                }
                else{
                    System.out.println("What you typed did not match your username. Type 'cancel' to go back.");
                    String cancel = scanner.nextLine();
                    if (cancel.equalsIgnoreCase("cancel")){
                        return false;
                    }
                    else{

                    }
                }
            }
            
            
        }
        return false;
    }

    public void createNewUser(){
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
        System.out.println("Saving data..."); // Debug line
        save.saveToCSV(register.getAuthors());
        System.out.println("Save completed."); // Debug line
        scanner.close();
        System.out.println("Goodbye!");
    }
}