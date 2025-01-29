package songbird.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTaskTest {
    private EventTask eventTask;
    private final LocalDateTime eventStart = LocalDateTime.of(2025, 9, 30, 14, 0);
    private final LocalDateTime eventEnd = LocalDateTime.of(2025, 9, 30, 16, 0);

    @BeforeEach
    public void setUp() {
        eventTask = new EventTask("Black market arms deal", eventStart, eventEnd);
    }

    @Test
    public void testConstructor() {
        assertEquals("Black market arms deal", eventTask.DESCRIPTION);
        assertEquals(TaskType.EVENT, eventTask.TASK_TYPE);
        assertEquals(eventStart, eventTask.getEventStart());
        assertEquals(eventEnd, eventTask.getEventEnd());
        assertFalse(eventTask.isDone);
    }

    @Test
    public void testSetTaskDone() {
        eventTask.setTaskDone();
        assertTrue(eventTask.isDone);
    }

    @Test
    public void testSetTaskNotDone() {
        eventTask.setTaskDone(); // First mark it as done
        eventTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(eventTask.isDone);
    }

    @Test
    public void testToString() {
        String formattedStart = eventStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedEnd = eventEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals("[E][ ] Black market arms deal (from: " + formattedStart + " to: " + formattedEnd + ")",
                eventTask.toString());
        eventTask.setTaskDone();
        assertEquals("[E][X] Black market arms deal (from: " + formattedStart + " to: " + formattedEnd + ")",
                eventTask.toString());
    }
}