package edu.ntnu.idi.idatt.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class SettingsHandlerTest {

    private AuthorRegister register;
    private Scanner scanner;
    private TestLoginHandler menu;
    private SettingsHandler settingsHandler;
    private String authorName = "Alice";

    // Simple stub to intercept setAuthorName calls and avoid real saving
    static class TestLoginHandler extends LoginHandler {
        boolean exited = false;
        public TestLoginHandler(Scanner scanner, AuthorRegister register) {
            super(scanner, register);
        }
        @Override
        public void exit() {
            exited = true; // avoid saving
        }
    }

    @BeforeEach
    void setUp() {
        register = new AuthorRegister();
        register.addNewAuthor(authorName, 1234);
        menu = new TestLoginHandler(new Scanner(""), register);
    }

    @Test
    void changeUsername_success() {
        // prepare scanner: dummy newline then new name
        scanner = new Scanner("\nBob\n\n");
        settingsHandler = new SettingsHandler(scanner, register, menu);
        settingsHandler.authorName = authorName; // set the current author

        settingsHandler.changeUsername();

        assertNotNull(register.getAuthorByName("Bob"));
        assertNull(register.getAuthorByName("Alice"));
        assertEquals("Bob", menu.getAuthorName());
    }

    @Test
    void changePassword_success() {
        // current pin then new pin
        scanner = new Scanner("1234\n2468\n\n");
        settingsHandler = new SettingsHandler(scanner, register, menu);
        settingsHandler.authorName = authorName;

        settingsHandler.changePassword();

        assertEquals(2468, register.getAuthorByName(authorName).getPin());
    }

    @Test
    void deleteAccount_confirmationDeletesAccount() {
        scanner = new Scanner("Alice\n");
        settingsHandler = new SettingsHandler(scanner, register, menu);
        settingsHandler.authorName = authorName;

        boolean deleted = settingsHandler.deleteAccount();
        assertTrue(deleted);
        assertNull(register.getAuthorByName(authorName));
    }
}
