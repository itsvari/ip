package songbird.task;

public class DeadlineTask extends Task {
    private final String deadline;

    /**
     * Constructor for the DeadlineTask class. Initializes the task with the given description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param deadline    The deadline of the Deadline task.
     */
    public DeadlineTask(String description, String deadline) {
        super(TaskType.DEADLINE, description);
        this.deadline = deadline;
    }

    /**
     * Returns the string representation of the Deadline task.
     *
     * @return The string representation of the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (deadline: " + deadline + ")";
    }
}
