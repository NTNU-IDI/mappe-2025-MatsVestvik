package main.java.edu.ntnu.idi.idatt;

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
            //case 2 -> createNewUser();
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
                        """);
            System.out.println("----------------------------------------");
        }
        else{
            System.out.println("    entered pin was incorrect. Plese try again");
        }
        scanner.close();
    }
}
