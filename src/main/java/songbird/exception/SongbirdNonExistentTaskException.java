package songbird.exception;

/**
 * Represents an exception that is thrown when a task does not exist in the TaskList.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class SongbirdNonExistentTaskException extends SongbirdException {
    /**
     * Constructs the SongbirdNonExistentTaskException class.
     * Initializes the exception with a default message.
     */
    public SongbirdNonExistentTaskException() {
        super("That task couldn't be found. Please try again.");
    }
}
