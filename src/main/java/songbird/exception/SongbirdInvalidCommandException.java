package songbird.exception;

/**
 * Represents an exception that is thrown when an invalid command is entered by the user.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class SongbirdInvalidCommandException extends SongbirdException {
    /**
     * Constructs the SongbirdInvalidCommandException class.
     * Initializes the exception with a default message.
     */
    public SongbirdInvalidCommandException() {
        super("That's not a valid command. Please try again.");
    }
}
