package main.java.edu.ntnu.idi.idatt;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();
        Load load = new Load(AuthorRegister.getAuthors(), AuthorRegister.getDays());

        
        //register.addNewAuthor("Birgitte");
        //register.addNewAuthor("Mats");

        //register.printAllAuthors();

        //register.addDay("Birgitte", "2025-08-12", "This is content");
        //register.addDayToday("Birgitte", "This is also some content");
        //register.addDay("Birgitte", "2025-08-30");

        //register.addContentToDay("This is content", "Mats");
        //System.out.println(register.searchDays("Mats",LocalDate.now()));
        
        //register.addContentToDay("add some more after", "Mats");

 
        //register.printAllAuthors();

        //MenuBoxes boxes = new MenuBoxes();
        //boxes.welcome(register);

        register.printAllAuthors();
        register.printDaysAuthor(AuthorRegister.getAuthorByName("Mats"));

        //register.printAll();
        //Save.saveToCSV(AuthorRegister.getAuthors(), AuthorRegister.getDays());
    }
}