package edu.ntnu.idi.idatt.BusinessLogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidTitleTest {

    @Test
    void isValidTitle_acceptsNonEmptyUnderMax() {
        assertTrue(ValidTitle.isValidTitle("My title"));
    }

    @Test
    void isValidTitle_rejectsEmptyOrTooLong() {
        assertFalse(ValidTitle.isValidTitle(""));
        // 31 chars should be rejected
        assertFalse(ValidTitle.isValidTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    void validateTitle_trimsAndReturns() {
        String input = "  Hello Title  ";
        String validated = ValidTitle.validateTitle(input);
        assertEquals("Hello Title", validated);
    }

    @Test
    void validateTitle_throwsForInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ValidTitle.validateTitle(""));
    }
}
