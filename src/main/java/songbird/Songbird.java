package songbird;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final List<String> initialResponses = new ArrayList<>();
    private Ui ui;
    private Parser parser;
    private TaskList tasks;

    /**
     * Constructs the Songbird chatbot.
     */
    public Songbird() {
        try {
            Storage storage = new Storage("./data/tasklistDB");
            List<Task> loadedTasks = storage.load();

            if (!loadedTasks.isEmpty()) {
                initialResponses.add("Found " + loadedTasks.size() + " previously-saved task(s).");
            }

            tasks = new TaskList(loadedTasks, storage);
            this.parser = new Parser(tasks);
        } catch (SongbirdStorageException e) {
            initialResponses.add("Failed to initialize storage: " + e.getMessage());
        }
    }

    /**
     * Initializes the Songbird chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Songbird songbird = new Songbird();
    }

    /**
     * Initializes the Songbird chatbot.
     * Songbird greets the user and reads the user's command.
     * This method should be called once at the start of the program.
     */
    private void init() {
        this.ui = new Ui();
        initialResponses.add(ui.getGreeting());
        showTodayReminder();
    }

    public void getResponse(String input) {
        try {
            Command commandObject = parser.parse(input);
            commandObject.execute();
        } catch (Exception e) {
            Ui.error(e.getMessage());
        }
    }

    /**
     * Shows a reminder for tasks that are due or occurring on the current date as a reminder whenever Songbird is
     * initialized.
     */
    private void showTodayReminder() {
        LocalDateTime today = LocalDateTime.now();
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

    /**
     * Sends the initial stored responses to the UI after it has been initialized.
     */
    public void sendInitialResponses() {
        init();
        initialResponses.forEach(Ui::respond);
    }
}
