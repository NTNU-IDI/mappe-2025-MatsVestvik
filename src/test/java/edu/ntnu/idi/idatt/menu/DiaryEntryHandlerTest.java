package edu.ntnu.idi.idatt.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.AuthorRegister;

@ExtendWith(MockitoExtension.class)
public class DiaryEntryHandlerTest {

    @Mock
    private AuthorRegister mockRegister;

    @Mock
    private Author mockAuthor;

    private DiaryEntryHandler handler;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    private Scanner createMockScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    private String getOutput() {
        return outputStream.toString();
    }

    @Test
    void testWriteTodaysEntry_NewEntry() {
        // Arrange
        String input = "Today's thoughts\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String today = LocalDate.now().toString();
        
        when(mockRegister.searchDays(authorName, LocalDate.now())).thenReturn(false);

        // Act
        handler.writeTodaysEntry(authorName);

        // Assert
        verify(mockRegister).addDay(authorName, today, "Today's thoughts", 0);
        assertTrue(getOutput().contains("Entry saved for today!"));
    }

    @Test
    void testWriteTodaysEntry_OverwriteExisting_ConfirmYes() {
        // Arrange
        String input = "y\nUpdated thoughts\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String today = LocalDate.now().toString();
        
        when(mockRegister.searchDays(authorName, LocalDate.now())).thenReturn(true);

        // Act
        handler.writeTodaysEntry(authorName);

        // Assert
        verify(mockRegister).addDay(authorName, today, "Updated thoughts", 0);
        assertTrue(getOutput().contains("Entry saved for today!"));
    }

    @Test
    void testWriteTodaysEntry_OverwriteExisting_ConfirmNo() {
        // Arrange
        String input = "n\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        
        when(mockRegister.searchDays(authorName, LocalDate.now())).thenReturn(true);

        // Act
        handler.writeTodaysEntry(authorName);

        // Assert
        verify(mockRegister, never()).addDay(anyString(), anyString(), anyString(), anyInt());
        assertFalse(getOutput().contains("Entry saved for today!"));
    }

    @Test
    void testWriteTodaysEntry_OverwriteExisting_InvalidInput() {
        // Arrange
        String input = "invalid\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        
        when(mockRegister.searchDays(authorName, LocalDate.now())).thenReturn(true);

        // Act
        handler.writeTodaysEntry(authorName);

        // Assert
        verify(mockRegister, never()).addDay(anyString(), anyString(), anyString(), anyInt());
        assertTrue(getOutput().contains("invalid input"));
    }

    @Test
    void testLookAtExistingDay_ExitImmediately() {
        // Arrange
        String input = "e\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        
        when(mockRegister.getAuthorByName(authorName)).thenReturn(mockAuthor);

        // Act
        handler.lookAtExistingDay(authorName);

        // Assert
        verify(mockAuthor).printAll();
        verify(mockRegister, never()).editDay(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testLookAtExistingDay_ViewAndEdit() {
        // Arrange
        String input = "2024-01-01\ne\nUpdated entry\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String date = "2024-01-01";
        
        when(mockRegister.getAuthorByName(authorName)).thenReturn(mockAuthor);
        when(mockAuthor.readDay(date)).thenReturn("Original entry");

        // Act
        handler.lookAtExistingDay(authorName);

        // Assert
        verify(mockRegister).editDay(date, "Updated entry", authorName, 0);
        assertTrue(getOutput().contains("Entry updated successfully!"));
    }

    @Test
    void testLookAtExistingDay_ViewAndBack() {
        // Arrange
        String input = "2024-01-01\nb\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String date = "2024-01-01";
        
        when(mockRegister.getAuthorByName(authorName)).thenReturn(mockAuthor);
        when(mockAuthor.readDay(date)).thenReturn("Original entry");

        // Act
        handler.lookAtExistingDay(authorName);

        // Assert
        verify(mockRegister, never()).editDay(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testLookAtExistingDay_ViewAndInvalidInput() {
        // Arrange
        String input = "2024-01-01\ninvalid\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String date = "2024-01-01";
        
        when(mockRegister.getAuthorByName(authorName)).thenReturn(mockAuthor);
        when(mockAuthor.readDay(date)).thenReturn("Original entry");

        // Act
        handler.lookAtExistingDay(authorName);

        // Assert
        verify(mockRegister, never()).editDay(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testAddSpecificDate_ValidInput() {
        // Arrange
        String input = "2024-01-15\nSpecific date thoughts\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String date = "2024-01-15";

        // Act
        handler.addSpecificDate(authorName);

        // Assert
        verify(mockRegister).addDay(authorName, date, "Specific date thoughts", 0);
        assertTrue(getOutput().contains("Entry saved for " + date + "!"));
    }

    @Test
    void testAddSpecificDate_EmptyContent() {
        // Arrange
        String input = "2024-01-15\n\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        String date = "2024-01-15";

        // Act
        handler.addSpecificDate(authorName);

        // Assert
        verify(mockRegister).addDay(authorName, date, "", 0);
        assertTrue(getOutput().contains("Entry saved for " + date + "!"));
    }

    @Test
    void testConstructor_Initialization() {
        // Arrange
        Scanner scanner = createMockScanner("");
        
        // Act
        handler = new DiaryEntryHandler(scanner, mockRegister);

        // Assert
        assertNotNull(handler);
    }

    @Test
    void testLookAtExistingDay_MultipleDates() {
        // Arrange
        String input = "2024-01-01\nb\n2024-01-02\ne\nSecond updated\n";
        Scanner scanner = createMockScanner(input);
        handler = new DiaryEntryHandler(scanner, mockRegister);
        
        String authorName = "John";
        
        when(mockRegister.getAuthorByName(authorName)).thenReturn(mockAuthor);
        when(mockAuthor.readDay("2024-01-01")).thenReturn("First entry");
        when(mockAuthor.readDay("2024-01-02")).thenReturn("Second entry");

        // Act
        handler.lookAtExistingDay(authorName);

        // Assert
        verify(mockRegister).editDay("2024-01-02", "Second updated", authorName, 0);
    }
}