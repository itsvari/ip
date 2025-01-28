package songbird.task;

import songbird.exception.SongbirdNonExistentTaskException;

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

    /**
     * Constructor for the TaskList class. Initializes the task list with an empty ArrayList.
     * The task list is used to store the user's tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     * @return The task that was added.
     */
    public Task addTask(Task task) {
        this.tasks.add(task);
        return task;
    }

    /**
     * Deletes the specified task from the task list by index, if valid. Otherwise, throws a
     * SongbirdNonExistentTaskException.
     *
     * @param index The index of the task to delete.
     * @return The task that was deleted.
     * @throws SongbirdNonExistentTaskException when the task is not found.
     */
    public Task deleteTask(int index) throws SongbirdNonExistentTaskException {
        if (index < 0 || index >= tasks.size()) {
            throw new SongbirdNonExistentTaskException();
        }
        return this.tasks.remove(index);
    }

    /**
     * Returns the specified task from the task list by index, if valid. Otherwise, throws a
     * SongbirdNonExistentTaskException.
     *
     * @param index The index of the task to get.
     * @return The specified task.
     * @throws SongbirdNonExistentTaskException when the task is not found.
     */
    public Task getTask(int index) throws SongbirdNonExistentTaskException {
        if (index < 0 || index >= tasks.size()) {
            throw new SongbirdNonExistentTaskException();
        }
        return this.tasks.get(index);
    }

    /**
     * Returns the total size of the task list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    public String getTaskCountMessage() {
        return "You now have " + getSize() + " task(s).";
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
