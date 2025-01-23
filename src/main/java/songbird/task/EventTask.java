package songbird.task;

public class EventTask extends Task {
    private final String eventStart;
    private final String eventEnd;

    /**
     * Constructor for the EventTask class. Initializes the task with the given description and event time.
     *
     * @param description The description of the Event task.
     * @param eventStart  The event starting time of the Event task.
     * @param eventEnd    The event ending time of the Event task.
     */
    public EventTask(String description, String eventStart, String eventEnd) {
        super(TaskType.EVENT, description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    /**
     * Returns the string representation of the Event task.
     *
     * @return The string representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + eventStart + " to: " + eventEnd + ")";
    }
}
