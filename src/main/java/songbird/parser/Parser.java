package songbird.parser;

import songbird.command.ByeCommand;
import songbird.command.Command;
import songbird.command.CommandType;
import songbird.command.ListCommand;
import songbird.command.TaskAddCommand;
import songbird.command.TaskMarkCommand;
import songbird.command.TaskUnmarkCommand;
import songbird.task.TaskList;

/**
 * Represents a parser that parses user input into commands.
 * The parser is responsible for creating the appropriate command object based on the user input.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Parser {
    private final TaskList tasks;

    /**
     * Constructor for the Parser class.
     *
     * @param tasks The TaskList object that stores the user's tasks.
     */
    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Parses the user input into a command object.
     * The command object is created based on the user input.
     *
     * @param input The user input to be parsed.
     * @return The command object that corresponds to the user input.
     */
    public Command parse(String input) {
        String[] inputArray = input.split(" ");
        CommandType commandType;
        commandType = CommandType.fromString(inputArray[0]);

        switch (commandType) {
        case LIST:
            return new ListCommand(tasks);
        case BYE:
            return new ByeCommand();
        case MARK:
        case UNMARK:
            int index = Integer.parseInt(inputArray[1]);
            return handleMarkingCommands(commandType, tasks, index);
        default:
            return new TaskAddCommand(tasks, input);
        }
    }

    /**
     * Handles marking and unmarking commands.
     * The method creates the appropriate command object based on the command type. Should only be called for marking
     * and unmarking commands.
     *
     * @param commandType The type of command to be executed.
     * @param tasks       The TaskList object that stores the user's tasks.
     * @param index       The index of the task to be marked or unmarked.
     * @return The command object that corresponds to the user input.
     */
    private Command handleMarkingCommands(CommandType commandType, TaskList tasks, int index) {
        index = index - 1; // convert to 0-based indexing for internal use
        return switch (commandType) {
            case MARK -> new TaskMarkCommand(tasks, index);
            case UNMARK -> new TaskUnmarkCommand(tasks, index);
            default -> throw new IllegalStateException("Unexpected value: " + commandType); // should never get here
        };
    }
}
