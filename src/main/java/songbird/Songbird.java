package songbird;

import java.util.List;

import songbird.command.Command;
import songbird.exception.SongbirdStorageException;
import songbird.parser.Parser;
import songbird.storage.Storage;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents the Songbird chatbot.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Songbird {
    private Ui ui;
    private Parser parser;

    /**
     * Entry point of the Songbird chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Songbird songbird = new Songbird();
        songbird.init();
    }

    /**
     * Constructor for the Songbird chatbot.
     */
    public Songbird() {
        try {
            this.ui = new Ui();
            Storage storage = new Storage("./data/tasklistDB");
            List<Task> loadedTasks = storage.load();

            // provide an update if tasks exist
            if (!loadedTasks.isEmpty()) {
                Ui.respond("Found " + loadedTasks.size() + " previously-saved task(s).");
            }

            TaskList tasks = new TaskList(loadedTasks, storage);
            this.parser = new Parser(tasks);
            init();
        } catch (SongbirdStorageException e) {
            Ui.error("Failed to initialize storage: " + e.getMessage());
            System.exit(1);
        }
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
