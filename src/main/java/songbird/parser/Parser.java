package songbird.parser;

import songbird.command.ByeCommand;
import songbird.command.Command;
import songbird.command.ListCommand;
import songbird.command.TaskAddCommand;
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
        switch (input.toLowerCase()) {
        case "list":
            return new ListCommand(tasks);
        case "bye":
            return new ByeCommand();
        default:
            return new TaskAddCommand(tasks, input);
        }
    }
}
