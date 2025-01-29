package songbird.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.exception.SongbirdStorageException;
import songbird.storage.Storage;
import songbird.ui.Ui;

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
    private final Storage storage;

    /**
     * Creates a TaskList with no initial tasks and the specified storage.
     *
     * @param storage The Storage instance to be used by the TaskList for persistence.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Creates a TaskList with the specified initial tasks and storage.
     *
     * @param initialTasks The initial tasks to be added to the TaskList.
     * @param storage      The Storage instance to be used by the TaskList for persistence.
     */
    public TaskList(List<Task> initialTasks, Storage storage) {
        this.tasks = new ArrayList<>(initialTasks);
        this.storage = storage;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     * @return The task that was added.
     */
    public Task addTask(Task task) {
        this.tasks.add(task);
        this.saveList();
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
        Task deletedTask = this.tasks.remove(index);
        this.saveList();

        return deletedTask;
    }

    /**
     * Saves the updated task list to persistent storage.
     * If there are any errors saving the task list, an error message is displayed.
     */
    public void saveList() {
        try {
            storage.save(getAll());
        } catch (SongbirdStorageException e) {
            Ui.error("Failed to save tasks: " + e.getMessage());
        }
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
     * Returns all tasks in the task list.
     *
     * @return A copy of the task list.
     */
    private List<Task> getAll() {
        return new ArrayList<>(tasks);  // return a copy to maintain encapsulation
    }

    /**
     * Returns the total size of the task list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns a count of the number of tasks in the TaskList.
     *
     * @return A message indicating the number of tasks in the TaskList.
     */
    public String getTaskCountMessage() {
        return "You now have " + getSize() + " task(s).";
    }

    /**
     * Returns tasks that occur on the specified date:
     * - For Deadline tasks, the deadline must be on the specified date.
     * - For Event tasks, the event must start or end on the specified date.
     * If there are no tasks that match the criteria, an empty list is returned.
     *
     * @param date The date to filter tasks by.
     *             The time component of the date is ignored.
     * @return A list of tasks that occur on the specified date.
     */
    public List<Task> getTasksByDate(LocalDate date) {
        return this.tasks.stream()
                .filter(task -> {
                    if (task instanceof DeadlineTask dt) {
                        return dt.getDeadline().toLocalDate().isEqual(date);
                    } else if (task instanceof EventTask et) {
                        LocalDate start = et.getEventStart().toLocalDate();
                        LocalDate end = et.getEventEnd().toLocalDate();
                        // check if current date is between start and end (inclusive)
                        return !date.isBefore(start) && !date.isAfter(end);
                    }
                    // ignore other task types
                    return false;
                })
                .collect(Collectors.toList());
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
