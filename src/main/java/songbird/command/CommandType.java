package songbird.command;

import songbird.exception.SongbirdInvalidCommandException;

/**
 * Represents the different types of commands that the user can input.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public enum CommandType {
    BYE,
    LIST,
    TODO,
    DEADLINE,
    EVENT,
    MARK,
    UNMARK,
    DELETE;

    public static CommandType fromString(String command) throws SongbirdInvalidCommandException {
        return switch (command.toLowerCase()) {
            case "bye" -> BYE;
            case "list" -> LIST;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            case "delete" -> DELETE;
            default -> throw new SongbirdInvalidCommandException();
        };
    }
}
