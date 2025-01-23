package songbird.task;

/**
 * Represents a Task that the user wants to save.
 * <p>
 * Tasks can either be done or not done, and must have a description.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Task {
    protected final String DESCRIPTION;
    protected boolean isDone;

    /**
     * Constructor for the Task class.
     * Initializes the Task with a description, which cannot be changed.
     *
     * @param description The description of the Task.
     */
    public Task(String description) {
        this.DESCRIPTION = description;
        isDone = false;
    }

    /**
     * Sets the Task as done.
     */
    public void setTaskDone() {
        isDone = true;
    }

    /**
     * Sets the Task as not done.
     */
    public void setTaskNotDone() {
        isDone = false;
    }

    /**
     * Returns a String representation of the Task.
     * The string includes the status icon (X for done, space for not done) and the task description.
     *
     * @return A String representation of the Task.
     */
    @Override
    public String toString() {
        String statusIcon = this.isDone ? "X" : " ";
        return "[" + statusIcon + "] " + this.DESCRIPTION;
    }
}
