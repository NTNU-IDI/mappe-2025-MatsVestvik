package main.java.edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuBoxes {

    Scanner scanner;

    MenuBoxes(){

    }

    public void welcome(AuthorRegister register){
        System.out.println("""
            ----------------------------------------
                Welcome to this diary program
                Choose an option:
                1. Login existing user
                2. Create new user
                3. Exit
            ----------------------------------------
                """);
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        welcomeHandling(choice, register);
        scanner.close();
    }

    public void welcomeHandling(int input, AuthorRegister register){
        switch (input) {
            case 1 -> login(register);
            case 2 -> createNewUser(register);
            //case 3 -> exit();
            default -> System.out.println("Something went wrong, please try again");
        }
    }

    public void login(AuthorRegister register){
        System.out.println("----------------------------------------");
        System.out.println("    Choose a user:");
        register.printAllAuthors();
        System.out.println("----------------------------------------");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        loginHandling(choice, register);
        scanner.close();
    }

    public void loginHandling(int input, AuthorRegister register){
        System.out.println("----------------------------------------");
        System.out.println("    You have selected " + register.getAuthorName(input-1));
        System.out.println("    Please enter your pin:");
        System.out.println("----------------------------------------");

        scanner = new Scanner(System.in);
        int ePin = scanner.nextInt();

        if (ePin == 1){
            System.out.println("----------------------------------------");
            System.out.println("    Welcome " + register.getAuthorName(input-1));
            System.out.println("""
                        What do you want to do today?
                        1. Write todays entry
                        2. Edit existing day
                        3. Add specific date
                        4. Exit
                        """);
            System.out.println("----------------------------------------");
            scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            whatTodayHandling(choice, register.getAuthorName(input-1), register);
        }
        else{
            System.out.println("    entered pin was incorrect. Plese try again");
        }
        scanner.close();
    }

    public void whatTodayHandling(int choice, String author, AuthorRegister register){
        switch (choice) {
            case 1 -> writeTodaysEntry(author, register);
            case 2 -> editExistingDay(author, register);
            //case 3 -> addSpecificDate();
            //case 4 -> exit();
            default -> System.out.println("Something went wrong, please try again");
        }
    }

    public void writeTodaysEntry(String author, AuthorRegister register){
        System.out.println("----------------------------------------");
        System.out.println("      What is on your mind today: ");
        scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        register.addDay(author, LocalDate.now().toString(), content);
        System.out.println("----------------------------------------");
    }

    public void editExistingDay(String author, AuthorRegister register){
        register.getAuthorByName(author).printAll();
    }

    public void createNewUser(AuthorRegister register){
        System.out.println("----------------------------------------");
        System.out.print("  Please enter your name: ");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        register.addNewAuthor(name);
        System.out.println("    Welcome to the system " + name);
        System.out.println("----------------------------------------");
    }
}
