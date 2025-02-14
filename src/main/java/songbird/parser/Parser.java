package songbird.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;

import songbird.command.ByeCommand;
import songbird.command.Command;
import songbird.command.CommandType;
import songbird.command.DeadlineAddCommand;
import songbird.command.DueCommand;
import songbird.command.EventAddCommand;
import songbird.command.FindCommand;
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
    private static final String BY_DELIMITER = " /by ";
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";

    private final TaskList tasks;

    /**
     * Constructs the Parser class.
     *
     * @param tasks The TaskList object that stores the user's tasks.
     */
    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Parses the user input into a command object.
     * Actual parsing is delegated to the specific command parsing methods.
     *
     * @param input The user input to be parsed.
     * @return The command object corresponding to the user input.
     * @throws SongbirdException If the user input is invalid or malformed.
     */
    public Command parse(String input) throws SongbirdException {
        String[] inputArray = input.trim().split("\\s+", 2);
        CommandType commandType = CommandType.fromString(inputArray[0]);
        String parameters = inputArray.length > 1 ? inputArray[1] : "";

        return switch (commandType) {
            case LIST -> new ListCommand(tasks);
            case BYE -> new ByeCommand();
            case TODO -> parseToDoCommand(parameters);
            case DEADLINE -> parseDeadlineCommand(parameters);
            case EVENT -> parseEventCommand(parameters);
            case FIND -> parseFindCommand(parameters);
            case MARK -> parseMarkCommand(parameters);
            case UNMARK -> parseUnmarkCommand(parameters);
            case DELETE -> parseDeleteCommand(parameters);
            case DUE -> parseDueCommand(parameters);
        };
    }

    // COMMAND PARSER METHODS

    /**
     * Parses the user input for a ToDoCommand.
     *
     * @param parameters The user input parameters for the ToDoCommand.
     * @return The ToDoCommand object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseToDoCommand(String parameters) throws SongbirdMalformedCommandException {
        if (parameters.isBlank()) {
            throw new SongbirdMalformedCommandException("The description of a todo cannot be empty.");
        }
        return new ToDoAddCommand(tasks, parameters);
    }

    /**
     * Parses the user input for a DeadlineCommand.
     *
     * @param parameters The user input parameters for the DeadlineCommand.
     * @return The DeadlineCommand object.
     * @throws SongbirdException If the user input is invalid or malformed.
     */
    private Command parseDeadlineCommand(String parameters) throws SongbirdException {
        String[] parts = parameters.split(BY_DELIMITER, 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SongbirdMalformedCommandException(
                    "The deadline task must have a deadline, formatted as '/by YYYY-MM-DD' (e.g. 2025-01-30).");
        }
        String description = parts[0].trim();
        LocalDateTime deadline = DateTimeParser.parseDateTime(parts[1].trim());
        return new DeadlineAddCommand(tasks, description, deadline);
    }

    /**
     * Parses the user input for an EventCommand.
     *
     * @param parameters The user input parameters for the EventCommand.
     * @return The EventCommand object.
     * @throws SongbirdException If the user input is invalid or malformed.
     */
    private Command parseEventCommand(String parameters) throws SongbirdException {
        if (!parameters.contains(FROM_DELIMITER) || !parameters.contains(TO_DELIMITER)) {
            throw new SongbirdMalformedCommandException("The event task must have a start and end time, "
                    + "formatted as '/from YYYY-MM-DD HH:MM /to YYYY-MM-DD HH:MM'.");
        }
        String[] fromParts = parameters.split(FROM_DELIMITER, 2);
        String[] toParts;
        String eventDescription;
        String eventStartString;
        String eventEndString;

        if (fromParts[1].contains(TO_DELIMITER)) {
            eventDescription = fromParts[0].trim();
            toParts = fromParts[1].split(TO_DELIMITER, 2);
            eventStartString = toParts[0].trim();
            eventEndString = toParts[1].trim();
        } else {
            eventStartString = fromParts[1].trim();
            toParts = fromParts[0].split(TO_DELIMITER, 2);
            eventDescription = toParts[1].trim();
            eventEndString = toParts[0].trim();
        }
        LocalDateTime eventStart = DateTimeParser.parseDateTime(eventStartString);
        LocalDateTime eventEnd = DateTimeParser.parseDateTime(eventEndString);
        return new EventAddCommand(tasks, eventDescription, eventStart, eventEnd);
    }

    /**
     * Parses the user input for a FindCommand.
     *
     * @param parameters The user input parameters for the FindCommand.
     * @return The FindCommand object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseFindCommand(String parameters) throws SongbirdMalformedCommandException {
        if (parameters.isBlank()) {
            throw new SongbirdMalformedCommandException("You must specify a keyword to search for.");
        }
        return new FindCommand(tasks, parameters);
    }

    /**
     * Parses the user input for a MarkCommand.
     *
     * @param parameters The user input parameters for the MarkCommand.
     * @return The MarkCommand object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseMarkCommand(String parameters) throws SongbirdMalformedCommandException {
        return parseTaskIndexCommand(parameters, CommandType.MARK);
    }

    /**
     * Parses the user input for an UnmarkCommand.
     *
     * @param parameters The user input parameters for the UnmarkCommand.
     * @return The UnmarkCommand object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseUnmarkCommand(String parameters) throws SongbirdMalformedCommandException {
        return parseTaskIndexCommand(parameters, CommandType.UNMARK);
    }

    /**
     * Parses the user input for a DeleteCommand.
     *
     * @param parameters The user input parameters for the DeleteCommand.
     * @return The DeleteCommand object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseDeleteCommand(String parameters) throws SongbirdMalformedCommandException {
        return parseTaskIndexCommand(parameters, CommandType.DELETE);
    }

    /**
     * Parses the user input for commands that require a task index.
     *
     * @param parameters The user input parameters for the command.
     * @param type The CommandType of the command.
     * @return The Command object.
     * @throws SongbirdMalformedCommandException If the user input is invalid or malformed.
     */
    private Command parseTaskIndexCommand(String parameters,
                                          CommandType type) throws SongbirdMalformedCommandException {
        if (parameters.isBlank()) {
            throw new SongbirdMalformedCommandException("You must specify a task number.");
        }
        try {
            int index = Integer.parseInt(parameters) - 1;
            return switch (type) {
                case MARK -> new TaskMarkCommand(tasks, index);
                case UNMARK -> new TaskUnmarkCommand(tasks, index);
                case DELETE -> new TaskDeleteCommand(tasks, index);
                default -> throw new IllegalStateException("Unexpected command type: " + type);
            };

        } catch (NumberFormatException e) {
            throw new SongbirdMalformedCommandException("Invalid task number: " + parameters);
        }
    }

    /**
     * Parses the user input for a DueCommand.
     * The user input should be a date in the format 'YYYY-MM-DD'.
     *
     * @param parameters The user input parameters for the DueCommand.
     * @return The DueCommand object.
     * @throws SongbirdException If the user input is invalid or malformed.
     */
    private Command parseDueCommand(String parameters) throws SongbirdException {
        if (parameters.isBlank()) {
            throw new SongbirdMalformedCommandException("You must specify a date, e.g. 'due 2025-01-17'.");
        }
        LocalDate date = DateTimeParser.parseDate(parameters);
        return new DueCommand(tasks, date);
    }
}
