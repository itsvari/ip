package songbird;

import java.time.LocalDate;
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
    private TaskList tasks;

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

            tasks = new TaskList(loadedTasks, storage);
            this.parser = new Parser(tasks);
        } catch (SongbirdStorageException e) {
            Ui.error("Failed to initialize storage: " + e.getMessage());
            System.exit(1);
        }
    }

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
     * Initializes the Songbird chatbot.
     * Songbird greets the user and reads the user's command.
     * This method should be called once at the start of the program.
     */
    private void init() {
        ui.greet();

        // show a reminder for tasks due or occurring today
        showTodayReminder();
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

    /**
     * Shows a reminder for tasks that are due or occurring on the current date as a reminder whenever Songbird is
     * initialized.
     */
    private void showTodayReminder() {
        LocalDate today = LocalDate.now();
        List<Task> tasksDueToday = tasks.getTasksByDate(today);

        if (!tasksDueToday.isEmpty()) {
            Ui.respond("REMINDER :: You have task(s) happening today (" + today + "):");
            int counter = 1;
            for (Task task : tasksDueToday) {
                Ui.respond(counter + ". " + task.toString());
                counter++;
            }
        }

    }
}
