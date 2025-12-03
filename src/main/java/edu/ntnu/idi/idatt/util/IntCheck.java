package edu.ntnu.idi.idatt.util;

import java.util.Scanner;

public class IntCheck {

    public static int isInt(){
        Scanner scanner = ScannerManager.getScanner();
        while (!scanner.hasNextInt()) {
            System.out.println("    Invalid input! Enter a valid number: ");
            System.out.print("    ");
            scanner.next(); // Clear the invalid input
        }
        int value = scanner.nextInt();
        return value;
    }

    public static int validPin(){
        Scanner scanner = ScannerManager.getScanner();
        System.out.print("    Please enter a pin (0000-9999): ");

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.print("    Please enter a pin (0000-9999): ");
                continue;
            }

            // Require exactly 4 digits
            if (!line.matches("\\d{4}")) {
                System.out.print("    Invalid input! Please enter a four-digit pin (0000-9999): ");
                continue;
            }

            try {
                int ePin = Integer.parseInt(line);
                if (ePin >= 0 && ePin <= 9999) {
                    return ePin;
                }
                System.out.print("    Pin must be between 0000 and 9999. Try again: ");
            } catch (NumberFormatException ex) {
                // Shouldn't happen because of regex, but handle defensively
                System.out.print("    Invalid input! Please enter a numeric pin (0000-9999): ");
            }
        }
    }
}
