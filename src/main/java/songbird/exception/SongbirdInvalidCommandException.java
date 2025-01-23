package songbird.exception;

public class SongbirdInvalidCommandException extends SongbirdException {
    public SongbirdInvalidCommandException() {
        super("That's not a valid command. Please try again.");
    }
}
