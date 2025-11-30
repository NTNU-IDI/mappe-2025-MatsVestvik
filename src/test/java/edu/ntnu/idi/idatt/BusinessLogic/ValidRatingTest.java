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

public class ValidRatingTest {

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
    void printValidRating_validInput_returnsRating() {
        // Arrange
        String input = "5\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(5, result);
    }

    @Test
    void printValidRating_minimumRating_returns1() {
        // Arrange
        String input = "1\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(1, result);
    }

    @Test
    void printValidRating_maximumRating_returns10() {
        // Arrange
        String input = "10\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(10, result);
    }

    @Test
    void printValidRating_belowMinimum_rejectsAndPromptsAgain() {
        // Arrange
        String input = "0\n5\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(5, result);
    }

    @Test
    void printValidRating_aboveMaximum_rejectsAndPromptsAgain() {
        // Arrange
        String input = "11\n8\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(8, result);
    }

    @Test
    void printValidRating_multipleInvalid_rejectsUntilValid() {
        // Arrange
        String input = "0\n11\n-5\n20\n7\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(7, result);
    }

    @Test
    void printValidRating_nonNumericInput_rejectsAndPromptsAgain() {
        // Arrange
        String input = "abc\nfive\n3\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(3, result);
    }

    @Test
    void printValidRating_mixedInvalidInput_rejectsUntilValid() {
        // Arrange
        String input = "abc\n0\n11\ntest\n5\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(5, result);
    }

    @Test
    void printValidRating_decimalInput_rejectsAndPromptsAgain() {
        // Arrange
        String input = "7.5\n8\n";
        setScannerForTesting(input);

        // Act
        int result = ValidRating.printValidRating();

        // Assert
        assertEquals(8, result);
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