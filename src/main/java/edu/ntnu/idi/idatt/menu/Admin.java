package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.TerminalUtils;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class Admin {

    /**
     * contains functions that are reserved to admin
     * Allows search in all diaries. 
     * allows display of all statistics.
     */

    public static void printAdminScreen(AuthorRegister register){
        TerminalUtils.clear();
        Scanner scanner = ScannerManager.getScanner();
        System.out.print("""
                ----------------------------------------
                    Enter Admin password:  """);
        
        while (!scanner.hasNextInt()) {
            TerminalUtils.clear();
            System.out.print("    Invalid input! \nPlease enter a pin (0000-9999): ");
            scanner.next(); // Clear the invalid input
        }
        int ePin = scanner.nextInt();
        scanner.nextLine();
        if(ePin == 1234){
            System.out.println("""
                ----------------------------------------
                    1. Statistics
                    2. Search Keyword
                    3. Search in timespan
                    4. Read all
                    5. Exit
                ----------------------------------------""");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice == 1){
                TerminalUtils.clear();
                register.getStatisticsAll();
                System.out.println("e.Exit");
                scanner.nextLine();
            }
            else if(choice == 2){
                // Search all diaries for a keyword
                TerminalUtils.clear();
                System.out.println("----------------------------------------");
                System.out.print("    Enter keyword to search for (or blank to cancel): ");
                String keyword = scanner.nextLine().trim();
                if (keyword.isEmpty()) {
                    // cancel
                    System.out.println("    Cancelled. Press enter to continue...");
                    scanner.nextLine();
                } else {
                    java.util.List<String> results = register.searchEntries(keyword);
                    TerminalUtils.clear();
                    System.out.println("----------------------------------------");
                    if (results.isEmpty()) {
                        System.out.println("    No diary entries found containing '" + keyword + "'.");
                    } else {
                        System.out.println("    Results for '" + keyword + "':");
                        for (String line : results) {
                            System.out.println("    " + line);
                        }
                    }
                    System.out.println("----------------------------------------");
                    System.out.println("    Press enter to continue...");
                    scanner.nextLine();
                }
                return;
            }
            else if(choice == 3){
                TerminalUtils.clear();
                System.out.println("----------------------------------------");

                // Read and validate start date
                java.time.LocalDate start = null;
                while (start == null) {
                    System.out.print("    Enter start date (YYYY-MM-DD) or 'E' to cancel: ");
                    String startInput = scanner.nextLine().trim();
                    if (startInput.equalsIgnoreCase("e")) {
                        System.out.println("    Cancelled. Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    try {
                        start = java.time.LocalDate.parse(startInput);
                    } catch (java.time.format.DateTimeParseException ex) {
                        System.out.println("    Invalid date format. Please use YYYY-MM-DD.");
                    }
                }

                // Read and validate end date
                java.time.LocalDate end = null;
                while (end == null) {
                    System.out.print("    Enter end date (YYYY-MM-DD) or 'E' to cancel: ");
                    String endInput = scanner.nextLine().trim();
                    if (endInput.equalsIgnoreCase("e")) {
                        System.out.println("    Cancelled. Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    try {
                        end = java.time.LocalDate.parse(endInput);
                    } catch (java.time.format.DateTimeParseException ex) {
                        System.out.println("    Invalid date format. Please use YYYY-MM-DD.");
                    }
                }

                if (end.isBefore(start)) {
                    System.out.println("    End date must be the same or after start date. Press enter to continue...");
                    scanner.nextLine();
                    return;
                }

                // Prompt for optional keyword (empty = match all)
                System.out.print("    Enter keyword to search for within timespan ");
                System.out.print("(blank = all): ");
                String keyword = scanner.nextLine().trim();

                String startStr = start.toString();
                String endStr = end.toString();
                java.util.List<String> results = register.searchEntriesInTimeSpan(keyword, startStr, endStr);
                TerminalUtils.clear();
                System.out.println("----------------------------------------");
                if (results.isEmpty()) {
                    System.out.println("    No diary entries found in the given timespan");
                } else {
                    System.out.println("    Results:");
                    for (String line : results) {
                        System.out.println("    " + line);
                    }
                }
                System.out.println("----------------------------------------");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
            }
            else if(choice == 4){
                TerminalUtils.clear();
                register.printAllDiaries();
                System.out.println("----------------------------------------");
                System.out.println("    Press enter to continue...");
                scanner.nextLine();
            }
            else if (choice == 5){
                return;
            }
            else{
                return;
            }

        }
        else{
            return;
        }
        
    }
}
