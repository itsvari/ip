package songbird.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ToDoTask class.
 */
public class ToDoTaskTest {
    private ToDoTask todoTask;

    /**
     * Sets up the ToDoTask object before each test.
     */
    @BeforeEach
    public void setUp() {
        todoTask = new ToDoTask("Test ToDo Task");
    }

    /**
     * Tests the constructor of the ToDoTask class.
     * The constructor should correctly initialize the ToDoTask object with the specified description and task type.
     */
    @Test
    public void constructor_initializesFieldsCorrectly() {
        assertEquals("Test ToDo Task", todoTask.description);
        assertEquals(TaskType.TODO, todoTask.taskType);
        assertFalse(todoTask.isDone);
    }

    /**
     * Tests the setTaskDone method of the ToDoTask class.
     * The method should correctly mark the task as done.
     */
    @Test
    public void setTaskDone_marksTaskAsDone() {
        todoTask.setTaskDone();
        assertTrue(todoTask.isDone);
    }

    /**
     * Tests the setTaskNotDone method of the ToDoTask class.
     * The method should correctly mark the task as not done.
     */
    @Test
    public void setTaskNotDone_marksTaskAsNotDone() {
        todoTask.setTaskDone(); // First mark it as done
        todoTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(todoTask.isDone);
    }

    /**
     * Tests the toString method of the ToDoTask class.
     * The method should return the correct string representation of the ToDoTask object.
     * The string representation should include the task type, completion status, and description.
     * The completion status should be represented by [X] if the task is done, and [ ] if the task is not done.
     * The task type should be represented by [T].
     * The description should be the same as the description provided during initialization.
     * The string representation should be correct for both done and not done tasks.
     */
    @Test
    public void toString_returnsCorrectStringRepresentation() {
        assertEquals("[T][ ] Test ToDo Task", todoTask.toString());
        todoTask.setTaskDone();
        assertEquals("[T][X] Test ToDo Task", todoTask.toString());
    }
}
