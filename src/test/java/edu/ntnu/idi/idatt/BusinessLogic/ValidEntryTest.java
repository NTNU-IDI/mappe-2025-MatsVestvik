package edu.ntnu.idi.idatt.BusinessLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ValidEntryTest {
    
    private InputStream originalSystemIn;

    @BeforeEach
    public void saveSystemIn() {
        originalSystemIn = System.in;
    }

    @Test
    public void testValidEntryWithoutPipe() {
        // Simulate user input: "Today was a good day"
        String input = "Today was a good day";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Today was a good day", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryWithPipeThenValid() {
        // Simulate user input with pipe first, then valid input
        String input = "Bad|input\nGood input";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Good input", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryMultiplePipesThenValid() {
        // Simulate multiple invalid inputs with pipes, then valid input
        String input = "First|try\nSecond|attempt\nThird|time\nFinally valid";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Finally valid", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryEmptyStringThenValid() {
        // Simulate empty input first, then valid input
        String input = "\nValid entry without pipe";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Valid entry without pipe", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryWithSpecialCharacters() {
        // Simulate input with special characters (except pipe)
        String input = "Today was great! @ 100% #awesome";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Today was great! @ 100% #awesome", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryImmediatelyValid() {
        // Test when first input is valid
        String input = "Perfectly valid entry";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("Perfectly valid entry", result);
        assertFalse(result.contains("|"));
    }

    @Test
    public void testValidEntryWithMultipleNewlines() {
        // Test with multiple newlines in the input stream
        String input = "|bad\n|also bad\n\nfinally good";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String result = ValidEntry.printValidEntry();
        
        assertEquals("finally good", result);
        assertFalse(result.contains("|"));
    }
}