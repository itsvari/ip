package songbird.command;

import songbird.exception.SongbirdInvalidCommandException;

/**
 * Represents the different types of commands that the user can input.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public enum CommandType {
    BYE("bye"),
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    DUE("due"),
    FIND("find");

    private final String value;

    /**
     * Constructs a CommandType with the given string value.
     *
     * @param value The string value of the CommandType.
     */
    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns the CommandType corresponding to the given string.
     * The string is case-insensitive.
     *
     * @param command The user-input command to be converted to a CommandType.
     * @return The corresponding CommandType.
     * @throws SongbirdInvalidCommandException If the string does not match any known type.
     */
    public static CommandType fromString(String command) throws SongbirdInvalidCommandException {
        for (CommandType type : CommandType.values()) {
            if (type.getValue().equalsIgnoreCase(command)) {
                return type;
            }
        }

        throw new SongbirdInvalidCommandException();
    }
}
