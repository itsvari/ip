package songbird.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the DeadlineTask class.
 */
public class DeadlineTaskTest {
    private DeadlineTask deadlineTask;
    private final LocalDateTime deadline = LocalDateTime.of(2025, 10, 30, 23, 59);

    /**
     * Sets up the DeadlineTask object before each test.
     */
    @BeforeEach
    public void setUp() {
        deadlineTask = new DeadlineTask("Submit report", deadline);
    }

    /**
     * Tests the constructor of the DeadlineTask class.
     * The constructor should correctly initialize the DeadlineTask object with the specified description, deadline,
     * and task type.
     */
    @Test
    public void testConstructor_validInputs_correctInitialization() {
        assertEquals("Submit report", deadlineTask.description);
        assertEquals(TaskType.DEADLINE, deadlineTask.taskType);
        assertEquals(deadline, deadlineTask.getDeadline());
        assertFalse(deadlineTask.isDone);
    }

    /**
     * Tests the setTaskDone method of the DeadlineTask class.
     * The method should correctly mark the task as done.
     */
    @Test
    public void testSetTaskDone_taskInitiallyNotDone_taskMarkedAsDone() {
        deadlineTask.setTaskDone();
        assertTrue(deadlineTask.isDone);
    }

    /**
     * Tests the setTaskNotDone method of the DeadlineTask class.
     * The method should correctly mark the task as not done.
     * This test is done after the task is initially marked as done.
     */
    @Test
    public void testSetTaskNotDone_taskInitiallyDone_taskMarkedAsNotDone() {
        deadlineTask.setTaskDone(); // First mark it as done
        deadlineTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(deadlineTask.isDone);
    }

    /**
     * Tests the toString method of the DeadlineTask class.
     * The method should return a string representation of the DeadlineTask object in the correct format.
     * The status is represented by a cross [X] if the task is done, and a space [ ] if the task is not done.
     */
    @Test
    public void testToString_taskNotDone_correctStringRepresentation() {
        String formattedDeadline = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals("[D][ ] Submit report (deadline: " + formattedDeadline + ")", deadlineTask.toString());
    }

    /**
     * Tests the toString method of the DeadlineTask class.
     * The method should return a string representation of the DeadlineTask object in the correct format.
     * The status is represented by a cross [X] if the task is done, and a space [ ] if the task is not done.
     */
    @Test
    public void testToString_taskDone_correctStringRepresentation() {
        String formattedDeadline = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        deadlineTask.setTaskDone();
        assertEquals("[D][X] Submit report (deadline: " + formattedDeadline + ")", deadlineTask.toString());
    }
}
