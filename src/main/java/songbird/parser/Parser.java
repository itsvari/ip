package songbird.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;

import songbird.command.ByeCommand;
import songbird.command.Command;
import songbird.command.CommandType;
import songbird.command.DeadlineAddCommand;
import songbird.command.DueCommand;
import songbird.command.EventAddCommand;
import songbird.command.ListCommand;
import songbird.command.TaskDeleteCommand;
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
        String[] inputArray = input.trim().split("\\s+", 2);
        CommandType commandType = CommandType.fromString(inputArray[0]);

        return switch (commandType) {
            case LIST -> new ListCommand(tasks);
            case BYE -> new ByeCommand();
            case DEADLINE, EVENT, TODO -> {
                // Check if the task description is empty
                if (inputArray.length < 2 || inputArray[1].isBlank()) {
                    throw new SongbirdMalformedCommandException("The description of a task cannot be empty.");
                }

                yield handleTaskAddCommands(commandType, inputArray[1]);
            }
            case MARK, UNMARK -> {
                if (inputArray.length < 2) {
                    throw new SongbirdMalformedCommandException("You must specify a task number to modify.");
                }

                int index = Integer.parseInt(inputArray[1]) - 1; // convert to 0-based indexing for internal use
                yield handleMarkingCommands(commandType, tasks, index);
            }
            case DELETE -> {
                if (inputArray.length < 2) {
                    throw new SongbirdMalformedCommandException("You must specify a task number to delete.");
                }

                int index = Integer.parseInt(inputArray[1]) - 1; // convert to 0-based indexing for internal use
                yield new TaskDeleteCommand(tasks, index);
            }
            case DUE -> {
                if (inputArray.length < 2) {
                    throw new SongbirdMalformedCommandException("You must specify a date, e.g. 'due 2025-01-17'.");
                }

                LocalDate date = DateTimeParser.parseDate(inputArray[1]);
                yield new DueCommand(tasks, date);
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
     * @param index       The index of the task to be marked or unmarked, 0-indexed.
     * @return The command object that corresponds to the user input.
     */
    private Command handleMarkingCommands(CommandType commandType, TaskList tasks, int index) {
        return switch (commandType) {
            case MARK -> new TaskMarkCommand(tasks, index);
            case UNMARK -> new TaskUnmarkCommand(tasks, index);
            default ->
                    throw new IllegalStateException("Unexpected command type: " + commandType); // should never get here
        };
    }

    /**
     * Handles commands related to adding tasks.
     *
     * @param commandType    The type of command to be executed.
     * @param taskParameters The parameters of the task to be added.
     * @return The command object that corresponds to the user input.
     * @throws SongbirdException If the task parameters are empty or malformed.
     */
    private Command handleTaskAddCommands(CommandType commandType, String taskParameters) throws SongbirdException {
        return switch (commandType) {
            case TODO -> new ToDoAddCommand(tasks, taskParameters);

            case DEADLINE -> {
                // must be in format: <DESCRIPTION> /by <DEADLINE>
                String[] parts = taskParameters.split(" /by ", 2);

                // Check if the deadline task has a deadline
                if (parts.length < 2 || parts[1].isEmpty()) {
                    throw new SongbirdMalformedCommandException("The deadline task must have a deadline, formatted as "
                            + "'/by YYYY-MM-DD' (e.g. 2025-01-30).");
                }

                String description = parts[0].trim();
                LocalDateTime deadline = DateTimeParser.parseDateTime(parts[1].trim());
                yield new DeadlineAddCommand(tasks, description, deadline);
            }

            case EVENT -> {
                String eventDescription;
                String eventStartString;
                String eventEndString;

                // must be in format: <DESCRIPTION> /from <START> /to <END>
                if (!taskParameters.contains(" /from ") || !taskParameters.contains(" /to ")) {
                    throw new SongbirdMalformedCommandException("The event task must have a start and end time, "
                            + "formatted as '/from YYYY-MM-DD HH:MM /to YYYY-MM-DD HH:MM'.");
                }

                // Split the taskParameters string
                String[] fromParts = taskParameters.split(" /from ", 2);
                String[] toParts;

                // extract the description, start, and end times, no matter the order
                if (fromParts[1].contains(" /to ")) {
                    // if /from comes before /to
                    eventDescription = fromParts[0].trim();
                    toParts = fromParts[1].split(" /to ", 2);
                    eventStartString = toParts[0].trim();
                } else {
                    // if /to comes before /from
                    eventStartString = fromParts[1].trim();
                    toParts = fromParts[0].split(" /to ", 2);
                    eventDescription = toParts[0].trim();
                }
                eventEndString = toParts[1].trim();

                LocalDateTime eventStart = DateTimeParser.parseDateTime(eventStartString);
                LocalDateTime eventEnd = DateTimeParser.parseDateTime(eventEndString);

                yield new EventAddCommand(tasks, eventDescription, eventStart, eventEnd);
            }
            default ->
                    throw new IllegalStateException("Unexpected command type: " + commandType); // should never get here
        };
    }
}
