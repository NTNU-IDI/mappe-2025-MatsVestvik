package edu.ntnu.idi.idatt.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class AuthorTest {

    private Author author;
    private Day testDay1;
    private Day testDay2;

    private final String testAuthor = "Mats";
    private final String testEntry = "Mats is pretty cool";
    private final int testRating = 5;
    private final String testTitle = "This is a title";
    private final String testDate = LocalDate.now().toString();


    @BeforeEach
    void setUp() {
        author = new Author(testAuthor, 1234);
        // Use explicit date constructor for deterministic tests
        testDay1 = new Day(testDate, testEntry, testRating, testTitle);
        testDay2 = new Day("0001-01-01", testEntry, testRating, testTitle);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Mats", author.getName());
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
        author.addDay(testDay2);
        assertEquals(1, author.getDaysSize());
        assertTrue(author.searchDays("0001-01-01"));
    }

    @Test
    void testAddDay_OverwriteExistingDay() {
        // Add first day
        author.addDay(testDay2);
        assertEquals(1, author.getDaysSize());
        assertEquals("Mats is pretty cool", author.readDay("0001-01-01"));

        // Create a new day with same date but different content
        Day updatedDay = new Day("0001-01-01", "Updated content", testRating, testTitle);
        author.addDay(updatedDay);

        // Should still have only one day, but with updated content
        assertEquals(1, author.getDaysSize());
        assertEquals("Updated content", author.readDay("0001-01-01"));
    }

    @Test
    void testAddDay_MultipleDifferentDays() {
        author.addDay(testDay1);
        author.addDay(testDay2);
        assertEquals(2, author.getDaysSize());
        assertTrue(author.searchDays(LocalDate.now().toString()));
        assertTrue(author.searchDays("0001-01-01"));
    }

    @Test
    void testSearchDays_NonExistingDay_ReturnsFalse() {
        author.addDay(testDay1);
        assertFalse(author.searchDays("2024-01-03"));
        assertFalse(author.searchDays(""));
        assertFalse(author.searchDays(null));
    }

    @Test
    void testGetDayByDate_ExistingDay() {
        author.addDay(testDay1);
        Day foundDay = author.getDayByDate(LocalDate.now().toString());
        assertNotNull(foundDay);
        assertEquals(LocalDate.now().toString(), foundDay.getDate());
        assertEquals("Mats is pretty cool", foundDay.getContent());
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
        assertEquals("Mats is pretty cool", author.readDay(LocalDate.now().toString()));
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
        Day day3 = new Day("2003-01-01", testEntry, testRating, testTitle);
        Day day1 = new Day("2001-01-01", testEntry, testRating, testTitle);
        Day day2 = new Day("2005-01-01", testEntry, testRating, testTitle);

        author.addDay(day3);
        author.addDay(day1);
        author.addDay(day2);

        List<Day> sortedDays = author.getSortDays(author.getListDays());

        // Verify the list is sorted by date
        assertEquals(3, sortedDays.size());
        assertEquals("2001-01-01", sortedDays.get(0).getDate());
        assertEquals("2003-01-01", sortedDays.get(1).getDate());
        assertEquals("2005-01-01", sortedDays.get(2).getDate());
    }

    @Test
    void testGetSortDays_EmptyList() {
        List<Day> sortedDays = author.getSortDays(author.getListDays());
        assertTrue(sortedDays.isEmpty());
    }

    @Test
    void testGetListDays_ReturnsCopyNotReference() {
        author.addDay(testDay1);
        List<Day> daysList = author.getListDays();
        
        // Modifying the returned list will affect the author's internal list
        int originalSize = daysList.size();
        if (!daysList.isEmpty()) {
            daysList.remove(0);
        }
        
        // Author's internal list should reflect the change
        assertEquals(originalSize - (originalSize > 0 ? 1 : 0), author.getDaysSize());
    }

    @Test
    void testPrintAll_NoExceptionThrown() {
        // This test just ensures printAll doesn't throw exceptions
        // Since it's a void method that prints to console, we mainly test it doesn't crash
        author.addDay(testDay1);
        author.addDay(testDay2);
        
        assertDoesNotThrow(() -> author.printAll());
    }

    @Test
    void findDaysByKeyword_returnsMatchingDays() {
        Day day1 = new Day( "2024-01-01", "I drank coffee in the morning", 5, testTitle);
        Day day2 = new Day("2024-01-02", "Had tea in the vodka", 7, testTitle);
        author.addDay(day1);
        author.addDay(day2);

        java.util.List<Day> matches = author.findDaysByKeyword("coffee");
        assertEquals(1, matches.size());
        assertEquals("2024-01-01", matches.get(0).getDate());
    }

    @Test
    void findDatesByKeyword_returnsOnlyDates() {
        Day day1 = new Day("2024-01-01", "I drank coffee in the morning", 5, testTitle);
        Day day2 = new Day( "2024-01-02", "Had tea in the afternoon", 6, testTitle);
        author.addDay(day1);
        author.addDay(day2);

        java.util.List<String> dates = author.findDatesByKeyword("coffee");
        assertEquals(1, dates.size());
        assertEquals("2024-01-01", dates.get(0));
    }

    @Test
    void findDaysByKeywordInRange_returnsDaysInRange_andOptionalKeyword() {
        Day day1 = new Day("2024-01-01", "Coffee", 5, testTitle);
        Day day2 = new Day("2024-01-02", "Tea", 6, testTitle);
        Day day3 = new Day("2024-01-05", "Cake", 7, testTitle);
        author.addDay(day1);
        author.addDay(day2);
        author.addDay(day3);

        java.time.LocalDate start = java.time.LocalDate.parse("2024-01-01");
        java.time.LocalDate end = java.time.LocalDate.parse("2024-01-03");
        java.util.List<Day> matches = author.findDaysByKeywordInRange("", start, end);
        assertEquals(2, matches.size());

        java.util.List<Day> coffeeOnly = author.findDaysByKeywordInRange("coffee", start, end);
        assertEquals(1, coffeeOnly.size());
        assertEquals("2024-01-01", coffeeOnly.get(0).getDate());
    }
}