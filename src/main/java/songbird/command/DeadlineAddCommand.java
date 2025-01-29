package songbird.command;

import java.time.LocalDateTime;

import songbird.task.DeadlineTask;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command that adds a Deadline task to the task list.
 * The DeadlineAdd command is used to add a Deadline task to the task list.
 * The command requires additional input from the user, which is the description of the Deadline task to be added, as
 * well as the deadline of the task.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Command
 * @see DeadlineTask
 */
public class DeadlineAddCommand extends Command {
    private final String taskDescription;
    private final TaskList tasks;
    private final LocalDateTime deadline;

    /**
     * Creates a new DeadlineAddCommand with the given task description and deadline.
     *
     * @param tasks           The TaskList instance to which the task is to be added.
     * @param taskDescription The description of the task to be added.
     * @param deadline        The deadline of the task to be added.
     */
    public DeadlineAddCommand(TaskList tasks, String taskDescription, LocalDateTime deadline) {
        super(CommandType.DEADLINE, "deadline", "Adds a new Deadline task to the task list.");
        this.tasks = tasks;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
    }

    /**
     * Executes the DeadlineAdd command.
     * Adds a new Deadline task to the task list and provides the user with a response.
     */
    @Override
    public void execute() {
        Task addedTask = tasks.addTask(new DeadlineTask(taskDescription, deadline));
        Ui.respond("Deadline task added:\n" + addedTask, tasks.getTaskCountMessage());
    }
}
