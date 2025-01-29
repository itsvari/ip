package songbird.command;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command to mark a task as done in the task list.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class TaskMarkCommand extends Command {
    private final TaskList tasks;
    private final int taskIndex;

    /**
     * Constructor for the MarkCommand class.
     * Initializes the Mark command with the type, friendly name, and description.
     *
     * @param taskIndex The index of the task to be marked as done, 0-indexed.
     */
    public TaskMarkCommand(TaskList tasks, int taskIndex) {
        super(CommandType.MARK, "mark", "Marks a task as done.");
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
            retrievedTask.setTaskDone();
            Ui.respond("Task marked as done:\n" + retrievedTask);
            tasks.saveList();
        } catch (SongbirdNonExistentTaskException e) {
            Ui.error(e.getMessage());
        }
    }
}
