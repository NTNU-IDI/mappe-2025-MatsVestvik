package edu.ntnu.idi.idatt.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class UserMenuHandlerTest {

    static class TestLoginHandler extends LoginHandler {
        boolean exited = false;
        public TestLoginHandler(Scanner scanner, AuthorRegister register) {
            super(scanner, register);
        }
        @Override
        public void exit() {
            exited = true; // prevent save/close
        }
    }

    @Test
    void showUserMenu_logoutReturnsFalse() {
        String authorName = "Alice";
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor(authorName, 1234);
        // choice 5 -> logout
        Scanner scanner = new Scanner("5\n");
        TestLoginHandler menu = new TestLoginHandler(scanner, reg);
        UserMenuHandler handler = new UserMenuHandler(scanner, reg, menu);

        boolean result = handler.showUserMenu(authorName);
        assertFalse(result); // logout does not request save & quit
    }

    @Test
    void showUserMenu_saveAndQuitReturnsTrue() {
        String authorName = "Bob";
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor(authorName, 1234);
        // choice 6 -> Save and quit
        Scanner scanner = new Scanner("6\n");
        TestLoginHandler menu = new TestLoginHandler(scanner, reg);
        UserMenuHandler handler = new UserMenuHandler(scanner, reg, menu);

        boolean result = handler.showUserMenu(authorName);
        assertTrue(result); // save & quit requested
    }
}
