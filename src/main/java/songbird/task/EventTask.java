package songbird.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task in the TaskList.
 * An Event task is a task with a starting and ending date/time.
 * It is denoted by a [E] prefix in the task list.
 * It contains a description, a starting date/time and an ending date/time.
 * Example: [E][ ] black market arms deal (from: 2025-09-30T14:00 to: 2025-09-30T16:00)
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class EventTask extends Task {
    private final LocalDateTime eventStart;
    private final LocalDateTime eventEnd;

    /**
     * Constructor for the EventTask class. Initializes the task with the given description and event time.
     *
     * @param description The description of the Event task.
     * @param eventStart  The event starting time of the Event task.
     * @param eventEnd    The event ending time of the Event task.
     */
    public EventTask(String description, LocalDateTime eventStart, LocalDateTime eventEnd) {
        super(TaskType.EVENT, description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    /**
     * Returns the starting time of the Event task.
     *
     * @return The starting time of the Event task.
     */
    public LocalDateTime getEventStart() {
        return eventStart;
    }

    /**
     * Returns the ending time of the Event task.
     *
     * @return The ending time of the Event task.
     */
    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    /**
     * Returns the string representation of the Event task.
     *
     * @return The string representation of the Event task.
     */
    @Override
    public String toString() {
        String start = eventStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String end = eventEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
