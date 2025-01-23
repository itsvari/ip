package songbird;

import songbird.command.Command;
import songbird.parser.Parser;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents the Songbird chatbot.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Songbird {
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;

    /**
     * Entry point of the Songbird chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Songbird();
    }

    /**
     * Constructor for the Songbird chatbot.
     */
    public Songbird() {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.parser = new Parser(tasks);
        init();
    }

    /**
     * Initializes the Songbird chatbot.
     * Songbird greets the user and reads the user's command.
     * This method should be called once at the start of the program.
     */
    private void init() {
        ui.greet();
        while (true) {
            String command = ui.readCommand();

            try {
                Command commandObject = parser.parse(command);
                commandObject.execute();
            } catch (Exception e) {
                Ui.error(e.getMessage());
            }

            // check for "bye" command and handle it
            if (command.equalsIgnoreCase("bye")) {
                break;
            }
        }
    }
}
