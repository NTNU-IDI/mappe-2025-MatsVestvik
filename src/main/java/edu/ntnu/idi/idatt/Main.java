package main.java.edu.ntnu.idi.idatt;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();

        register.addNewAuthor("Mats");
        register.addNewAuthor("Birgitte");
        register.addNewAuthor("Adrian");

        //register.printAllAuthors();

        register.addDay("Mats");
        register.addDay("Birgitte");

        register.addContentToDay("This is content", "Mats");
    }
}