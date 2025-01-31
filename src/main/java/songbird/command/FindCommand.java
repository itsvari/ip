package songbird.command;

import java.util.List;

import songbird.task.Task;
import songbird.task.TaskList;
import songbird.ui.Ui;

/**
 * Represents the command to find tasks that contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;
    private final TaskList tasks;

    /**
     * Constructs for the FindCommand class.
     * Initializes the command with the given keyword.
     *
     * @param tasks The task list to search for tasks in.
     * @param keyword The keyword to search for.
     */
    public FindCommand(TaskList tasks, String keyword) {
        super(CommandType.FIND, "Find", "Finds tasks that contain the given keyword (case-insensitive).");
        this.tasks = tasks;
        this.keyword = keyword;
    }

    /**
     * Executes the Find command.
     * Searches the task list for tasks that contain the keyword. If found, the tasks are displayed to the user.
     * If no tasks are found, a message is displayed to the user.
     */
    @Override
    public void execute() {
        // search task list for tasks that contain the keyword
        List<Task> foundTasks = tasks.getTasksByKeyword(keyword);

        if (foundTasks.isEmpty()) {
            Ui.respond("No tasks found with the keyword: " + keyword);
        } else {
            Ui.respond("Found " + foundTasks.size() + " tasks containing the keyword: " + keyword + ":");
            int counter = 1;
            for (Task task : foundTasks) {
                Ui.respond(counter + ". " + task.toString());
                counter++;
            }
        }
    }
}
