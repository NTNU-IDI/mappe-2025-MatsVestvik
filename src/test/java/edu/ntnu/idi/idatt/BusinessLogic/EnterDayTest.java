package edu.ntnu.idi.idatt.BusinessLogic;

import edu.ntnu.idi.idatt.objects.AuthorRegister;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class EnterDayTest {

    @Test
    void createDay_validInputs_addsDayToRegister() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Bob", 1234);
        String author = "Bob";
        String date = LocalDate.now().toString();
        String title = "My Title";
        String entry = "This is a diary entry.";
        int rating = 5;

        EnterDay.createDay(reg, author, date, title, entry, rating);

        // Validate day exists
        assertNotNull(reg.getAuthorByName(author).getDayByDate(date));
        assertEquals(title, reg.getAuthorByName(author).getDayByDate(date).getTitle());
        assertEquals(rating, reg.getAuthorByName(author).getDayByDate(date).getRating());
    }

    @Test
    void createDay_invalidRating_throws() {
        AuthorRegister reg = new AuthorRegister();
        reg.addNewAuthor("Bob", 1234);
        assertThrows(IllegalArgumentException.class, () -> EnterDay.createDay(reg, "Bob", "2020-01-01", "T", "E", 11));
    }
}
