package edu.ntnu.idi.idatt.BusinessLogic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ValidRatingTest {

    private final InputStream originalSystemIn = System.in;
    
    @BeforeEach
    void setUp() {
        // Backup original System.in if needed for other tests
    }
    
    @AfterEach
    void tearDown() {
        // Restore original System.in after each test
        System.setIn(originalSystemIn);
    }
    
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testValidRatingWithinRange() {
        // Test valid input: 5
        provideInput("5\n");
        int result = ValidRating.printValidRating();
        assertEquals(5, result);
    }

    @Test
    void testValidRatingLowerBoundary() {
        // Test lower boundary: 1
        provideInput("1\n");
        int result = ValidRating.printValidRating();
        assertEquals(1, result);
    }

    @Test
    void testValidRatingUpperBoundary() {
        // Test upper boundary: 10
        provideInput("10\n");
        int result = ValidRating.printValidRating();
        assertEquals(10, result);
    }

    @Test
    void testInvalidRatingBelowRangeThenValid() {
        // Test invalid input (0) then valid input (7)
        provideInput("0\n7\n");
        int result = ValidRating.printValidRating();
        assertEquals(7, result);
    }

    @Test
    void testInvalidRatingAboveRangeThenValid() {
        // Test invalid input (11) then valid input (3)
        provideInput("11\n3\n");
        int result = ValidRating.printValidRating();
        assertEquals(3, result);
    }

    @Test
    void testNonNumericInputThenValid() {
        // Test non-numeric input then valid input
        provideInput("abc\n5\n");
        int result = ValidRating.printValidRating();
        assertEquals(5, result);
    }

    @Test
    void testMultipleInvalidInputsThenValid() {
        // Test multiple invalid inputs (non-numeric, out of range) then valid
        provideInput("hello\n0\n15\n8\n");
        int result = ValidRating.printValidRating();
        assertEquals(8, result);
    }

    @Test
    void testDecimalInputThenValid() {
        // Test decimal input then valid input
        provideInput("5.5\n6\n");
        int result = ValidRating.printValidRating();
        assertEquals(6, result);
    }

    @Test
    void testNegativeInputThenValid() {
        // Test negative input then valid input
        provideInput("-5\n9\n");
        int result = ValidRating.printValidRating();
        assertEquals(9, result);
    }

    @Test
    void testBoundaryValuesMultipleAttempts() {
        // Test multiple boundary violations then valid boundary
        provideInput("0\n11\n1\n");
        int result = ValidRating.printValidRating();
        assertEquals(1, result);
    }
}