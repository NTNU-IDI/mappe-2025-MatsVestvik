package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.objects.Day;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DayTest {
    
    private Day day;
    private final String TEST_ENTRY = "Test entry content";
    
    @BeforeEach
    void setUp() {
        day = new Day("testId", "2023-10-15", TEST_ENTRY, 0);
    }
    
    @Test
    void testConstructorWithAuthor() {
        Day dayWithAuthor = new Day("testUser", "New entry", 0);
        assertNotNull(dayWithAuthor.getId());
        assertEquals("New entry", dayWithAuthor.getContent());
    }
    
    @Test
    void testGetContent() {
        assertEquals(TEST_ENTRY, day.getContent());
    }
    
    @Test
    void testSetEntry() {
        day.setEntry("Updated content");
        assertEquals("Updated content", day.getContent());
    }
}