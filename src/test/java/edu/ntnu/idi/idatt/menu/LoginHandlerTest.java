package edu.ntnu.idi.idatt.menu;

import edu.ntnu.idi.idatt.objects.AuthorRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LoginHandlerTest {

    private AuthorRegister register;
    // no global LoginHandler required; tests construct a dedicated instance as needed

    static class TestLoginHandler extends LoginHandler {
        boolean exited = false;

        public TestLoginHandler(Scanner scanner, AuthorRegister register) {
            super(scanner, register);
        }

        @Override
        public void exit() {
            // don't actually save/close scanner; just mark that we exited
            this.exited = true;
        }

        public boolean isExited() {
            return exited;
        }
    }

    @BeforeEach
    void setUp() {
        register = new AuthorRegister();
        register.addNewAuthor("Alice", 1234);
    }

    @Test
    void loginHandling_correctPin_logoutReturnsFalse() {
        Scanner scanner = new Scanner("1234\n5\n"); // correct pin -> user menu -> logout
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice"); // simulate choosing the user

        boolean result = handler.loginHandling();
        assertFalse(result, "loginHandling should return false when user logs out");
    }

    @Test
    void loginHandling_correctPin_saveAndQuitReturnsTrue() {
        Scanner scanner = new Scanner("1234\n6\n"); // correct pin -> user menu -> save & exit
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice");

        boolean result = handler.loginHandling();
        assertTrue(result, "loginHandling should return true when user chose save & exit");
    }

    @Test
    void loginHandling_incorrectPin_returnsFalse() {
        Scanner scanner = new Scanner("0000\n"); // incorrect pin
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice");

        boolean result = handler.loginHandling();
        assertFalse(result, "loginHandling should return false for incorrect pin");
    }
}
