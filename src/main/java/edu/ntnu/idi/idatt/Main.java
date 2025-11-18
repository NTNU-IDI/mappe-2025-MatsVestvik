package main.java.edu.ntnu.idi.idatt;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();

        //register.addNewAuthor("Mats");
        //register.addNewAuthor("Birgitte");
        //register.addNewAuthor("Adrian");

        //register.printAllAuthors();

        //register.addDay("Mats");
        //register.addDay("Birgitte");

        //register.addContentToDay("This is content", "Mats");
        //System.out.println(register.searchDays("Mats",LocalDate.now()));
        
        //register.addContentToDay("add some more after", "Mats");

 
        //register.printAllAuthors();

        MenuBoxes boxes = new MenuBoxes();
        boxes.welcome(register);
    }
}