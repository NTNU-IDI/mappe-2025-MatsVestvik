package edu.ntnu.idi.idatt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.Day;

public class LoadTest {

    @TempDir
    Path tempDir;
    
    private List<Author> authors;
    private File testEntriesDir;

    @BeforeEach
    void setUp() throws IOException {
        authors = new ArrayList<>();
        testEntriesDir = tempDir.resolve("entries").toFile();
        testEntriesDir.mkdirs();
    }

    private void createTestCSVFile(String fileName, String content) throws IOException {
        File file = new File(testEntriesDir, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    @Test
    void testLoadAuthorsFromCSV_NoFiles() {
        // Test when no CSV files exist
        Load load = new Load(authors);
        assertEquals(0, authors.size());
    }

    @Test
    void testLoadAuthorsFromCSV_SingleAuthor() throws IOException {
        String csvContent = "# Author: John, PIN: 1234\n2024-01-01|First entry\n2024-01-02|Second entry";
        createTestCSVFile("John.csv", csvContent);

        // Use reflection to call the method directly since constructor calls both methods
        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);

        assertEquals(1, authors.size());
        Author author = authors.get(0);
        assertEquals("John", author.getName());
        assertEquals(1234, author.getPin());
    }

    @Test
    void testLoadAuthorsFromCSV_MultipleAuthors() throws IOException {
        createTestCSVFile("John.csv", "# Author: John, PIN: 1234\n2024-01-01|Entry 1");
        createTestCSVFile("Jane.csv", "# Author: Jane, PIN: 5678\n2024-01-01|Entry 2");
        createTestCSVFile("Bob.csv", "# Author: Bob, PIN: 9999\n2024-01-01|Entry 3");

        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);

        assertEquals(3, authors.size());
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("John") && a.getPin() == 1234));
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Jane") && a.getPin() == 5678));
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Bob") && a.getPin() == 9999));
    }

    @Test
    void testLoadAuthorsFromCSV_InvalidPinFormat() throws IOException {
        String csvContent = "# Author: John, PIN: invalid\n2024-01-01|First entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);

        assertEquals(1, authors.size());
        // Should use default PIN (1111) when PIN parsing fails
        assertEquals(1111, authors.get(0).getPin());
    }

    @Test
    void testLoadAuthorsFromCSV_NoPinInHeader() throws IOException {
        String csvContent = "# Just a comment\n2024-01-01|First entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);

        assertEquals(1, authors.size());
        // Should use default PIN when no PIN in header
        assertEquals(1111, authors.get(0).getPin());
    }

    @Test
    void testLoadDaysFromCSV_SingleAuthorWithEntries() throws IOException {
        // First create author
        Author author = new Author("John", 1234);
        authors.add(author);

        String csvContent = "# Author: John, PIN: 1234\n" +
                           "2024-01-01|First entry\\nwith newline\n" +
                           "2024-01-02|Second entry\n" +
                           "2024-01-03|Third entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadDaysFromCSV(authors);

        assertEquals(3, author.getDaysSize());
        assertEquals("First entry\nwith newline", author.readDay("2024-01-01"));
        assertEquals("Second entry", author.readDay("2024-01-02"));
        assertEquals("Third entry", author.readDay("2024-01-03"));
    }

    @Test
    void testLoadDaysFromCSV_MultipleAuthors() throws IOException {
        Author john = new Author("John", 1234);
        Author jane = new Author("Jane", 5678);
        authors.add(john);
        authors.add(jane);

        createTestCSVFile("John.csv", "# Author: John, PIN: 1234\n2024-01-01|John's entry");
        createTestCSVFile("Jane.csv", "# Author: Jane, PIN: 5678\n2024-01-01|Jane's entry");

        Load load = new Load(new ArrayList<>());
        load.loadDaysFromCSV(authors);

        assertEquals(1, john.getDaysSize());
        assertEquals(1, jane.getDaysSize());
        assertEquals("John's entry", john.readDay("2024-01-01"));
        assertEquals("Jane's entry", jane.readDay("2024-01-01"));
    }

    @Test
    void testLoadDaysFromCSV_InvalidLineFormat() throws IOException {
        Author author = new Author("John", 1234);
        authors.add(author);

        String csvContent = "# Author: John, PIN: 1234\n" +
                           "2024-01-01|Valid entry\n" +
                           "InvalidLineWithoutSeparator\n" +
                           "2024-01-02|Another valid entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadDaysFromCSV(authors);

        // Should load only the valid entries
        assertEquals(2, author.getDaysSize());
        assertEquals("Valid entry", author.readDay("2024-01-01"));
        assertEquals("Another valid entry", author.readDay("2024-01-02"));
    }

    @Test
    void testLoadDaysFromCSV_FileNotFound() {
        Author author = new Author("NonExistent", 1234);
        authors.add(author);

        Load load = new Load(new ArrayList<>());
        // Should not throw exception when file doesn't exist, just print error
        assertDoesNotThrow(() -> load.loadDaysFromCSV(authors));
    }

    @Test
    void testLoadDaysFromCSV_EmptyFile() throws IOException {
        Author author = new Author("John", 1234);
        authors.add(author);

        createTestCSVFile("John.csv", "# Author: John, PIN: 1234\n");
        
        Load load = new Load(new ArrayList<>());
        load.loadDaysFromCSV(authors);

        assertEquals(0, author.getDaysSize());
    }

    @Test
    void testAddDay_AuthorExists() {
        Author author = new Author("John", 1234);
        authors.add(author);

        Load load = new Load(new ArrayList<>());
        load.addDay(authors, "John", "2024-01-01", "Test content");

        assertEquals(1, author.getDaysSize());
        assertEquals("Test content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDay_AuthorNotExists() {
        Load load = new Load(new ArrayList<>());
        // Should not throw exception, just print error message
        assertDoesNotThrow(() -> load.addDay(authors, "NonExistent", "2024-01-01", "Test content"));
    }

    @Test
    void testAddDay_OverwriteExistingDay() {
        Author author = new Author("John", 1234);
        authors.add(author);

        Load load = new Load(new ArrayList<>());
        load.addDay(authors, "John", "2024-01-01", "First content");
        load.addDay(authors, "John", "2024-01-01", "Updated content");

        assertEquals(1, author.getDaysSize());
        assertEquals("Updated content", author.readDay("2024-01-01"));
    }

    @Test
    void testReadPinFromCSV_ValidPin() throws IOException {
        String csvContent = "# Author: John, PIN: 1234\n2024-01-01|Entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        File csvFile = new File(testEntriesDir, "John.csv");
        
        // Use reflection to test private method, or test through public interface
        // For now, we'll test through the public loadAuthorsFromCSV method
        load.loadAuthorsFromCSV(authors);
        
        assertEquals(1234, authors.get(0).getPin());
    }

    @Test
    void testReadPinFromCSV_PinWithSpaces() throws IOException {
        String csvContent = "# Author: John, PIN:  5678  \n2024-01-01|Entry";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);
        
        assertEquals(5678, authors.get(0).getPin());
    }

    @Test
    void testReadPinFromCSV_NoHeader() throws IOException {
        String csvContent = "2024-01-01|Entry without header";
        createTestCSVFile("John.csv", csvContent);

        Load load = new Load(new ArrayList<>());
        load.loadAuthorsFromCSV(authors);
        
        assertEquals(1111, authors.get(0).getPin()); // Default PIN
    }
}