package songbird.command;

import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command that adds a task to the task list.
 * The TaskAdd command is used to add a task to the task list.
 * The command requires additional input from the user, which is the description of the task to be added.
 * The description of the task is provided by the user when the command is executed.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Command
 * @see Task
 */
public class TaskAddCommand extends Command {
    private final String taskDescription;
    private final TaskList tasks;

    /**
     * Constructor for the TaskAddCommand class. Initializes the command with the given task description.
     *
     * @param taskDescription The description of the task to be added.
     */
    public TaskAddCommand(TaskList tasks, String taskDescription) {
        super(CommandType.TASK_ADD, "add", "Adds a task to the task list.");
        this.tasks = tasks;
        this.taskDescription = taskDescription;
    }

    /**
     * Executes the TaskAdd command.
     * Adds a task to the task list.
     */
    @Override
    public void execute() {
        tasks.addTask(new Task(taskDescription));
        Ui.respond("Task added: " + taskDescription);
    }
}
