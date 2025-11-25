package edu.ntnu.idi.idatt.menu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class UserMenuHandlerTest {

    static class TestLoginHandler extends LoginHandler {
        public TestLoginHandler(Scanner scanner, AuthorRegister register) {
            super(scanner, register);
        }

        @Override
        public void exit() {
            // noop for tests
        }
    }

    @Test
    void showUserMenu_logoutReturnsFalse() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Dana", 4444);

        String input = "5\n"; // logout
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        UserMenuHandler handler = new UserMenuHandler(scanner, reg, controller);

        boolean result = handler.showUserMenu("Dana");
        assertFalse(result);
    }

    @Test
    void showUserMenu_saveAndQuitReturnsTrue() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Ellen", 5555);

        String input = "6\n"; // save and quit
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        UserMenuHandler handler = new UserMenuHandler(scanner, reg, controller);

        boolean result = handler.showUserMenu("Ellen");
        assertTrue(result);
    }
}
