package songbird.tasks;

/**
 * Represents a Task that the user wants to save.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Task {
    private final String DESCRIPTION;

    /**
     * Constructor for the Task class.
     * Initializes the Task with a description, which cannot be changed.
     *
     * @param description The description of the Task.
     */
    public Task(String description) {
        this.DESCRIPTION = description;
    }

    /**
     * Returns a String representation of the Task.
     *
     * @return A String representation of the Task.
     */
    @Override
    public String toString() {
        return this.DESCRIPTION;
    }
}
