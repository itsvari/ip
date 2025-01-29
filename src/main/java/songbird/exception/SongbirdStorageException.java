package songbird.exception;

/**
 * Represents an exception that is thrown when an error occurs in the storage of the Songbird program.
 */
public class SongbirdStorageException extends SongbirdException {
    /**
     * Constructor for the SongbirdStorageException class. Initializes the exception with the given message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public SongbirdStorageException(String message) {
        super("Storage error: " + message);
    }
}
