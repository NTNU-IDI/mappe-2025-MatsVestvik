package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidEntry {

    public static String printValidEntry() {
        Scanner scanner = ScannerManager.getScanner();
        String entry;
        do {
            System.out.println("    Type in the entry for this day:");
            System.out.print("    ");
            entry = scanner.nextLine();
            
            if (entry.contains("|")) {
                System.out.println("    Error: Entry cannot contain '|' character. Please try again.");
            }
        } while (entry.contains("|"));
        
        return entry;
    
    }
}
