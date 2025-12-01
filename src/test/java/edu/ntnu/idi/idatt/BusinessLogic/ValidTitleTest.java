package edu.ntnu.idi.idatt.BusinessLogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidTitleTest {
    @Test
    void isValidTitle_acceptsNormalTitle() {
        assertEquals("Hello", ValidTitle.isValidTitle("Hello"));
    }

    @Test
    void isValidTitle_rejectsEmptyOrTooLong() {
        assertEquals("-1", ValidTitle.isValidTitle(""));
        assertEquals("-1", ValidTitle.isValidTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    void isValidTitle_rejectsIllegalChars() {
        assertEquals("-1", ValidTitle.isValidTitle("Bad|title"));
        assertEquals("-1", ValidTitle.isValidTitle("Title\nNewline"));
    }
}
