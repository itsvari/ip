package songbird.command;

/**
 * Represents a command that the user can input.
 * The command is responsible for executing the user's input.
 * The command is also responsible for providing a friendly name and description to the user.
 * The command is an abstract class that is inherited by specific command classes.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public abstract class Command {
    private final CommandType commandType;
    private final String friendlyName;
    private final String description;

    /**
     * Constructs the Command class.
     * Initializes the command with the given type, friendly name, and description.
     *
     * @param commandType  The type of the command.
     * @param friendlyName The friendly name of the command, shown to the user.
     * @param description  The description of the command, shown to the user.
     */
    public Command(CommandType commandType, String friendlyName, String description) {
        this.commandType = commandType;
        this.friendlyName = friendlyName;
        this.description = description;
    }

    // GETTERS
    public CommandType getType() {
        return commandType;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getDescription() {
        return description;
    }

    // ABSTRACT METHODS

    /**
     * Executes the command.
     */
    public abstract void execute();
}
