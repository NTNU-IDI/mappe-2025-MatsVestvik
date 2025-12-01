package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidEntry {

    public static String printValidEntry() {
        Scanner scanner = ScannerManager.getScanner();
        String entry;
        final int MAX_CHARS_PER_LINE = 35; // Adjust this value as needed
        
        do {
            System.out.println("    Type in the entry for this day:");
            System.out.print("    ");
            entry = scanner.nextLine();
            
            if (entry.contains("|")) {
                System.out.println("    Error: Entry cannot contain '|' character. Please try again.");
            }
        } while (entry.contains("|"));
        
        if (entry.length() <= MAX_CHARS_PER_LINE) {
            return entry;
        }
        
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < entry.length(); i++) {
            formatted.append(entry.charAt(i));
            if ((i + 1) % MAX_CHARS_PER_LINE == 0 && i != entry.length() - 1) {
                formatted.append("\n");
            }
        }
        
        return formatted.toString();
    }
}
