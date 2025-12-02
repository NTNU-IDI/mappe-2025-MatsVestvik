package edu.ntnu.idi.idatt.CreateDay;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidEntry {


    public static String promptValidEntry() {
        Scanner scanner = ScannerManager.getScanner();
        String input;
        String entry = "-1";
        while (entry.equals("-1")) {
            System.out.println("    Please enter your entry: ");
            System.out.print("    ");
            input = scanner.nextLine();
            entry = isValidEntry(input);
            if (entry.equals("-1")) {
                System.out.println("    Invalid input! Entry cannot contain '|' character.");
            }
        }
        return entry;
    }
    /**
     * Ensures entry variable does not contain '|' and is formatted to 35 characters per line
     * @param entry
     * @return
     */

    public static String isValidEntry(String entry) {
        if (entry.contains("|")) {
            return "-1";
        }
        else {
            if (entry.length() <= 35) {
                return entry;
            }
            
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < entry.length(); i++) {
                formatted.append(entry.charAt(i));
                if ((i + 1) % 35 == 0 && i != entry.length() - 1) {
                    formatted.append("\n");
                }
            }
            
            return formatted.toString();
        }
    }
}
