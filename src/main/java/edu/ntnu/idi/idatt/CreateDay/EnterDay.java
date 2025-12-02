package edu.ntnu.idi.idatt.CreateDay;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class EnterDay {
    
    public static void printValidDay(AuthorRegister register, String authorName, String date){
        String entry;
        int rating;
        String title;
        System.out.println("-----------------------------------------");
        
        title = ValidTitle.promptValidTitle();
        entry = ValidEntry.promptValidEntry();
        rating = ValidRating.promptValidRating();

        System.out.println("-----------------------------------------");

        register.addDay(authorName, date, entry, rating, title);
    }
}
