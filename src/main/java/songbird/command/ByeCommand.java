package songbird.command;

import songbird.ui.Ui;

/**
 * Represents the Bye command that exits the program.
 * Inherits from the Command class.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Command
 */
public class ByeCommand extends Command {
    /**
     * Constructor for the ByeCommand class.
     * Initializes the Bye command with the type, friendly name, and description.
     */
    public ByeCommand() {
        super(CommandType.BYE, "bye", "Exits Songbird.");
    }

    /**
     * Executes the Bye command.
     * Prints a goodbye message to the user.
     */
    @Override
    public void execute() {
        Ui.respond("Goodbye. Remember: I'm always listening.");
    }
}
