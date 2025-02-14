package songbird.command;

import java.time.LocalDateTime;
import java.util.List;

import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Lists tasks that are due on a specified date. Only applies to Deadlines and Events.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class DueCommand extends Command {
    private final TaskList tasks;
    private final LocalDateTime date;

    /**
     * Constructs a new DueCommand with the specified date.
     *
     * @param tasks The TaskList instance to be used by the DueCommand.
     * @param date  The date to filter tasks by.
     */
    public DueCommand(TaskList tasks, LocalDateTime date) {
        super(CommandType.DUE, "due", "Lists tasks that are due on a specified date.");
        this.tasks = tasks;
        this.date = date;
    }

    /**
     * Executes the Due command.
     * Lists tasks that are due on the specified date and displays them to the user.
     */
    @Override
    public void execute() {
        List<Task> tasksDue = tasks.getTasksByDate(date);
        if (tasksDue.isEmpty()) {
            Ui.respond("No tasks are due on " + date + ".");
        } else {
            Ui.respond("You have " + tasksDue.size() + " tasks due on " + date + ":");
            int counter = 1;
            for (Task task : tasksDue) {
                Ui.respond(counter + ". " + task.toString());
                counter++;
            }
        }
    }
}
