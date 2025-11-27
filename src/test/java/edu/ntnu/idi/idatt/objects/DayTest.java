package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    private Day day;
    private final String TEST_AUTHOR = "testAuthor";
    private final String TEST_ENTRY = "This is a test entry";
    private final int TEST_RATING = 5;

    @BeforeEach
    void setUp() {
        day = new Day(TEST_AUTHOR, TEST_ENTRY, TEST_RATING);
    }

    @Test
    void testConstructorWithAuthor() {
        assertNotNull(day.getId());
        assertTrue(day.getId().contains(TEST_AUTHOR));
        assertEquals(TEST_ENTRY, day.getContent());
        assertEquals(TEST_RATING, day.getRating());
        assertNotNull(day.getDate());
    }

    @Test
    void testConstructorWithId() {
        String testId = "2024-01-01testAuthor";
        String testDate = "2024-01-01";
        String testEntry = "Specific date entry";
        int testRating = 8;

        Day specificDay = new Day(testId, testDate, testEntry, testRating);

        assertEquals(testId, specificDay.getId());
        assertEquals(testDate, specificDay.getDate());
        assertEquals(testEntry, specificDay.getContent());
        assertEquals(testRating, specificDay.getRating());
    }

    @Test
    void testSetRating() {
        int newRating = 9;
        day.setRating(newRating);
        assertEquals(newRating, day.getRating());
    }

    @Test
    void testSetEntry() {
        String newEntry = "Updated entry content";
        day.setEntry(newEntry);
        assertEquals(newEntry, day.getContent());
    }

    @Test
    void testContainsKeyword() {
        assertTrue(day.containsKeyword("test"));
        assertTrue(day.containsKeyword("TEST")); // case insensitive
        assertTrue(day.containsKeyword("entry"));
    }

    @Test
    void testContainsKeywordNotFound() {
        assertFalse(day.containsKeyword("nonexistent"));
        assertFalse(day.containsKeyword("xyz"));
    }

    @Test
    void testContainsKeywordWithNull() {
        assertFalse(day.containsKeyword(null));
    }

    @Test
    void testContainsKeywordWithEmpty() {
        assertFalse(day.containsKeyword(""));
    }

    @Test
    void testContainsKeywordWithNullContent() {
        Day emptyDay = new Day(TEST_AUTHOR, null, TEST_RATING);
        assertFalse(emptyDay.containsKeyword("test"));
    }

    @Test
    void testRatingBoundaries() {
        // Test minimum rating
        Day minRatingDay = new Day(TEST_AUTHOR, "Min rating", 1);
        assertEquals(1, minRatingDay.getRating());

        // Test maximum rating
        Day maxRatingDay = new Day(TEST_AUTHOR, "Max rating", 10);
        assertEquals(10, maxRatingDay.getRating());
    }

    @Test
    void testGettersConsistency() {
        assertEquals(TEST_ENTRY, day.getContent());
        assertEquals(TEST_RATING, day.getRating());
        assertNotNull(day.getDate());
        assertNotNull(day.getId());
    }
}