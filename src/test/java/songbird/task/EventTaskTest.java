package songbird.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the EventTask class.
 */
public class EventTaskTest {
    private EventTask eventTask;
    private final LocalDateTime eventStart = LocalDateTime.of(2025, 9, 30, 14, 0);
    private final LocalDateTime eventEnd = LocalDateTime.of(2025, 9, 30, 16, 0);

    /**
     * Sets up the EventTask object before each test.
     */
    @BeforeEach
    public void setUp() {
        eventTask = new EventTask("Black market arms deal", eventStart, eventEnd);
    }

    /**
     * Tests the constructor of the EventTask class.
     * The constructor should correctly initialize the EventTask object with the specified description, event start,
     * event end, and task type.
     */
    @Test
    public void testConstructor_validInputs_correctInitialization() {
        assertEquals("Black market arms deal", eventTask.description);
        assertEquals(TaskType.EVENT, eventTask.taskType);
        assertEquals(eventStart, eventTask.getEventStart());
        assertEquals(eventEnd, eventTask.getEventEnd());
        assertFalse(eventTask.isDone);
    }

    /**
     * Tests the setTaskDone method of the EventTask class.
     * The method should correctly mark the task as done.
     */
    @Test
    public void testSetTaskDone_taskInitiallyNotDone_taskMarkedAsDone() {
        eventTask.setTaskDone();
        assertTrue(eventTask.isDone);
    }

    /**
     * Tests the setTaskNotDone method of the EventTask class.
     * The method should correctly mark the task as not done.
     */
    @Test
    public void testSetTaskNotDone_taskInitiallyDone_taskMarkedAsNotDone() {
        eventTask.setTaskDone(); // First mark it as done
        eventTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(eventTask.isDone);
    }

    /**
     * Tests the toString method of the EventTask class.
     * The method should return the correct string representation of the EventTask object.
     * The string representation should include the task type, completion status, description, and event start and end.
     * The completion status should be represented by [X] if the task is done, and [ ] if the task is not done.
     * The task type should be represented by [E].
     * The description should be the same as the description provided during initialization.
     * The event start and end should be formatted in ISO_LOCAL_DATE_TIME format.
     * The string representation should be correct for both done and not done tasks.
     */
    @Test
    public void testToString_taskNotDone_correctStringRepresentation() {
        String formattedStart = eventStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedEnd = eventEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals("[E][ ] Black market arms deal (from: " + formattedStart + " to: " + formattedEnd + ")",
                eventTask.toString());
        eventTask.setTaskDone();
        assertEquals("[E][X] Black market arms deal (from: " + formattedStart + " to: " + formattedEnd + ")",
                eventTask.toString());
    }
}
