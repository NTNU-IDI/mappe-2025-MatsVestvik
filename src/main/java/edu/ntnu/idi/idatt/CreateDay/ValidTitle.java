package edu.ntnu.idi.idatt.CreateDay;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidTitle {

    public static String promptValidTitle() {
        Scanner scanner = ScannerManager.getScanner();
        String input;
            String title = "-1";
            while (title.equals("-1")) {
                System.out.println("    Please enter a title: ");
                System.out.print("    ");
                input = scanner.nextLine();
                title = ValidTitle.isValidTitle(input);
                if (title.equals("-1")) {
                    System.out.println("""
                                Invalid input! Title cannot be empty,
                                exceed 35 characters, or contain '|' 
                                or newline characters.
                            """);
                }
            }
            return title;
    }

    /**
     * Ensures title is not empty and within maximum length
     * @return title
     */
    public static String printValidTitle() {
        Scanner scanner = ScannerManager.getScanner();
        String title;
        final int MAX_TITLE_LENGTH = 30;
        
        do {
            System.out.println("    Please enter a title: ");
            System.out.print("    ");
            title = scanner.nextLine().trim();
            
            if (title.isEmpty()) {
                System.out.println("    Error: Title cannot be empty. Please try again.");
            } else if (title.length() > MAX_TITLE_LENGTH) {
                System.out.println("    Error: Title cannot exceed " + MAX_TITLE_LENGTH + " characters.");
                System.out.println("    Current length: " + title.length() + " characters.");
            }
        } while (title.isEmpty() || title.length() > MAX_TITLE_LENGTH);
        
        return title;
    }

    public static String isValidTitle(String title) {
        final int MAX_TITLE_LENGTH = 35;
        if (title == null 
        || title.trim().isEmpty() 
        || title.length() > MAX_TITLE_LENGTH 
        || title.contains("|") 
        || title.contains("\n")) {
            return "-1";
        }
        return title;
    }
}