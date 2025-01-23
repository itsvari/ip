package songbird.command;

/**
 * Represents the different types of commands that the user can input.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public enum CommandType {
    BYE,
    LIST,
    TASK_ADD,
    MARK,
    UNMARK;

    public static CommandType fromString(String command) {
        return switch (command.toLowerCase()) {
            case "bye" -> BYE;
            case "list" -> LIST;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            default -> TASK_ADD;
        };
    }
}
