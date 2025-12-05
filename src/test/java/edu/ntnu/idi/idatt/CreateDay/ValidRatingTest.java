package edu.ntnu.idi.idatt.CreateDay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidRatingTest {

    @Test
    void isValidRating_acceptsValidNumericValues() {
        assertEquals(5, ValidRating.isValidRating("5"));
        assertEquals(1, ValidRating.isValidRating("1"));
        assertEquals(10, ValidRating.isValidRating("10"));
    }

    @Test
    void isValidRating_rejectsOutOfRangeOrInvalid() {
        assertEquals(-1, ValidRating.isValidRating("0"));
        assertEquals(-1, ValidRating.isValidRating("11"));
        assertEquals(-1, ValidRating.isValidRating("abc"));
        assertEquals(-1, ValidRating.isValidRating("7.5"));
    }

    @Test
    void isValidRating_emptyOrWhitespace_returnsMinusOne() {
        assertEquals(-1, ValidRating.isValidRating(""));
        assertEquals(-1, ValidRating.isValidRating("   "));
    }

    @Test
    void isValidRating_null_throwsException() {
        assertThrows(NullPointerException.class, () -> ValidRating.isValidRating(null));
    }

    @Test
    void isValidRating_negativeSign_returnsMinusOne() {
        assertEquals(-1, ValidRating.isValidRating("-3"));
    }
}