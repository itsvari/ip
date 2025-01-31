package songbird.command;

import songbird.task.Task;
import songbird.task.TaskList;
import songbird.task.ToDoTask;
import songbird.ui.Ui;

/**
 * Represents a command that adds a ToDoTask to the task list.
 * The ToDoAdd command is used to add a ToDoTask to the task list.
 * The command requires additional input from the user, which is the description of the ToDoTask to be added.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class ToDoAddCommand extends Command {
    private final String taskDescription;
    private final TaskList tasks;

    /**
     * Constructs the TaskAddCommand class.
     * Initializes the command with the given task description.
     *
     * @param taskDescription The description of the task to be added.
     */
    public ToDoAddCommand(TaskList tasks, String taskDescription) {
        super(CommandType.TODO, "todo", "Adds a new ToDo task to the task list.");
        this.tasks = tasks;
        this.taskDescription = taskDescription;
    }

    /**
     * Executes the ToDoAdd command.
     * Adds a new ToDoTask to the task list and provides the user with a response.
     */
    @Override
    public void execute() {
        Task addedTask = tasks.addTask(new ToDoTask(taskDescription));
        Ui.respond("ToDo task added:\n" + addedTask, tasks.getTaskCountMessage());
    }
}
