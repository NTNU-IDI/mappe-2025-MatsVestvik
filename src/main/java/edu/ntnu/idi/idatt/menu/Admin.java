package edu.ntnu.idi.idatt.menu;

import java.util.Scanner;

import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.TerminalUtils;
import edu.ntnu.idi.idatt.util.ScannerManager;
import edu.ntnu.idi.idatt.util.DateUtils;
import edu.ntnu.idi.idatt.util.IntCheck;

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
        
        int ePin = IntCheck.isInt();
        scanner.nextLine();

        if(ePin == 1234){
            boolean inAdminMenu = true;
            while (inAdminMenu) {
                TerminalUtils.clear();
                System.out.println("""
                    ----------------------------------------
                        1. Statistics
                        2. Search
                        3. Read all
                        4. Logout
                    ----------------------------------------""");
                System.out.println("    ");
                //ensure that input is an int
                
                int choice = IntCheck.isInt();
                scanner.nextLine();
                if(choice == 1){
                    TerminalUtils.clear();
                    register.getStatisticsAll();
                    System.out.println("----------------------------------------");
                    System.out.println("    Press enter to continue...");
                    scanner.nextLine();
                }
                else if(choice == 2){
                    // Search all diaries for a keyword
                    search(register);;
                }
                else if(choice == 3){
                    TerminalUtils.clear();
                    register.printAllDiaries();
                    System.out.println("----------------------------------------");
                    System.out.println("    Press enter to continue...");
                    scanner.nextLine();
                }
                else if (choice == 4){
                    inAdminMenu = false;
                    return;
                }
                else{
                    System.out.println("    invalid input");;
                }

            }

        }
        else{
            return;
        }
        
    }

    private static void  search(AuthorRegister register){
        Scanner scanner = ScannerManager.getScanner();
        TerminalUtils.clear();
        System.out.println("----------------------------------------");
        System.out.println("    Enter keyword to search for (or blank): ");
        System.out.print("    "); 
        String keyword = scanner.nextLine().trim();
        java.util.List<edu.ntnu.idi.idatt.objects.Day> days = register.getAllDays();
  
        System.out.println("    Enter a start date (YYYY-MM-DD) or blank: ");
        System.out.print("    ");
        String dateInput = scanner.nextLine().trim();
        // keep prompting until blank or valid
        while (!dateInput.isEmpty() && !DateUtils.isValidIsoDate(dateInput)) {
            System.out.println("    Invalid date format. Please enter date as YYYY-MM-DD or blank: ");
            System.out.print("    ");
            dateInput = scanner.nextLine().trim();
        }
        if (dateInput.isEmpty()) {
            dateInput = null;
        }

        System.out.println("    Enter an end date (YYYY-MM-DD) or blank: ");
        System.out.print("    ");
        String endDateInput = scanner.nextLine().trim();
        while (!endDateInput.isEmpty() && !DateUtils.isValidIsoDate(endDateInput)) {
            System.out.println("    Invalid date format. Please enter date as YYYY-MM-DD or blank: ");
            System.out.print("    ");
            endDateInput = scanner.nextLine().trim();
        }
        if (endDateInput.isEmpty()) {
            endDateInput = null;
        }

        java.util.List<edu.ntnu.idi.idatt.objects.Day> results;
        if(dateInput == null || endDateInput == null){
            results = register.searchEntries(keyword, days);
        }
        else{
            try {
                results = register.searchEntriesInTimeSpan(keyword, dateInput, endDateInput, days);
            } catch (IllegalArgumentException e) {
                System.out.println("    Date range invalid: " + e.getMessage());
                results = register.searchEntries(keyword, days);
            }
        }

        TerminalUtils.clear();
        System.out.println("----------------------------------------");
        register.printDays(results);
        System.out.println("----------------------------------------");
        System.out.println("    Press enter to continue...");
        scanner.nextLine();
    }

}
