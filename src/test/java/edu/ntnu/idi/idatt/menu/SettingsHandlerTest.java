package edu.ntnu.idi.idatt.menu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class SettingsHandlerTest {

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
    void changeUsername_disallowDuplicateAndApplyNewName() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Alice", 1111);
        reg.addNewAuthor("Bob", 2222);

        // initial blank line consumed by changeUsername's first nextLine()
        String input = "\nBob\nAliceNew\n\n"; // try duplicate 'Bob' then valid 'AliceNew'
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        SettingsHandler settings = new SettingsHandler(scanner, reg, controller);
        settings.authorName = "Alice";

        settings.changeUsername();

        assertNull(reg.getAuthorByName("Alice"));
        assertNotNull(reg.getAuthorByName("AliceNew"));
        assertEquals(1111, reg.getAuthorByName("AliceNew").getPin());
    }

    @Test
    void deleteAccount_confirmsDeletion() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Charlie", 3333);

        String input = "Charlie\n"; // confirm deletion
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        SettingsHandler settings = new SettingsHandler(scanner, reg, controller);
        settings.authorName = "Charlie";

        boolean deleted = settings.deleteAccount();

        assertTrue(deleted);
        assertNull(reg.getAuthorByName("Charlie"));
    }

    @Test
    void changeUsername_emptyInput_keepsOldName() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Alice", 1111);

        // user presses enter then cancel by providing the same name
        String input = "\nAlice\n\n"; // blank then same name
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        SettingsHandler settings = new SettingsHandler(scanner, reg, controller);
        settings.authorName = "Alice";

        settings.changeUsername();

        // Name should remain unchanged
        assertNotNull(reg.getAuthorByName("Alice"));
        assertEquals(1111, reg.getAuthorByName("Alice").getPin());
    }

    @Test
    void deleteAccount_cancelledByUser_returnsFalseAndAuthorStays() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Charlie", 3333);

        // user types incorrect name then types 'cancel'
        String input = "WrongName\ncancel\n"; 
        Scanner scanner = new Scanner(input);
        TestLoginHandler controller = new TestLoginHandler(scanner, reg);
        SettingsHandler settings = new SettingsHandler(scanner, reg, controller);
        settings.authorName = "Charlie";

        boolean deleted = settings.deleteAccount();

        assertFalse(deleted);
        assertNotNull(reg.getAuthorByName("Charlie"));
    }
}
