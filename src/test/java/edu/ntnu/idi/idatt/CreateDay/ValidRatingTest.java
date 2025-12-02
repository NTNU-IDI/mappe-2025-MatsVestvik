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
}