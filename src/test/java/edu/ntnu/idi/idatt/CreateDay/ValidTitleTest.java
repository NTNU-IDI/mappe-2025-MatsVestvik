package edu.ntnu.idi.idatt.CreateDay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidTitleTest {
    @Test
    void isValidTitle_acceptsNormalTitle() {
        assertEquals("Hello", ValidTitle.isValidTitle("Hello"));
    }

    @Test
    void isValidTitle_rejectsEmpty() {
        assertEquals("-1", ValidTitle.isValidTitle(""));
    }

    @Test
    void isValidTitle_rejectsIllegalChars() {
        assertEquals("-1", ValidTitle.isValidTitle("Bad|title"));
        assertEquals("-1", ValidTitle.isValidTitle("Title\nNewline"));
    }

    @Test
    void isValidTitle_nullOrWhitespace_returnsMinusOne() {
        assertEquals("-1", ValidTitle.isValidTitle(null));
        assertEquals("-1", ValidTitle.isValidTitle("   "));
    }

    @Test
    void isValidTitle_boundaryLength_accepts35_rejects36() {
        // 35 characters should be accepted
        String thirtyFive = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 35 a's
        assertEquals(thirtyFive, ValidTitle.isValidTitle(thirtyFive));

        // 36 characters should be rejected
        String thirtySix = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 36 a's
        assertEquals("-1", ValidTitle.isValidTitle(thirtySix));
    }
}
