package edu.ntnu.idi.idatt.CreateDay;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidRating {

    public static int promptValidRating() {
        Scanner scanner = ScannerManager.getScanner();
        String input;
        int rating = -1;
        while (rating == -1) {
            System.out.print("    Please enter a rating (1-10): ");
            input = scanner.nextLine();
            rating = isValidRating(input);
            
            if (rating == -1) {
                System.out.println("    Invalid input!");
            }
        }
        return rating;
    }
    /**
     * Ensures rating variable is an int between 1 and 10
     * @param input
     * @return
     */
    public static int isValidRating(String input) {
        try {
            // First, try to parse the input as an integer
            int rating = Integer.parseInt(input.trim());
            
            // Then check if it's in the valid range
            if (rating >= 1 && rating <= 10) {
                return rating;
            }
            // If it's an integer but outside the range, return -1
            return -1;
            
        } catch (NumberFormatException e) {
            // If parsing fails, it's not a valid integer
            return -1;
        }
    }
}
