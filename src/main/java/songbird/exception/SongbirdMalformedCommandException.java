package songbird.exception;

public class SongbirdMalformedCommandException extends SongbirdException {
    public SongbirdMalformedCommandException(String message) {
        super("That command was not formatted correctly: " + message);
    }
}
