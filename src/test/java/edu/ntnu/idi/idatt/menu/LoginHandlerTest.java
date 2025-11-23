package edu.ntnu.idi.idatt.menu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.objects.AuthorRegister;

public class LoginHandlerTest {

	static class TestLoginHandler extends LoginHandler {
		private boolean exited = false;

		public TestLoginHandler(Scanner scanner, AuthorRegister register) {
			super(scanner, register);
		}

		@Override
		public void exit() {
			exited = true; // avoid real saving/closing scanner in tests
		}

		public boolean isExited() {
			return exited;
		}
	}

	@Test
	void loginHandling_correctPin_logoutReturnsFalse() {
		AuthorRegister reg = new AuthorRegister();
		reg.addNewAuthor("Alice", 1234);

		String input = "1234\n5\n"; // enter pin then choose logout (5)
		Scanner scanner = new Scanner(input);
		TestLoginHandler menu = new TestLoginHandler(scanner, reg);
		LoginHandler handler = menu;

		handler.setAuthorName("Alice");
		boolean result = handler.loginHandling();
		assertFalse(result);
	}

	@Test
	void loginHandling_correctPin_saveAndQuitReturnsTrue() {
		AuthorRegister reg = new AuthorRegister();
		reg.addNewAuthor("Bob", 4321);

		String input = "4321\n6\n"; // enter pin then choose save and quit (6)
		Scanner scanner = new Scanner(input);
		TestLoginHandler menu = new TestLoginHandler(scanner, reg);
		LoginHandler handler = menu;

		handler.setAuthorName("Bob");
		boolean result = handler.loginHandling();
		assertTrue(result);
	}

	@Test
	void loginHandling_incorrectPin_returnsFalse() {
		AuthorRegister reg = new AuthorRegister();
		reg.addNewAuthor("Eve", 1111);

		String input = "0000\n"; // wrong pin
		Scanner scanner = new Scanner(input);
		TestLoginHandler menu = new TestLoginHandler(scanner, reg);
		LoginHandler handler = menu;

		handler.setAuthorName("Eve");
		boolean result = handler.loginHandling();
		assertFalse(result);
	}

	@Test
	void createNewUser_addsAuthorToRegister() {
		AuthorRegister reg = new AuthorRegister();

		// name, pin, then press enter to continue
		String input = "Charlie\n2468\n\n";
		Scanner scanner = new Scanner(input);
		TestLoginHandler menu = new TestLoginHandler(scanner, reg);
		LoginHandler handler = menu;

		handler.createNewUser();

		assertNotNull(reg.getAuthorByName("Charlie"));
		assertEquals(2468, reg.getAuthorByName("Charlie").getPin());
	}

}
