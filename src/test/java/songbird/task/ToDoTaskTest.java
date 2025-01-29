package songbird.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToDoTaskTest {
    private ToDoTask todoTask;

    @BeforeEach
    public void setUp() {
        todoTask = new ToDoTask("Test ToDo Task");
    }

    @Test
    public void testConstructor() {
        assertEquals("Test ToDo Task", todoTask.description);
        assertEquals(TaskType.TODO, todoTask.taskType);
        assertFalse(todoTask.isDone);
    }

    @Test
    public void testSetTaskDone() {
        todoTask.setTaskDone();
        assertTrue(todoTask.isDone);
    }

    @Test
    public void testSetTaskNotDone() {
        todoTask.setTaskDone(); // First mark it as done
        todoTask.setTaskNotDone(); // Then mark it as not done
        assertFalse(todoTask.isDone);
    }

    @Test
    public void testToString() {
        assertEquals("[T][ ] Test ToDo Task", todoTask.toString());
        todoTask.setTaskDone();
        assertEquals("[T][X] Test ToDo Task", todoTask.toString());
    }
}
