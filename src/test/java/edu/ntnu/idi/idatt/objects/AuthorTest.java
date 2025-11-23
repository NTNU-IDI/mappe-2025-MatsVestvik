package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AuthorTest {

    private Author author;
    private Day testDay1;
    private Day testDay2;

    @BeforeEach
    void setUp() {
        author = new Author("TestUser", 1234);
        testDay1 = new Day("TestUser", "First day of the year", 0);
        testDay2 = new Day("TestUser", "Second day of the year", 0);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("TestUser", author.getName());
        assertEquals(1234, author.getPin());
        assertEquals(0, author.getDaysSize());
        assertTrue(author.getListDays().isEmpty());
    }

    @Test
    void testCheckPin_CorrectPin_ReturnsTrue() {
        assertTrue(author.checkPin(1234));
    }

    @Test
    void testCheckPin_IncorrectPin_ReturnsFalse() {
        assertFalse(author.checkPin(9999));
        assertFalse(author.checkPin(0));
        assertFalse(author.checkPin(-1));
    }

    @Test
    void testSetPin() {
        author.setPin(5678);
        assertEquals(5678, author.getPin());
        assertTrue(author.checkPin(5678));
        assertFalse(author.checkPin(1234));
    }

    @Test
    void testSetName() {
        author.setName("NewUserName");
        assertEquals("NewUserName", author.getName());
    }

    @Test
    void testAddDay_NewDay() {
        author.addDay(testDay1);
        assertEquals(1, author.getDaysSize());
        assertTrue(author.searchDays("2024-01-01"));
    }

    @Test
    void testAddDay_OverwriteExistingDay() {
        // Add first day
        author.addDay(testDay1);
        assertEquals(1, author.getDaysSize());
        assertEquals("First day of the year", author.readDay("2024-01-01"));

        // Create a new day with same date but different content
        Day updatedDay = new Day("2024-01-01TestUser", "2024-01-01", "Updated content", 0);
        author.addDay(updatedDay);

        // Should still have only one day, but with updated content
        assertEquals(1, author.getDaysSize());
        assertEquals("Updated content", author.readDay("2024-01-01"));
    }

    @Test
    void testAddDay_MultipleDifferentDays() {
        author.addDay(testDay1);
        author.addDay(testDay2);
        assertEquals(2, author.getDaysSize());
        assertTrue(author.searchDays("2024-01-01"));
        assertTrue(author.searchDays("2024-01-02"));
    }

    @Test
    void testSearchDays_ExistingDay_ReturnsTrue() {
        author.addDay(testDay1);
        assertTrue(author.searchDays("2024-01-01"));
    }

    @Test
    void testSearchDays_NonExistingDay_ReturnsFalse() {
        author.addDay(testDay1);
        assertFalse(author.searchDays("2024-01-03"));
        assertFalse(author.searchDays(""));
        assertFalse(author.searchDays(null));
    }

    @Test
    void testSearchDays_EmptyDaysList_ReturnsFalse() {
        assertFalse(author.searchDays("2024-01-01"));
    }

    @Test
    void testGetDayByDate_ExistingDay() {
        author.addDay(testDay1);
        Day foundDay = author.getDayByDate("2024-01-01");
        assertNotNull(foundDay);
        assertEquals("2024-01-01", foundDay.getDate());
        assertEquals("First day of the year", foundDay.getContent());
    }

    @Test
    void testGetDayByDate_NonExistingDay() {
        author.addDay(testDay1);
        assertNull(author.getDayByDate("2024-01-03"));
        assertNull(author.getDayByDate(""));
        assertNull(author.getDayByDate(null));
    }

    @Test
    void testReadDay_ExistingDay() {
        author.addDay(testDay1);
        assertEquals("First day of the year", author.readDay("2024-01-01"));
    }

    @Test
    void testReadDay_NonExistingDay() {
        author.addDay(testDay1);
        assertNull(author.readDay("2024-01-03"));
        assertNull(author.readDay(""));
        assertNull(author.readDay(null));
    }

    @Test
    void testGetSortDays_UnsortedList() {
        // Add days in non-chronological order
        Day day3 = new Day("2024-01-03TestUser", "2024-01-03", "Content 3", 0);
        Day day1 = new Day("2024-01-01TestUser", "2024-01-01", "Content 1", 0);
        Day day2 = new Day("2024-01-02TestUser", "2024-01-02", "Content 2", 0);

        author.addDay(day3);
        author.addDay(day1);
        author.addDay(day2);

        List<Day> sortedDays = author.getSortDays(author.getListDays());

        // Verify the list is sorted by date
        assertEquals(3, sortedDays.size());
        assertEquals("2024-01-01", sortedDays.get(0).getDate());
        assertEquals("2024-01-02", sortedDays.get(1).getDate());
        assertEquals("2024-01-03", sortedDays.get(2).getDate());
    }

    @Test
    void testGetSortDays_EmptyList() {
        List<Day> sortedDays = author.getSortDays(author.getListDays());
        assertTrue(sortedDays.isEmpty());
    }

    @Test
    void testGetSortDays_SingleElement() {
        author.addDay(testDay1);
        List<Day> sortedDays = author.getSortDays(author.getListDays());
        assertEquals(1, sortedDays.size());
        assertEquals("2024-01-01", sortedDays.get(0).getDate());
    }

    @Test
    void testGetListDays_ReturnsCopyNotReference() {
        author.addDay(testDay1);
        List<Day> daysList = author.getListDays();
        
        // Modifying the returned list should not affect the author's internal list
        int originalSize = daysList.size();
        if (!daysList.isEmpty()) {
            daysList.remove(0);
        }
        
        // Author's internal list should remain unchanged
        assertEquals(originalSize, author.getDaysSize());
    }

    @Test
    void testPrintAll_NoExceptionThrown() {
        // This test just ensures printAll doesn't throw exceptions
        // Since it's a void method that prints to console, we mainly test it doesn't crash
        author.addDay(testDay1);
        author.addDay(testDay2);
        
        assertDoesNotThrow(() -> author.printAll());
    }
}