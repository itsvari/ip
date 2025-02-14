package songbird.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DatesFound;
import com.zoho.hawking.language.english.model.ParserOutput;

import songbird.exception.SongbirdMalformedCommandException;

/**
 * Helper class to parse date/time strings into LocalDate or LocalDateTime objects.
 * Allows multiple input formats. Outputs are always in ISO date/time.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class DateTimeParser {
    private static final HawkingTimeParser PARSER;
    private static final HawkingConfiguration CONFIGURATION;
    private static final String LANGUAGE = "eng";

    static {
        PARSER = new HawkingTimeParser();
        CONFIGURATION = new HawkingConfiguration();
        CONFIGURATION.setTimeZone(ZoneId.systemDefault().getId());
        CONFIGURATION.setMaxParseDate(1); // only parse one date
        // dummy sentence to load parser model into RAM first for faster parsing later
        PARSER.parse("what are you doing tomorrow?", new Date(), new HawkingConfiguration(),"eng");
    }

    /**
     * Parses the input string into a LocalDateTime object using natural language processing.
     * Examples of valid inputs:
     * - "tomorrow at 2pm"
     * - "next monday at 3:30pm"
     * - "in 3 days"
     * - "next week"
     * - Standard date/time formats are still supported
     *
     * @param input The input string to be parsed.
     * @return The LocalDateTime object parsed from the input string.
     * @throws SongbirdMalformedCommandException If the input string cannot be parsed into a LocalDateTime object.
     */
    public static LocalDateTime parseDateTime(String input) throws SongbirdMalformedCommandException {
        Date referenceDate = new Date();
        try {
            DatesFound datesFound = PARSER.parse(input, referenceDate, CONFIGURATION, LANGUAGE);
            // should only get one parsed date, but it still returns a list, so just use a for loop to get the first one
            for (ParserOutput dateGroup : datesFound.getParserOutputs()) {
                /* we need to convert the Date object to LocalDateTime, but Hawking gives a joda DateTime object and
                   not a java.time.LocalDateTime object, so we extract its ISO string representation and *then*
                   convert that into a LocalDateTime object. yes, this is intensely frustrating.*/
                return LocalDateTime.parse(dateGroup.getDateRange().getStart().toString(),
                        DateTimeFormatter.ISO_DATE_TIME);
            }
        } catch (Exception e) {
            throw new SongbirdMalformedCommandException("Unable to parse date/time: " + input);
        }
        return null; // should never get here
    }
}
