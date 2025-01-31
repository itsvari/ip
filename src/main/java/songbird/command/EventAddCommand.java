package songbird.command;

import java.time.LocalDateTime;

import songbird.task.EventTask;
import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents a command that adds an Event task to the task list.
 * The EventAdd command is used to add an Event task to the task list.
 * The command requires additional input from the user, which is the description of the Event task to be added, as
 * well as the starting and ending time of the task.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Command
 * @see EventTask
 */
public class EventAddCommand extends Command {
    private final String taskDescription;
    private final TaskList tasks;
    private final LocalDateTime eventStart;
    private final LocalDateTime eventEnd;

    /**
     * Constructs the EventAddCommand class.
     * Initializes the command with the given task description.
     *
     * @param tasks           The TaskList instance to which the task is to be added.
     * @param taskDescription The description of the event task to be added.
     * @param eventStart      The starting time of the event task.
     * @param eventEnd        The ending time of the event task.
     */
    public EventAddCommand(TaskList tasks, String taskDescription, LocalDateTime eventStart, LocalDateTime eventEnd) {
        super(CommandType.EVENT, "event", "Adds a new Event task to the task list.");
        this.tasks = tasks;
        this.taskDescription = taskDescription;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    /**
     * Executes the EventAdd command.
     * Adds a new Event task to the task list and provides the user with a response.
     */
    @Override
    public void execute() {
        Task addedTask = tasks.addTask(new EventTask(taskDescription, eventStart, eventEnd));
        Ui.respond("Event task added:\n" + addedTask, tasks.getTaskCountMessage());
    }
}
