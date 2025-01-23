package songbird.commands;

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
    private final CommandType TYPE;
    private final String FRIENDLY_NAME;
    private final String DESCRIPTION;

    /**
     * Constructor for the Command class. Initializes the command with the given type, friendly name, and description.
     *
     * @param type         The type of the command.
     * @param friendlyName The friendly name of the command, shown to the user.
     * @param description  The description of the command, shown to the user.
     */
    public Command(CommandType type, String friendlyName, String description) {
        this.TYPE = type;
        this.FRIENDLY_NAME = friendlyName;
        this.DESCRIPTION = description;
    }

    // GETTERS
    public CommandType getType() {
        return TYPE;
    }

    public String getFriendlyName() {
        return FRIENDLY_NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    // ABSTRACT METHODS

    /**
     * Executes the command.
     */
    public abstract void execute();
}
