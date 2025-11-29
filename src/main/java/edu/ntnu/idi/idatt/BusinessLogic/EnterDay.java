package edu.ntnu.idi.idatt.BusinessLogic;

import java.time.LocalDate;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class EnterDay {
    
    public static void printValidDay(AuthorRegister register, String authorName){
        System.out.println("-----------------------------------------");
        String entry = ValidEntry.printValidEntry();
        int rating = ValidRating.printValidRating();
        System.out.println("-----------------------------------------");

        register.addDay(authorName, LocalDate.now().toString(), entry, rating);
    }
}
