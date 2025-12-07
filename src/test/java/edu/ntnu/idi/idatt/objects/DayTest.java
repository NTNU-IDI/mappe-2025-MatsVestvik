package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for {@link edu.ntnu.idi.idatt.objects.Day}.
 *
 * Verifies constructor behavior, getters, keyword matching and that setters
 * enforce validation rules (e.g. rating range, disallowed characters in entry/title).
 */
class DayTest {

    private Day day;
    private final String testEntry = "Mats is pretty cool";
    private final int testRating = 5;
    private final String testTitle = "This is a title";
    private final String testDate = LocalDate.now().toString();

    @BeforeEach
    void setUp() {
        day = new Day(testDate, testEntry, testRating, testTitle);
    }

    @Test
    /**
     * Constructor should store provided date, entry, rating and title.
     */
    void testConstructorWithAuthor() {
        assertEquals(testEntry, day.getEntry());
        assertEquals(testRating, day.getRating());
        assertEquals(testDate, day.getDate());
        assertEquals(testTitle, day.getTitle());
    }

    @Test
    /**
     * Explicit date constructor should set the date field to the provided value.
     */
    void testConstructorWithId() {

        Day specificDay = new Day(testDate, testEntry, testRating, testTitle);

        assertEquals(testDate, specificDay.getDate());
        assertEquals(testEntry, specificDay.getEntry());
        assertEquals(testRating, specificDay.getRating());
    }

    @Test
    /**
     * Setting a valid rating should update the rating value.
     */
    void testSetRating() {
        int newRating = 9;
        day.setRating(newRating);
        assertEquals(newRating, day.getRating());
    }

    @Test
    /**
     * Setting a valid entry should update the entry text.
     */
    void testSetEntry() {
        String newEntry = "Updated entry content";
        day.setEntry(newEntry);
        assertEquals(newEntry, day.getEntry());
    }

    @Test
    /**
     * Keyword matching should be case-insensitive and return true for contained words.
     */
    void testContainsKeyword() {
        assertTrue(day.containsKeyword("Mats"));
        assertTrue(day.containsKeyword("is")); // case insensitive
        assertTrue(day.containsKeyword("pretty"));
    }

    @Test
    /**
     * Non-existing keywords should return false.
     */
    void testContainsKeywordNotFound() {
        assertFalse(day.containsKeyword("nonexistent"));
        assertFalse(day.containsKeyword("xyz"));
    }

    @Test
    /**
     * Null or empty keywords return false.
     */
    void testContainsKeywordWithNull() {
        assertFalse(day.containsKeyword(null));
    }

    @Test
    void testContainsKeywordWithEmpty() {
        assertFalse(day.containsKeyword(""));
    }

    @Test
    /**
     * Basic getter consistency checks.
     */
    void testGettersConsistency() {
        assertEquals(testEntry, day.getEntry());
        assertEquals(testRating, day.getRating());
        assertNotNull(day.getDate());
    }

    @Test
    /**
     * Invalid rating values outside [1,10] should throw IllegalArgumentException.
     */
    void testSetRating_invalidLow_throws() {
        assertThrows(IllegalArgumentException.class, () -> day.setRating(0));
    }

    @Test
    void testSetRating_invalidHigh_throws() {
        assertThrows(IllegalArgumentException.class, () -> day.setRating(11));
    }

    @Test
    /**
     * Entries containing the '|' character are invalid and should throw.
     */
    void testSetEntry_containsPipe_throws() {
        assertThrows(IllegalArgumentException.class, () -> day.setEntry("Bad|entry"));
    }

    @Test
    /**
     * Title setter should reject null values.
     */
    void testSetTitle_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> day.setTitle(null));
    }

    @Test
    /**
     * Title exceeding the maximum allowed length should throw.
     */
    void testSetTitle_tooLong_throws() {
        String tooLong = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 36 chars
        assertTrue(tooLong.length() > 35);
        assertThrows(IllegalArgumentException.class, () -> day.setTitle(tooLong));
    }

    @Test
    /**
     * Titles containing the '|' character are invalid.
     */
    void testSetTitle_containsPipe_throws() {
        assertThrows(IllegalArgumentException.class, () -> day.setTitle("Bad|title"));
    }
}