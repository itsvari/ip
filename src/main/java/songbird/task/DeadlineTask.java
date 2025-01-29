package songbird.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task in the TaskList.
 * A Deadline task is a task with a deadline date/time.
 * It is denoted by a [D] prefix in the task list.
 * It contains a description and a deadline date/time.
 * Example: [D][ ] submit corporate espionage report (by: 2025-10-30T23:59)
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class DeadlineTask extends Task {
    private final LocalDateTime deadline;

    /**
     * Constructs a new DeadlineTask with a given description and deadline date/time.
     *
     * @param description The description of the Deadline task.
     * @param deadline    The deadline of the Deadline task.
     */
    public DeadlineTask(String description, LocalDateTime deadline) {
        super(TaskType.DEADLINE, description);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline of the Deadline task.
     *
     * @return The deadline of the Deadline task.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Returns the string representation of the Deadline task.
     *
     * @return The string representation of the Deadline task.
     */
    @Override
    public String toString() {
        String formatted = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "[D]" + super.toString() + " (deadline: " + formatted + ")";
    }
}
