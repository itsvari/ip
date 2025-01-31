package songbird.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import songbird.exception.SongbirdMalformedCommandException;

/**
 * Helper class to parse date/time strings into LocalDate or LocalDateTime objects.
 * Allows multiple input formats. Outputs are always in ISO date/time.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class DateTimeParser {
    private static final String[] DATE_TIME_PATTERNS = {
        "yyyy-MM-dd HH:mm",
        "yyyy/MM/dd HH:mm",
        "yyyy-MM-dd H:mm",
        "yyyy/MM/dd H:mm",
        "yyyy-MM-dd HHmm",
        "yyyy/MM/dd HHmm",
        "yyyy-MM-dd HH",
        "yyyy/MM/dd HH",
        "yyyy-MM-dd H",
        "yyyy/MM/dd H",
        "yyyy-MM-dd hh:mm a",
        "yyyy/MM/dd hh:mm a",
        "yyyy-MM-dd hh:mma",
        "yyyy/MM/dd hh:mma",
        "yyyy-MM-dd h:mma",
        "yyyy/MM/dd h:mma",
        "yyyy-MM-dd h:mm a",
        "yyyy/MM/dd h:mm a",
        "yyyy-MM-dd hh a",
        "yyyy/MM/dd hh a",
        "yyyy-MM-dd h a",
        "yyyy/MM/dd h a",
        "dd-MM-yyyy HH:mm",
        "dd/MM/yyyy HH:mm",
        "dd-MM-yyyy H:mm",
        "dd/MM/yyyy H:mm",
        "dd-MM-yyyy HHmm",
        "dd/MM/yyyy HHmm",
        "dd-MM-yyyy HH",
        "dd/MM/yyyy HH",
        "dd-MM-yyyy H",
        "dd/MM/yyyy H",
        "dd-MM-yyyy hh:mm a",
        "dd/MM/yyyy hh:mm a",
        "dd-MM-yyyy hh:mma",
        "dd/MM/yyyy hh:mma",
        "dd-MM-yyyy h:mma",
        "dd/MM/yyyy h:mma",
        "dd-MM-yyyy h:mm a",
        "dd/MM/yyyy h:mm a",
        "dd-MM-yyyy hh a",
        "dd/MM/yyyy hh a",
        "dd-MM-yyyy h a",
        "dd/MM/yyyy h a",
        "MM/dd/yyyy HH:mm",
        "MM-dd-yyyy HH:mm",
        "MM/dd/yyyy H:mm",
        "MM-dd-yyyy H:mm",
        "MM/dd/yyyy HHmm",
        "MM-dd-yyyy HHmm",
        "MM/dd/yyyy HH",
        "MM-dd-yyyy HH",
        "MM/dd/yyyy H",
        "MM-dd-yyyy H",
        "MM/dd/yyyy hh:mm a",
        "MM-dd-yyyy hh:mm a",
        "MM/dd/yyyy hh:mma",
        "MM-dd-yyyy hh:mma",
        "MM/dd/yyyy h:mma",
        "MM-dd-yyyy h:mma",
        "MM/dd/yyyy h:mm a",
        "MM-dd-yyyy h:mm a",
        "MM/dd/yyyy hh a",
        "MM-dd-yyyy hh a",
        "MM/dd/yyyy h a",
        "MM-dd-yyyy h a"
    };

    private static final String[] DATE_PATTERNS = {
        "yyyy-MM-dd",
        "yyyy/MM/dd",
        "dd-MM-yyyy",
        "dd/MM/yyyy",
        "MM/dd/yyyy",
        "MM-dd-yyyy"
    };

    private static final String[] TIME_PATTERNS = {
        "HH:mm",
        "HHmm",
        "H:mm",
        "HH",
        "H",
        "hh:mm a",
        "hh:mma",
        "h:mma",
        "h:mm a",
        "hh a",
        "h a"
    };

    /**
     * Parses the input string into a LocalDateTime object, trying multiple formats.
     * Handles several cases:
     * 1. Full date-time string (e.g. "2025-09-30 14:00")
     * 2, Date-only string (e.g. "2025-09-30") - time will default to 23:59
     * 3. Time-only string (e.g. "14:00") - date will default to current date
     * 4. Special keywords "today" and "tomorrow" - time will default to 23:59
     * <p>
     * Supports both 24-hour and 12-hour time formats.
     *
     * @param input The input string to be parsed.
     * @return The LocalDateTime object parsed from the input string.
     * @throws SongbirdMalformedCommandException If the input string cannot be parsed into a LocalDateTime object.
     */
    public static LocalDateTime parseDateTime(String input) throws SongbirdMalformedCommandException {
        // try full date-time patterns
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                return LocalDateTime.parse(input, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // ignore and try next pattern
            }
        }

        // try parsing as time only (default to today's date)
        for (String pattern : TIME_PATTERNS) {
            try {
                LocalTime time = LocalTime.parse(input, DateTimeFormatter.ofPattern(pattern));
                return LocalDateTime.of(LocalDate.now(), time);
            } catch (DateTimeParseException e) {
                // ignore and try next pattern
            }
        }

        // try date-only patterns (default to 23:59)
        try {
            LocalDate date = parseDate(input);
            return date.atTime(23, 59);
        } catch (SongbirdMalformedCommandException e) {
            throw new SongbirdMalformedCommandException(
                    "Unable to parse date/time: " + input + "\n"
                            + "Valid formats include:\n"
                            + "- Full date and time (e.g., 2024-03-15 14:30)\n"
                            + "- Date only (e.g., 2024-03-15)\n"
                            + "- Time only (e.g., 14:30)");
        }
    }

    /**
     * Parses a string into a LocalDate, trying multiple formats.
     *
     * @param input The string containing date information.
     * @return A LocalDate object.
     * @throws SongbirdMalformedCommandException if no valid format can parse the input.
     */
    public static LocalDate parseDate(String input) throws SongbirdMalformedCommandException {
        // first try explicit date patterns
        for (String pattern : DATE_PATTERNS) {
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // ignore and try next pattern
            }
        }

        // handle special keywords
        return switch (input.toLowerCase()) {
            case "today" -> LocalDate.now();
            case "tomorrow" -> LocalDate.now().plusDays(1);
            default -> throw new SongbirdMalformedCommandException(
                    "Unable to parse date: " + input + "\n"
                            + "Valid formats include:\n"
                            + "- yyyy-MM-dd (e.g., 2024-03-15)\n"
                            + "- yyyy/MM/dd (e.g., 2024/03/15)\n"
                            + "- Special keywords: today, tomorrow");
        };
    }
}
