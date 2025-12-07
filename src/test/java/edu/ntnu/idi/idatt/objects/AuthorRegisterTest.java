package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Unit tests for {@link AuthorRegister}.
 */
public class AuthorRegisterTest {

    private AuthorRegister register;

    @BeforeEach
    void setUp() {
        register = new AuthorRegister();
    }

    @Test
    void constructor_initializesEmptyAuthorsList() {
        assertTrue(register.getAuthors().isEmpty());
    }

    @Test
    void addNewAuthor_createsAuthorAndStoresIt() {
        register.addNewAuthor("John", 1234);

        List<Author> authors = register.getAuthors();
        assertEquals(1, authors.size());
        assertEquals("John", authors.get(0).getName());
        assertEquals(1234, authors.get(0).getPin());
    }

    @Test
    void addNewAuthor_duplicateNameIsIgnored() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("John", 9999);

        assertEquals(1, register.getAuthors().size());
    }

    @Test
    void addDay_and_searchDays_behavior() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Entry", 5, "title");

        assertTrue(register.searchDays("John", LocalDate.of(2024, 1, 1)));
        assertFalse(register.searchDays("John", LocalDate.of(2024, 1, 2)));
    }

    @Test
    void addDay_nonExistingAuthor_doesNotThrow() {
        assertDoesNotThrow(() -> register.addDay("Nope", "2024-01-01", "Entry", 1, "title"));
    }

    @Test
    void addDayToday_usesTodaysDate() {
        register.addNewAuthor("John", 1234);
        String today = LocalDate.now().toString();
        register.addDayToday("John", "Today", 3, "title");

        Author a = register.getAuthorByName("John");
        assertNotNull(a.getDayByDate(today));
        assertEquals("Today", a.readDay(today));
    }

    @Test
    void deleteAuthor_removesAuthor() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);

        register.deleteAuthor("John");
        assertEquals(1, register.getAuthors().size());
        assertNull(register.getAuthorByName("John"));
        assertNotNull(register.getAuthorByName("Jane"));
    }

    @Test
    void getAuthorName_and_getAuthorPos_behavior() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);

        assertEquals("John", register.getAuthorName(0));
        assertEquals("Jane", register.getAuthorName(1));
        assertEquals(1, register.getAuthorPos("John"));
        assertEquals(2, register.getAuthorPos("Jane"));
        assertEquals(3, register.getAuthorPos("Non"));
    }

    @Test
    void editDay_updatesOrCreatesDay() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Original", 1, "title");
        register.editDay("2024-01-01", "Edited", "John", 2, "title");

        assertEquals("Edited", register.getAuthorByName("John").readDay("2024-01-01"));

        // editing a non-existing day should not throw
        assertDoesNotThrow(() -> register.editDay("2024-02-02", "New", "John", 5, "title"));
    }

    @Test
    void getAuthors_returnsMutableList_currentImplementation() {
        register.addNewAuthor("John", 1234);
        List<Author> authors = register.getAuthors();

        int original = authors.size();
        if (!authors.isEmpty()) authors.remove(0);

        assertEquals(Math.max(0, original - 1), register.getAuthors().size());
    }

    @Test
    void searchEntries_and_searchEntriesInTimeSpan_behavior() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        register.addDay("John", "2024-01-01", "I love coffee", 5, "t");
        register.addDay("Jane", "2024-01-02", "Coffee is great", 6, "t");

        java.util.List<Day> results = register.searchEntries("coffee");
        assertEquals(2, results.size());
        assertEquals("John", register.getAuthorOfDay(results.get(0)));
        assertEquals("Jane", register.getAuthorOfDay(results.get(1)));

        // time span search (blank keyword -> include all within range)
        register.addDay("John", "2024-01-03", "Entry", 1, "t");
        java.util.List<Day> span = register.searchEntriesInTimeSpan("", "2024-01-01", "2024-01-02");
        assertEquals(2, span.size());
    }
}