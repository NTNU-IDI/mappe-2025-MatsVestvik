package main.java.edu.ntnu.idi.idatt;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();
        Load load = new Load(register.getAuthors());

        MenuBoxes menu = new MenuBoxes(register);
        menu.welcome();

        Save.saveToCSV(AuthorRegister.getAuthors());
    }
}