package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidRating {
    /**
     * Ensures rating variable is an int between 1 and 10
     * @return
     */
    public static int printValidRating() {
        Scanner scanner = ScannerManager.getScanner();
        int rating;
        do {
                System.out.println("    Please enter a rating (1-10): ");
                System.out.print("    ");
                while (!scanner.hasNextInt()) {
                    System.out.println("    Invalid input! Please enter a number (1-10): ");
                    scanner.next(); 
                    System.out.print("    ");
                }
                rating = scanner.nextInt();
                scanner.nextLine();
            } while (rating < 1 || rating > 10);
        return rating;
    }
}
