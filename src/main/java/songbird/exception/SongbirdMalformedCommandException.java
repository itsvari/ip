package songbird.exception;

/**
 * Represents an exception that is thrown when a malformed command is entered by the user.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class SongbirdMalformedCommandException extends SongbirdException {
    /**
     * Constructor for the SongbirdMalformedCommandException class. Initializes the exception with the given message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public SongbirdMalformedCommandException(String message) {
        super("That command was not formatted correctly: " + message);
    }
}
