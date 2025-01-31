package songbird.command;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class TaskDeleteCommand extends Command {
    private final TaskList tasks;
    private final int taskIndex;

    /**
     * Constructs the TaskDeleteCommand class.
     * Initializes the Delete command with the type, friendly name, and description.
     *
     * @param taskIndex The index of the task to be deleted.
     */
    public TaskDeleteCommand(TaskList tasks, int taskIndex) {
        super(CommandType.DELETE, "delete", "Deletes a task from the task list.");
        this.taskIndex = taskIndex;
        this.tasks = tasks;
    }

    /**
     * Executes the Delete command.
     * Deletes the task at the specified index from the task list.
     */
    @Override
    public void execute() {
        try {
            Task deletedTask = tasks.deleteTask(taskIndex);
            Ui.respond("Task deleted:\n" + deletedTask, tasks.getTaskCountMessage());
        } catch (SongbirdNonExistentTaskException e) {
            Ui.error(e.getMessage());
        }
    }
}
