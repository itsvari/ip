package songbird.commands;

import songbird.tasks.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command that lists all saved tasks.
 * The List command is used to display all saved tasks to the user.
 * The command does not require any additional input from the user.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Command
 */
public class ListCommand extends Command {
    private final TaskList tasks;

    /**
     * Constructor for the ListCommand class. Initializes the command with the given TaskList.
     *
     * @param tasks The TaskList to be used by the command.
     */
    public ListCommand(TaskList tasks) {
        super(CommandType.LIST, "list", "Lists all saved tasks.");
        this.tasks = tasks;
    }

    /**
     * Executes the List command.
     * <p>
     * Prints the list of saved tasks to the user.
     */
    @Override
    public void execute() {
        Ui.respond(tasks.toString());
    }
}
