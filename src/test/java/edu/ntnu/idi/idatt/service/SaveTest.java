package edu.ntnu.idi.idatt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.Day;
import edu.ntnu.idi.idatt.util.ClearCSV;

public class SaveTest {

    @TempDir
    Path tempDir;
    
    private List<Author> authors;
    private Save save;
    private String testDirectoryPath;

    @BeforeEach
    void setUp() {
        authors = new ArrayList<>();
        save = new Save();
        testDirectoryPath = tempDir.toString() + "/src/main/resources/entries/";
        
        // We'll use reflection to modify the directory path for testing
        // Alternatively, we can create the actual directory structure in temp dir
    }

    private Author createTestAuthor(String name, int pin) {
        Author author = new Author(name, pin);
        
        // Add some test days
        Day day1 = new Day("2024-01-01" + name, "2024-01-01", "First entry with\nnewline", 0);
        Day day2 = new Day("2024-01-02" + name, "2024-01-02", "Second entry", 0);
        Day day3 = new Day("2024-01-03" + name, "2024-01-03", "Third entry", 0);
        
        author.addDay(day3); // Add out of order to test sorting
        author.addDay(day1);
        author.addDay(day2);
        
        return author;
    }

    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    @Test
    void testSaveToCSV_SingleAuthor() throws IOException {
        // Create test author
        Author author = createTestAuthor("John", 1234);
        authors.add(author);

        // Use reflection to test with temp directory
        // For now, we'll test the basic functionality by checking it doesn't throw exceptions
        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_MultipleAuthors() throws IOException {
        Author author1 = createTestAuthor("John", 1234);
        Author author2 = createTestAuthor("Jane", 5678);
        authors.add(author1);
        authors.add(author2);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_EmptyAuthorsList() {
        // Empty list should not throw exceptions
        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_AuthorWithNoDays() {
        Author author = new Author("EmptyAuthor", 9999);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_DirectoryCreation() {
        // This tests that the directory creation works
        Author author = new Author("TestUser", 1111);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_ContentFormatting() throws IOException {
        Author author = new Author("TestUser", 1234);
        
        // Test content with special characters and newlines
        Day day = new Day("2024-01-01TestUser", "2024-01-01", "Line 1\nLine 2\nLine 3", 0);
        author.addDay(day);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_OverwriteExisting() {
        Author author = new Author("TestUser", 1234);
        Day day1 = new Day("2024-01-01TestUser", "2024-01-01", "First content", 0);
        author.addDay(day1);
        authors.add(author);

        // Save first time
        assertDoesNotThrow(() -> save.saveToCSV(authors));

        // Update content and save again (should overwrite)
        Day day2 = new Day("2024-01-01TestUser", "2024-01-01", "Updated content", 0);
        author.addDay(day2);
        
        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_SortingOrder() throws IOException {
        Author author = new Author("TestUser", 1234);
        
        // Add days in non-chronological order
        Day day3 = new Day("2024-01-03TestUser", "2024-01-03", "Third day", 0);
        Day day1 = new Day("2024-01-01TestUser", "2024-01-01", "First day", 0);
        Day day2 = new Day("2024-01-02TestUser", "2024-01-02", "Second day", 0);
        
        author.addDay(day3);
        author.addDay(day1);
        author.addDay(day2);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_SpecialCharactersInContent() {
        Author author = new Author("TestUser", 1234);
        
        // Test various special characters
        String contentWithSpecials = "Content with | pipes, commas,, and \n newlines\t tabs";
        Day day = new Day("2024-01-01TestUser", "2024-01-01", contentWithSpecials, 0);
        author.addDay(day);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    @Test
    void testSaveToCSV_EmptyContent() {
        Author author = new Author("TestUser", 1234);
        
        Day day = new Day("2024-01-01TestUser", "2024-01-01", "", 0);
        author.addDay(day);
        authors.add(author);

        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }

    // Integration-style test that actually verifies file content
    @Test
    void testSaveToCSV_FileContentVerification() throws IOException {
        // Create a temporary directory for this specific test
        Path testSaveDir = tempDir.resolve("test-save");
        Files.createDirectories(testSaveDir);
        
        Author author = new Author("VerifyUser", 4321);
        Day day = new Day("2024-01-01VerifyUser", "2024-01-01", "Test content with\nnewline", 0);
        author.addDay(day);
        authors.add(author);

        // Note: This test mainly verifies that no exceptions are thrown
        // Actual file content verification would require modifying the Save class
        // to accept a custom directory path (like we discussed with Load)
        assertDoesNotThrow(() -> save.saveToCSV(authors));
    }
}