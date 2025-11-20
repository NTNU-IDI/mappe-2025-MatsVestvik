package main.java.edu.ntnu.idi.idatt;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();
        Load load = new Load(AuthorRegister.getAuthors());

        MenuBoxes boxes = new MenuBoxes();
        boxes.welcome(register);

        Save.saveToCSV(AuthorRegister.getAuthors());
    }
}