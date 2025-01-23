package songbird.tasks;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a list of tasks that the user has saved.
 * The TaskList class is responsible for storing and managing the user's tasks.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 * @see Task
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Returns a String representation of all the tasks in the TaskList.
     *
     * @return A String representation of all the tasks in the TaskList.
     */
    @Override
    public String toString() {
        // handle case where there are no tasks
        if (this.tasks.isEmpty()) {
            return "You have no saved tasks.";
        }

        StringBuilder output = new StringBuilder("Your saved tasks are:\n");
        String tasksString = this.tasks.stream()
                .map(task -> (this.tasks.indexOf(task) + 1) + ". " + task.toString())
                .collect(Collectors.joining("\n"));
        output.append(tasksString);
        return output.toString();
    }
}
