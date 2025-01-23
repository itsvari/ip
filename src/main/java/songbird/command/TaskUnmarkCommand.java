package songbird.command;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

public class TaskUnmarkCommand extends Command {
    private final TaskList tasks;
    private final int taskIndex;

    /**
     * Constructor for the UnmarkCommand class.
     * Initializes the Unmark command with the type, friendly name, and description.
     *
     * @param taskIndex The index of the task to be marked as not done.
     */
    public TaskUnmarkCommand(TaskList tasks, int taskIndex) {
        super(CommandType.UNMARK, "unmark", "Marks a task as not done.");
        this.taskIndex = taskIndex;
        this.tasks = tasks;
    }

    /**
     * Executes the Mark command.
     * Marks the task at the specified index as done.
     */
    @Override
    public void execute() {
        try {
            Task retrievedTask = tasks.getTask(taskIndex);
            retrievedTask.setTaskNotDone();
            Ui.respond("Task marked as not done:\n" + retrievedTask.toString());
        } catch (SongbirdNonExistentTaskException e) {
            Ui.error(e.getMessage());
        }
    }
}
