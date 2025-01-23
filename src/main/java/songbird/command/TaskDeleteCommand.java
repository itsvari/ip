package songbird.command;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

public class TaskDeleteCommand extends Command {
    private final TaskList tasks;
    private final int taskIndex;

    /**
     * Constructor for the TaskDeleteCommand class.
     * Initializes the Delete command with the type, friendly name, and description.
     *
     * @param taskIndex The index of the task to be deleted.
     */
    public TaskDeleteCommand(TaskList tasks, int taskIndex) {
        super(CommandType.DELETE, "delete", "Deletes a task from the task list.");
        this.taskIndex = taskIndex - 1; // Convert to 0-based index
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
            Ui.respond("Task deleted:\n" + deletedTask, "You now have " + tasks.getSize() + " tasks.");
        } catch (SongbirdNonExistentTaskException e) {
            Ui.error(e.getMessage());
        }
    }
}
