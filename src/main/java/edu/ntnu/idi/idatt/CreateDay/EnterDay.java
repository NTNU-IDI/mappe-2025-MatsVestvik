package edu.ntnu.idi.idatt.CreateDay;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class EnterDay {
    
    public static void printValidDay(AuthorRegister register, String authorName, String date){
        String entry;
        int rating;
        String title;
        String input;
        Scanner scanner = ScannerManager.getScanner();
        System.out.println("-----------------------------------------");
        
        
        title = "-1";
        while (title.equals("-1")) {
            System.out.println("    Please enter a title: ");
            System.out.print("    ");
            input = scanner.nextLine();
            title = ValidTitle.isValidTitle(input);
            if (title.equals("-1")) {
                System.out.println("    Invalid input! Title cannot be empty, exceed 35 characters, or contain '|' or newline characters.");
            }
        }
        
        entry = "-1";
        while (entry.equals("-1")) {
            System.out.println("    Please enter your entry: ");
            System.out.print("    ");
            input = scanner.nextLine();
            entry = ValidEntry.isValidEntry(input);
            if (entry.equals("-1")) {
                System.out.println("    Invalid input! Entry cannot contain '|' character.");
            }
        }
        rating = -1;
        while (rating == -1) {
            System.out.print("    Please enter a rating (1-10): ");
            input = scanner.nextLine();
            rating = ValidRating.isValidRating(input);
            
            if (rating == -1) {
                System.out.println("    Invalid input!");
            }
        }
        System.out.println("-----------------------------------------");

        register.addDay(authorName, date, entry, rating, title);
    }
}
