package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.menu.LoginHandler;
import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.service.Load;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        AuthorRegister register = new AuthorRegister();
        @SuppressWarnings("unused")
        Load load = new Load(register.getAuthors());

        // create a scanner and start the login/menu flow using LoginHandler
        Scanner scanner = new Scanner(System.in);
        LoginHandler loginHandler = new LoginHandler(scanner, register);
        loginHandler.login();
        
    }
}