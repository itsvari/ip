package songbird.parser;

import songbird.command.ByeCommand;
import songbird.command.Command;
import songbird.command.CommandType;
import songbird.command.DeadlineAddCommand;
import songbird.command.EventAddCommand;
import songbird.command.ListCommand;
import songbird.command.TaskMarkCommand;
import songbird.command.TaskUnmarkCommand;
import songbird.command.ToDoAddCommand;
import songbird.exception.SongbirdException;
import songbird.exception.SongbirdMalformedCommandException;
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
    public Command parse(String input) throws SongbirdException {
        String[] inputArray = input.split(" ");
        CommandType commandType = CommandType.fromString(inputArray[0]);

        // should never get here
        return switch (commandType) {
            case LIST -> new ListCommand(tasks);
            case BYE -> new ByeCommand();
            case DEADLINE, EVENT, TODO -> {
                String taskParameters = input.substring(inputArray[0].length()).trim();
                yield handleTaskAddCommands(commandType, taskParameters);
            }
            case MARK, UNMARK -> {
                int index = Integer.parseInt(inputArray[1]);
                yield handleMarkingCommands(commandType, tasks, index);
            }
        };
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
            default ->
                    throw new IllegalStateException("Unexpected command type: " + commandType); // should never get here
        };
    }

    private Command handleTaskAddCommands(CommandType type, String taskParameters) throws SongbirdException {
        if (taskParameters.isEmpty()) {
            throw new SongbirdMalformedCommandException("The description of a task cannot be empty.");
        }
        return switch (type) {
            case TODO -> new ToDoAddCommand(tasks, taskParameters);
            case DEADLINE -> {
                String[] parts = taskParameters.split(" /by ", 2);

                // Check if the deadline task has a deadline
                if (parts.length < 2 || parts[1].isEmpty()) {
                    throw new SongbirdMalformedCommandException("The deadline task must have a deadline.");
                }

                String deadlineDescription = parts[0];
                String deadline = parts[1];
                yield new DeadlineAddCommand(tasks, deadlineDescription, deadline);
            }
            case EVENT -> {
                String eventDescription;
                String eventStart;
                String eventEnd;

                // Check if /from and /to are present
                if (! taskParameters.contains(" /from ") || ! taskParameters.contains(" /to ")) {
                    throw new SongbirdMalformedCommandException("The event task must have a start and end time.");
                }

                // Split the taskParameters string
                String[] fromParts = taskParameters.split(" /from ", 2);
                String[] toParts;

                // extract the description, start, and end times, no matter the order
                if (fromParts[1].contains(" /to ")) {
                    // if /from comes before /to
                    eventDescription = fromParts[0].trim();
                    toParts = fromParts[1].split(" /to ", 2);
                    eventStart = toParts[0].trim();
                } else {
                    // if /to comes before /from
                    eventStart = fromParts[1].trim();
                    toParts = fromParts[0].split(" /to ", 2);
                    eventDescription = toParts[0].trim();
                }
                eventEnd = toParts[1].trim();

                yield new EventAddCommand(tasks, eventDescription, eventStart, eventEnd);
            }
            default -> throw new IllegalStateException("Unexpected command type: " + type); // should never get here
        };
    }
}
