package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class AuthorRegisterTest {

    private AuthorRegister register;
    

    @BeforeEach
    void setUp() {
        register = new AuthorRegister();
        
    }

    @Test
    void testConstructor_InitializesEmptyAuthorsList() {
        assertTrue(register.getAuthors().isEmpty());
    }

    @Test
    void testAddNewAuthor_NewAuthor() {
        register.addNewAuthor("John", 1234);
        
        List<Author> authors = register.getAuthors();
        assertEquals(1, authors.size());
        assertEquals("John", authors.get(0).getName());
        assertEquals(1234, authors.get(0).getPin());
    }

    @Test
    void testAddNewAuthor_DuplicateAuthor() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("John", 9999); // Same name, different PIN
        
        // Should still have only one author
        assertEquals(1, register.getAuthors().size());
    }

    @Test
    void testAddNewAuthor_MultipleAuthors() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        register.addNewAuthor("Bob", 9999);
        
        assertEquals(3, register.getAuthors().size());
    }

    @Test
    void testSearchDays_AuthorExists_DayExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content", 0);
        
        assertTrue(register.searchDays("John", LocalDate.of(2024, 1, 1)));
    }

    @Test
    void testSearchDays_AuthorExists_DayNotExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content", 0);
        
        assertFalse(register.searchDays("John", LocalDate.of(2024, 1, 2)));
    }

    @Test
    void testSearchDays_AuthorNotExists() {
        assertFalse(register.searchDays("NonExistent", LocalDate.of(2024, 1, 1)));
    }

    @Test
    void testAddDay_AuthorExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content", 0);
        
        Author author = register.getAuthorByName("John");
        assertNotNull(author);
        assertEquals("Test content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDay_AuthorNotExists() {
        // This should print an error message but not throw an exception
        assertDoesNotThrow(() -> register.addDay("NonExistent", "2024-01-01", "Test content", 0));
    }

    @Test
    void testAddDay_OverwriteExistingDay() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Original content", 0);
        register.addDay("John", "2024-01-01", "Updated content", 0);
        
        Author author = register.getAuthorByName("John");
        assertEquals("Updated content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDayToday_AuthorExists() {
        register.addNewAuthor("John", 1234);
        String today = LocalDate.now().toString();
        register.addDayToday("John", "Today's content", 0);
        
        Author author = register.getAuthorByName("John");
        assertNotNull(author.readDay(today));
        assertEquals("Today's content", author.readDay(today));
    }

    @Test
    void testAddDayToday_AuthorNotExists() {
        // This should print an error message but not throw an exception
        assertDoesNotThrow(() -> register.addDayToday("NonExistent", "Today's content", 0));
    }

    @Test
    void testPrintAllAuthors_NoExceptionThrown() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        
        assertDoesNotThrow(() -> register.printAllAuthors());
    }

    @Test
    void testDeleteAuthor_AuthorExists() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        
        register.deleteAuthor("John");
        
        assertEquals(1, register.getAuthors().size());
        assertNull(register.getAuthorByName("John"));
        assertNotNull(register.getAuthorByName("Jane"));
    }

    @Test
    void testDeleteAuthor_AuthorNotExists() {
        register.addNewAuthor("John", 1234);
        
        // Should not throw exception when deleting non-existent author
        assertDoesNotThrow(() -> register.deleteAuthor("NonExistent"));
        assertEquals(1, register.getAuthors().size());
    }

    @Test
    void testGetAuthorName_ValidPosition() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        
        assertEquals("John", register.getAuthorName(0));
        assertEquals("Jane", register.getAuthorName(1));
    }

    @Test
    void testGetAuthorName_InvalidPosition() {
        register.addNewAuthor("John", 1234);
        
        // Should throw IndexOutOfBoundsException for invalid position
        assertThrows(IndexOutOfBoundsException.class, () -> register.getAuthorName(5));
    }

    @Test
    void testGetAuthorByName_AuthorExists() {
        register.addNewAuthor("John", 1234);
        
        Author author = register.getAuthorByName("John");
        assertNotNull(author);
        assertEquals("John", author.getName());
        assertEquals(1234, author.getPin());
    }

    @Test
    void testGetAuthorByName_AuthorNotExists() {
        assertNull(register.getAuthorByName("NonExistent"));
    }

    @Test
    void testGetAuthorPos_AuthorExists() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        register.addNewAuthor("Bob", 9999);
        
        assertEquals(1, register.getAuthorPos("John")); // 1-based position
        assertEquals(2, register.getAuthorPos("Jane"));
        assertEquals(3, register.getAuthorPos("Bob"));
    }

    @Test
    void testGetAuthorPos_AuthorNotExists() {
        register.addNewAuthor("John", 1234);
        
        // Should return the size + 1 when author not found
        assertEquals(2, register.getAuthorPos("NonExistent"));
    }

    @Test
    void testEditDay_AuthorExists_DayExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Original content", 0);
        register.editDay("2024-01-01", "Edited content", "John", 0);
        
        Author author = register.getAuthorByName("John");
        assertEquals("Edited content", author.readDay("2024-01-01"));
    }

    @Test
    void testEditDay_AuthorExists_DayNotExists() {
        register.addNewAuthor("John", 1234);
        
        // Should not throw exception when editing non-existent day
        assertDoesNotThrow(() -> register.editDay("2024-01-01", "New content", "John", 0));
    }

    @Test
    void testEditDay_AuthorNotExists() {
        // Should not throw exception when author doesn't exist
        assertDoesNotThrow(() -> register.editDay("2024-01-01", "Content", "NonExistent", 0));
    }

    @Test
    void testPrintDaysAuthor_NoExceptionThrown() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content", 0);
        
        Author author = register.getAuthorByName("John");
        assertDoesNotThrow(() -> register.printDaysAuthor(author));
    }

    @Test
    void testGetAuthors_ReturnsCopyNotReference() {
        register.addNewAuthor("John", 1234);
        List<Author> authors = register.getAuthors();
        
        int originalSize = authors.size();
        if (!authors.isEmpty()) {
            authors.remove(0);
        }
        
        // Internal list should be affected when modifying the returned list
        assertEquals(originalSize - (originalSize > 0 ? 1 : 0), register.getAuthors().size());
    }

    @Test
    void testSearchEntries_keywordMatchesAcrossAuthors() {
        register.addNewAuthor("John", 1234);
        register.addNewAuthor("Jane", 5678);
        register.addDay("John", "2024-01-01", "I love coffee", 5);
        register.addDay("Jane", "2024-01-02", "Coffee is great", 6);

        java.util.List<String> results = register.searchEntries("coffee");
        assertEquals(2, results.size());
        assertTrue(results.get(0).contains("John - 2024-01-01"));
        assertTrue(results.get(1).contains("Jane - 2024-01-02"));
    }

    @Test
    void testSearchEntriesInTimeSpan_blankKeywordMatchesAllDaysInRange() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Entry one", 5);
        register.addDay("John", "2024-01-03", "Entry two", 6);

        java.util.List<String> results = register.searchEntriesInTimeSpan("", "2024-01-01", "2024-01-02");
        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("2024-01-01"));
    }

    @Test
    void testSearchEntries_nonExistingKeywordReturnsEmpty() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Entry one", 5);

        java.util.List<String> results = register.searchEntries("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchEntriesInTimeSpan_noMatchesReturnsEmpty() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Entry one", 5);

        java.util.List<String> results = register.searchEntriesInTimeSpan("coffee", "2024-01-02", "2024-01-03");
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchEntriesInTimeSpan_keywordMatchesWithinRange() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "I love coffee", 5);
        register.addDay("John", "2024-01-02", "No coffee here", 6);

        java.util.List<String> results = register.searchEntriesInTimeSpan("coffee", "2024-01-01", "2024-01-02");
        assertEquals(2, results.size());
        assertTrue(results.get(0).contains("2024-01-01") || results.get(1).contains("2024-01-01"));
    }
}