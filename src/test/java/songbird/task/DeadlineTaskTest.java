package songbird.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTaskTest {
    private DeadlineTask deadlineTask;
    private final LocalDateTime deadline = LocalDateTime.of(2025, 10, 30, 23, 59);

    @BeforeEach
    public void setUp() {
        deadlineTask = new DeadlineTask("Submit report", deadline);
    }

    @Test
    public void testConstructor() {
        assertEquals("Submit report", deadlineTask.DESCRIPTION);
        assertEquals(TaskType.DEADLINE, deadlineTask.TASK_TYPE);
        assertEquals(deadline, deadlineTask.getDeadline());
        assertFalse(deadlineTask.isDone);
    }

    @Test
    public void testSetTaskDone() {
        deadlineTask.setTaskDone();
        assertTrue(deadlineTask.isDone);
    }

    @Test
    public void testSetTaskNotDone() {
        deadlineTask.setTaskDone(); // First mark it as done
        deadlineTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(deadlineTask.isDone);
    }

    @Test
    public void testToString() {
        String formattedDeadline = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals("[D][ ] Submit report (deadline: " + formattedDeadline + ")", deadlineTask.toString());
        deadlineTask.setTaskDone();
        assertEquals("[D][X] Submit report (deadline: " + formattedDeadline + ")", deadlineTask.toString());
    }
}