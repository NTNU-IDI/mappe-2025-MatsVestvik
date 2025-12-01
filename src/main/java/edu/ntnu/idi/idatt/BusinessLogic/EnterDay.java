package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class EnterDay {
    
    public static void printValidDay(AuthorRegister register, String authorName, String date){
        Scanner scanner = ScannerManager.getScanner();
        System.out.println("-----------------------------------------");
        String title = ValidTitle.printValidTitle();
        String entry = ValidEntry.printValidEntry();
        String input;
        int rating = -1;
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
