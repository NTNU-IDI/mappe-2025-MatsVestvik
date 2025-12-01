package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class DayTest {

    private Day day;
    private final String testAuthor = "Mats";
    private final String testEntry = "Mats is pretty cool";
    private final int testRating = 5;
    private final String testTitle = "This is a title";
    private final String testDate = LocalDate.now().toString();

    @BeforeEach
    void setUp() {
        day = new Day(testAuthor, testEntry, testRating, testTitle);
    }

    @Test
    void testConstructorWithAuthor() {
        assertEquals(testEntry, day.getContent());
        assertEquals(testRating, day.getRating());
        assertNotNull(day.getDate());
        assertEquals(testTitle, day.getTitle());
    }

    @Test
    void testConstructorWithId() {

        Day specificDay = new Day(testDate, testEntry, testRating, testTitle);

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
        assertTrue(day.containsKeyword("Mats"));
        assertTrue(day.containsKeyword("is")); // case insensitive
        assertTrue(day.containsKeyword("pretty"));
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
    void testGettersConsistency() {
        assertEquals(testEntry, day.getContent());
        assertEquals(testRating, day.getRating());
        assertNotNull(day.getDate());
    }
}