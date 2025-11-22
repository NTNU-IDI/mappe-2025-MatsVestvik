package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.menu.MenuBoxes;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.service.Load;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();
        Load load = new Load(register.getAuthors());

        MenuBoxes menu = new MenuBoxes(register);
        menu.welcome();
    }
}