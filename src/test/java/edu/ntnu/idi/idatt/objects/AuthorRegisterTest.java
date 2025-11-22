package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class AuthorRegisterTest {

    private AuthorRegister register;
    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        register = new AuthorRegister();
        author1 = new Author("John", 1234);
        author2 = new Author("Jane", 5678);
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
        register.addDay("John", "2024-01-01", "Test content");
        
        assertTrue(register.searchDays("John", LocalDate.of(2024, 1, 1)));
    }

    @Test
    void testSearchDays_AuthorExists_DayNotExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content");
        
        assertFalse(register.searchDays("John", LocalDate.of(2024, 1, 2)));
    }

    @Test
    void testSearchDays_AuthorNotExists() {
        assertFalse(register.searchDays("NonExistent", LocalDate.of(2024, 1, 1)));
    }

    @Test
    void testAddDay_AuthorExists() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content");
        
        Author author = register.getAuthorByName("John");
        assertNotNull(author);
        assertEquals("Test content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDay_AuthorNotExists() {
        // This should print an error message but not throw an exception
        assertDoesNotThrow(() -> register.addDay("NonExistent", "2024-01-01", "Test content"));
    }

    @Test
    void testAddDay_OverwriteExistingDay() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Original content");
        register.addDay("John", "2024-01-01", "Updated content");
        
        Author author = register.getAuthorByName("John");
        assertEquals("Updated content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDayToday_AuthorExists() {
        register.addNewAuthor("John", 1234);
        String today = LocalDate.now().toString();
        register.addDayToday("John", "Today's content");
        
        Author author = register.getAuthorByName("John");
        assertNotNull(author.readDay(today));
        assertEquals("Today's content", author.readDay(today));
    }

    @Test
    void testAddDayToday_AuthorNotExists() {
        // This should print an error message but not throw an exception
        assertDoesNotThrow(() -> register.addDayToday("NonExistent", "Today's content"));
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
        register.addDay("John", "2024-01-01", "Original content");
        register.editDay("2024-01-01", "Edited content", "John");
        
        Author author = register.getAuthorByName("John");
        assertEquals("Edited content", author.readDay("2024-01-01"));
    }

    @Test
    void testEditDay_AuthorExists_DayNotExists() {
        register.addNewAuthor("John", 1234);
        
        // Should not throw exception when editing non-existent day
        assertDoesNotThrow(() -> register.editDay("2024-01-01", "New content", "John"));
    }

    @Test
    void testEditDay_AuthorNotExists() {
        // Should not throw exception when author doesn't exist
        assertDoesNotThrow(() -> register.editDay("2024-01-01", "Content", "NonExistent"));
    }

    @Test
    void testPrintDaysAuthor_NoExceptionThrown() {
        register.addNewAuthor("John", 1234);
        register.addDay("John", "2024-01-01", "Test content");
        
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
        
        // Internal list should remain unchanged
        assertEquals(originalSize, register.getAuthors().size());
    }
}