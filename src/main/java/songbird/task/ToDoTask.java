package songbird.task;

/**
 * Represents a ToDoTask.
 */
public class ToDoTask extends Task {
    /**
     * Constructs the ToDoTask class.
     * Initializes the task with the given description.
     *
     * @param description The description of the ToDo task.
     */
    public ToDoTask(String description) {
        super(TaskType.TODO, description);
    }

    /**
     * Returns the string representation of the task.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
