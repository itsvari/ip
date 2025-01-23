package songbird.exception;

/**
 * Represents an exception that is thrown when an error occurs in the Songbird program.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class SongbirdException extends Exception {
    /**
     * Constructor for the SongbirdException class. Initializes the exception with the given message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public SongbirdException(String message) {
        super(message);
    }
}
