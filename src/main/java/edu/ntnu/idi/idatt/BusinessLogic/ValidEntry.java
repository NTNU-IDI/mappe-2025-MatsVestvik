package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;

public class ValidEntry {
    private static Scanner scanner = new Scanner(System.in);

    public static String printValidEntry() {
        
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
