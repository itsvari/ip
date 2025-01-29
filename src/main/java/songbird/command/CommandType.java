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
    DELETE,
    DUE;

    /**
     * Returns the CommandType corresponding to the given string.
     * The string is case-insensitive.
     *
     * @param command The user-input command to be converted to a CommandType.
     * @return The corresponding CommandType.
     * @throws SongbirdInvalidCommandException If the string does not match any known type.
     */
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
            case "due" -> DUE;
            default -> throw new SongbirdInvalidCommandException();
        };
    }
}
