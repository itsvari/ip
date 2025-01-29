package songbird.command;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command to mark a task as not done in the task list.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class TaskUnmarkCommand extends Command {
    private final TaskList tasks;
    private final int taskIndex;

    /**
     * Constructor for the UnmarkCommand class.
     * Initializes the Unmark command with the type, friendly name, and description.
     *
     * @param taskIndex The index of the task to be marked as not done, 0-indexed.
     */
    public TaskUnmarkCommand(TaskList tasks, int taskIndex) {
        super(CommandType.UNMARK, "unmark", "Marks a task as not done.");
        this.taskIndex = taskIndex;
        this.tasks = tasks;
    }

    /**
     * Executes the Unmark command.
     * Marks the task at the specified index as not done.
     */
    @Override
    public void execute() {
        try {
            Task retrievedTask = tasks.getTask(taskIndex);
            retrievedTask.setTaskNotDone();
            Ui.respond("Task marked as not done:\n" + retrievedTask);
        } catch (SongbirdNonExistentTaskException e) {
            Ui.error(e.getMessage());
        }
    }
}
