package songbird.exception;

public class SongbirdInvalidTaskTypeException extends SongbirdException {
    public SongbirdInvalidTaskTypeException() {
        super("That's not a valid task type. Please try again.");
    }
}
