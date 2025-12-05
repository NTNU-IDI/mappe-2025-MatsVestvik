package edu.ntnu.idi.idatt.CreateDay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidEntryTest {
    @Test
    void isValidEntry_validShortString_returnsSame() {
        String ok = "Hello world";
        assertEquals(ok, ValidEntry.isValidEntry(ok));
    }

    @Test
    void isValidEntry_longString_isFormatted() {
        String longEntry = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 44 chars
        String formatted = ValidEntry.isValidEntry(longEntry);
        assertNotNull(formatted);
        assertTrue(formatted.contains("\n"));
        assertNotEquals(longEntry, formatted);
    }

    @Test
    void isValidEntry_invalidPipe_returnsMinusOne() {
        assertEquals("-1", ValidEntry.isValidEntry("Bad|entry"));
    }

    @Test
    void isValidEntry_onlyPipe_returnsMinusOne() {
        assertEquals("-1", ValidEntry.isValidEntry("|"));
    }

    @Test
    void isValidEntry_null_throwsException() {
        assertThrows(NullPointerException.class, () -> ValidEntry.isValidEntry(null));
    }
}