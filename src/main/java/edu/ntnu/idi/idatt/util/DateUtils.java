package edu.ntnu.idi.idatt.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Small utility for parsing and validating ISO dates (YYYY-MM-DD).
 */
public final class DateUtils {
    private DateUtils() {
        // utility class
    }

    /**
     * Parse an ISO date string (YYYY-MM-DD) into a {@link LocalDate}.
     * Throws IllegalArgumentException when the input is null or not a valid date.
     */
    public static LocalDate parseIsoDate(String isoDate) {
        if (isoDate == null) {
            throw new IllegalArgumentException("Date string is null");
        }
        try {
            return LocalDate.parse(isoDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected YYYY-MM-DD: " + isoDate, e);
        }
    }

    /**
     * Returns true when the provided string can be parsed as an ISO date (YYYY-MM-DD).
     */
    public static boolean isValidIsoDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) return false;
        try {
            LocalDate.parse(isoDate, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
