package songbird.task;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Task that the user wants to save.
 * <p>
 * Tasks can either be done or not done, and must have a description.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public abstract class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected final TaskType taskType;
    protected final String description;
    protected boolean isDone;

    /**
     * Constructor for the Task class.
     * Initializes the Task with a description, which cannot be changed.
     *
     * @param description The description of the Task.
     */
    public Task(TaskType taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        isDone = false;
    }

    /**
     * Returns the description of the Task.
     *
     * @return The description of the Task.
     */
    public String getDescription() {
        return DESCRIPTION;
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
        return "[" + statusIcon + "] " + this.description;
    }
}
