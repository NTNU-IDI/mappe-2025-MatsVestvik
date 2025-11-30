package edu.ntnu.idi.idatt.BusinessLogic;

import edu.ntnu.idi.idatt.util.ScannerManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ValidEntryTest {

    private InputStream originalSystemIn;
    private Scanner originalScanner;

    @BeforeEach
    void setUp() {
        // Save the original System.in
        originalSystemIn = System.in;
        // Save the original scanner
        originalScanner = ScannerManager.getScanner();
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.in
        System.setIn(originalSystemIn);
        // Restore the original scanner using reflection
        resetScannerManager(originalScanner);
    }

    @Test
    void printValidEntry_validInputWithoutPipe_returnsEntry() {
        // Arrange
        String input = "This is a valid diary entry\n";
        setScannerForTesting(input);

        // Act
        String result = ValidEntry.printValidEntry();

        // Assert
        assertEquals("This is a valid diary entry", result);
    }

    @Test
    void printValidEntry_inputWithPipe_rejectsAndPromptsAgain() {
        // Arrange
        String input = "Invalid|entry\nValid entry without pipe\n";
        setScannerForTesting(input);

        // Act
        String result = ValidEntry.printValidEntry();

        // Assert
        assertEquals("Valid entry without pipe", result);
    }

    @Test
    void printValidEntry_multipleInvalidEntries_acceptsFirstValid() {
        // Arrange
        String input = "First|try\nSecond|attempt\nThird|time\nFinally valid\n";
        setScannerForTesting(input);

        // Act
        String result = ValidEntry.printValidEntry();

        // Assert
        assertEquals("Finally valid", result);
    }

    @Test
    void printValidEntry_emptyInput_returnsEmptyString() {
        // Arrange
        String input = "\n";
        setScannerForTesting(input);

        // Act
        String result = ValidEntry.printValidEntry();

        // Assert
        assertEquals("", result);
    }

    @Test
    void printValidEntry_whitespaceInput_returnsWhitespace() {
        // Arrange
        String input = "   entry with spaces   \n";
        setScannerForTesting(input);

        // Act
        String result = ValidEntry.printValidEntry();

        // Assert
        assertEquals("   entry with spaces   ", result);
    }

    // Helper method to set up the scanner for testing
    private void setScannerForTesting(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        // Reset the ScannerManager to use the new System.in
        resetScannerManager(new Scanner(System.in));
    }

    // Helper method to reset the ScannerManager's static scanner using reflection
    private void resetScannerManager(Scanner newScanner) {
        try {
            Field scannerField = ScannerManager.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(null, newScanner);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset ScannerManager", e);
        }
    }
}