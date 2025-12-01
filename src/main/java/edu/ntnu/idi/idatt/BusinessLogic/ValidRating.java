package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidRating {
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
