package edu.ntnu.idi.idatt.menu;

import edu.ntnu.idi.idatt.objects.AuthorRegister;
import edu.ntnu.idi.idatt.util.ScannerManager;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link edu.ntnu.idi.idatt.menu.LoginHandler}.
 *
 * Tests simulate user input via a {@link java.util.Scanner} and replace the
 * global {@link edu.ntnu.idi.idatt.util.ScannerManager} scanner so that
 * interactive menus can be exercised deterministically.
 */
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
        // ensure ScannerManager is reset each test
        resetScannerManager(new java.util.Scanner(System.in));
    }

    @AfterEach
    void tearDown() {
        // Restore ScannerManager to default
        resetScannerManager(new java.util.Scanner(System.in));
    }

    @Test
    /**
     * When the user enters a correct PIN and then selects the logout option,
     * {@code loginHandling()} should return false (no save/exit).
     */
    void loginHandling_correctPin_logoutReturnsFalse() {
        Scanner scanner = new Scanner("1234\n5\n"); // correct pin -> user menu -> logout
        // Inject scanner into ScannerManager used by UserMenuHandler
        resetScannerManager(scanner);
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice"); // simulate choosing the user

        boolean result = handler.loginHandling();
        assertFalse(result, "loginHandling should return false when user logs out");
    }

    @Test
    /**
     * When the user enters a correct PIN and chooses save & exit, the method
     * should return true to indicate the application should exit.
     */
    void loginHandling_correctPin_saveAndQuitReturnsTrue() {
        Scanner scanner = new Scanner("1234\n6\n"); // correct pin -> user menu -> save & exit
        resetScannerManager(scanner);
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice");

        boolean result = handler.loginHandling();
        assertTrue(result, "loginHandling should return true when user chose save & exit");
    }

    @Test
    /**
     * An incorrect PIN should cause {@code loginHandling()} to return false.
     */
    void loginHandling_incorrectPin_returnsFalse() {
        Scanner scanner = new Scanner("0000\n"); // incorrect pin
        resetScannerManager(scanner);
        TestLoginHandler handler = new TestLoginHandler(scanner, register);
        handler.setAuthorName("Alice");

        boolean result = handler.loginHandling();
        assertFalse(result, "loginHandling should return false for incorrect pin");
    }

    private void resetScannerManager(Scanner scanner) {
        try {
            Field f = ScannerManager.class.getDeclaredField("scanner");
            f.setAccessible(true);
            f.set(null, scanner);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
