package songbird.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import songbird.exception.SongbirdNonExistentTaskException;
import songbird.exception.SongbirdStorageException;
import songbird.storage.Storage;

/**
 * Unit tests for the TaskList class.
 * Tests the functionality of managing tasks including adding, deleting,
 * retrieving, and filtering tasks by date.
 */
public class TaskListTest {

    private Storage mockStorage;
    private TaskList taskList;

    /**
     * Sets up the test environment before each test method.
     * Creates a mock Storage object and initializes an empty TaskList.
     */
    @BeforeEach
    public void setUp() {
        // Mock the Storage dependency
        mockStorage = mock(Storage.class);

        // Initialize TaskList with empty initial tasks
        taskList = new TaskList(mockStorage);
    }

    /**
     * Tests adding a task to the TaskList.
     * Verifies that the task is correctly added and storage is updated.
     *
     * @throws SongbirdStorageException If there is an error saving the task list.
     */
    @SuppressWarnings("unchecked") // Suppress unchecked cast warning for mockito.anyList()
    @Test
    public void testAddTask() throws SongbirdStorageException {
        // Arrange
        Task task = new ToDoTask("Read a book");

        // Act
        Task addedTask = taskList.addTask(task);

        // Assert
        assertEquals(task, addedTask, "The added task should match the input task.");
        assertEquals(1, taskList.getSize(), "TaskList size should be 1 after adding a task.");

        // Verify that storage.save was called with the correct task list
        ArgumentCaptor<List<Task>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockStorage).save(captor.capture());
        List<Task> savedTasks = captor.getValue();
        assertEquals(1, savedTasks.size(), "Saved task list should contain one task.");
        assertEquals(task, savedTasks.get(0), "Saved task should match the added task.");
    }

    /**
     * Tests deleting a task at a valid index.
     * Verifies that the correct task is deleted and storage is updated.
     *
     * @throws SongbirdStorageException If there is an error saving the task list.
     */
    @Test
    public void testDeleteTaskValidIndex() throws SongbirdStorageException {
        // Arrange
        Task task1 = new ToDoTask("Read a book");
        Task task2 = new DeadlineTask("Submit assignment", LocalDateTime.now().plusDays(1));
        taskList.addTask(task1);
        taskList.addTask(task2);
        assertEquals(2, taskList.getSize(), "TaskList should have 2 tasks before deletion.");

        // Act
        Task deletedTask = null;
        try {
            deletedTask = taskList.deleteTask(0);
        } catch (SongbirdNonExistentTaskException e) {
            fail("Exception should not be thrown for a valid index.");
        }

        // Assert
        assertEquals(task1, deletedTask, "Deleted task should be the first task added.");
        assertEquals(1, taskList.getSize(), "TaskList size should be 1 after deletion.");

        // Verify that storage.save was called three times (twice for adds, once for delete)
        verify(mockStorage, times(3)).save(anyList());
    }

    /**
     * Tests deleting a task at an invalid index.
     * Verifies that the appropriate exception is thrown.
     */
    @Test
    public void testDeleteTaskInvalidIndex() {
        // Arrange
        Task task = new ToDoTask("Read a book");
        taskList.addTask(task);
        assertEquals(1, taskList.getSize(), "TaskList should have 1 task.");

        // Act & Assert
        assertThrows(SongbirdNonExistentTaskException.class, () -> {
            taskList.deleteTask(1); // Invalid index
        }, "Deleting with an invalid index should throw SongbirdNonExistentTaskException.");

        // Ensure size remains unchanged
        assertEquals(1, taskList.getSize(), "TaskList size should remain 1 after failed deletion.");
    }

    /**
     * Tests retrieving a task at valid indices.
     * Verifies that the correct tasks are retrieved.
     *
     * @throws SongbirdNonExistentTaskException If the task index is invalid.
     */
    @Test
    public void testGetTaskValidIndex() throws SongbirdNonExistentTaskException {
        // Arrange
        Task task1 = new ToDoTask("Read a book");
        Task task2 = new EventTask("Attend meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        taskList.addTask(task1);
        taskList.addTask(task2);

        // Act
        Task retrievedTask1 = taskList.getTask(0);
        Task retrievedTask2 = taskList.getTask(1);

        // Assert
        assertEquals(task1, retrievedTask1, "Retrieved task should match the first task.");
        assertEquals(task2, retrievedTask2, "Retrieved task should match the second task.");
    }

    /**
     * Tests retrieving a task at invalid indices.
     * Verifies that the appropriate exception is thrown.
     */
    @Test
    public void testGetTaskInvalidIndex() {
        // Arrange
        Task task = new ToDoTask("Read a book");
        taskList.addTask(task);

        // Act & Assert
        assertThrows(SongbirdNonExistentTaskException.class, () -> {
            taskList.getTask(-1); // Negative index
        }, "Getting a task with negative index should throw SongbirdNonExistentTaskException.");

        assertThrows(SongbirdNonExistentTaskException.class, () -> {
            taskList.getTask(1); // Index equal to size
        }, "Getting a task with index equal to size should throw SongbirdNonExistentTaskException.");
    }

    /**
     * Tests saving the task list successfully.
     * Verifies that the storage.save method is called with correct arguments.
     *
     * @throws SongbirdStorageException If there is an error saving the task list.
     */
    @SuppressWarnings("unchecked") // Suppress unchecked cast warning for mockito.anyList()
    @Test
    public void testSaveListSuccess() throws SongbirdStorageException {
        // Arrange
        Task task = new ToDoTask("Read a book");
        taskList.addTask(task);

        // Act
        taskList.saveList(); // Manually invoke saveList

        // Assert
        verify(mockStorage, times(2)).save(anyList()); // Once for addTask, once for saveList

        // To further verify, capture the argument
        ArgumentCaptor<List<Task>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockStorage, times(2)).save(captor.capture());
        List<Task> savedTasks = captor.getAllValues().get(1); // The second save is from saveList
        assertEquals(1, savedTasks.size(), "Saved task list should contain one task.");
        assertEquals(task, savedTasks.get(0), "Saved task should match the added task.");
    }

    /**
     * Tests the task count message generation.
     * Verifies correct messages for different numbers of tasks.
     */
    @Test
    public void testGetTaskCountMessage() {
        // Arrange
        assertEquals("You now have 0 task(s).", taskList.getTaskCountMessage(),
                "Initial task count message should indicate 0 tasks.");

        Task task1 = new ToDoTask("Read a book");
        taskList.addTask(task1);
        assertEquals("You now have 1 task(s).", taskList.getTaskCountMessage(),
                "Task count message should indicate 1 task.");

        Task task2 = new DeadlineTask("Submit assignment", LocalDateTime.now().plusDays(1));
        taskList.addTask(task2);
        assertEquals("You now have 2 task(s).", taskList.getTaskCountMessage(),
                "Task count message should indicate 2 tasks.");
    }

    /**
     * Tests retrieving tasks by date when the list is empty.
     * Verifies that an empty list is returned.
     */
    @Test
    public void testGetTasksByDate_noTasks() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(date);

        // Assert
        assertTrue(tasksOnDate.isEmpty(), "getTasksByDate should return an empty list when there are no tasks.");
    }

    /**
     * Tests retrieving tasks by date with only ToDoTasks.
     * Verifies that ToDoTasks are not included in date-filtered results.
     */
    @Test
    public void testGetTasksByDate_onlyToDoTasks() {
        // Arrange
        taskList.addTask(new ToDoTask("Read a book"));
        taskList.addTask(new ToDoTask("Write code"));
        LocalDateTime date = LocalDateTime.now();

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(date);

        // Assert
        assertTrue(tasksOnDate.isEmpty(), "ToDoTasks should not be included in getTasksByDate results.");
    }

    /**
     * Tests retrieving tasks by date with a deadline task matching the target date.
     * Verifies that matching deadline tasks are included in results.
     */
    @Test
    public void testGetTasksByDate_deadlineTaskMatchingDate() {
        // Arrange
        LocalDateTime deadline = LocalDateTime.of(2025, 10, 30, 23, 59);
        DeadlineTask deadlineTask = new DeadlineTask("Submit report", deadline);
        taskList.addTask(deadlineTask);

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(deadline);

        // Assert
        assertEquals(1, tasksOnDate.size(), "There should be one task matching the deadline date.");
        assertTrue(tasksOnDate.contains(deadlineTask), "The DeadlineTask should be included in the results.");
    }

    /**
     * Tests retrieving tasks by date with a deadline task not matching the target date.
     * Verifies that non-matching deadline tasks are excluded from results.
     * The target date is set to the day after the deadline.
     */
    @Test
    public void testGetTasksByDate_deadlineTaskNonMatchingDate() {
        // Arrange
        LocalDateTime deadline = LocalDateTime.of(2025, 10, 30, 23, 59);
        DeadlineTask deadlineTask = new DeadlineTask("Submit report", deadline);
        taskList.addTask(deadlineTask);
        LocalDateTime targetDate = deadline.plusDays(1); // Next day

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(targetDate);

        // Assert
        assertTrue(tasksOnDate.isEmpty(), "No tasks should match the non-deadline date.");
    }

    /**
     * Tests retrieving tasks by date with an event task overlapping the target date.
     * Verifies that overlapping event tasks are included in results.
     * The target date is set to the start date of the event.
     * The EventTask spans from 2pm to 4pm on the target date.
     */
    @Test
    public void testGetTasksByDate_eventTaskOverlappingDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2025, 9, 30, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 9, 30, 16, 0);
        EventTask eventTask = new EventTask("Attend conference", start, end);
        taskList.addTask(eventTask);
        LocalDateTime targetDate = LocalDateTime.of(2025, 9, 30, 0, 0, 0);

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(targetDate);

        // Assert
        assertEquals(1, tasksOnDate.size(), "EventTask overlapping the date should be included.");
        assertTrue(tasksOnDate.contains(eventTask), "The EventTask should be included in the results.");
    }

    /**
     * Tests retrieving tasks by date with an event task spanning multiple days.
     * Verifies correct handling of multi-day events.
     */
    @Test
    public void testGetTasksByDate_eventTaskSpanningMultipleDays() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2025, 12, 31, 22, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 1, 2, 0);
        EventTask eventTask = new EventTask("New Year Party", start, end);
        taskList.addTask(eventTask);

        // Test on the first day
        LocalDateTime firstDay = LocalDateTime.of(2025, 12, 31, 0, 0, 0);
        List<Task> tasksOnFirstDay = taskList.getTasksByDate(firstDay);
        assertEquals(1, tasksOnFirstDay.size(), "EventTask should be included on the start date.");
        assertTrue(tasksOnFirstDay.contains(eventTask), "The EventTask should be included on the start date.");

        // Test on the second day
        LocalDateTime secondDay = LocalDateTime.of(2026, 1, 1, 0, 0, 0);
        List<Task> tasksOnSecondDay = taskList.getTasksByDate(secondDay);
        assertEquals(1, tasksOnSecondDay.size(), "EventTask should be included on the end date.");
        assertTrue(tasksOnSecondDay.contains(eventTask), "The EventTask should be included on the end date.");

        // Test on a day outside the event duration
        LocalDateTime outsideDay = LocalDateTime.of(2026, 1, 2, 0, 0, 0);
        List<Task> tasksOnOutsideDay = taskList.getTasksByDate(outsideDay);
        assertTrue(tasksOnOutsideDay.isEmpty(), "EventTask should not be included on a day outside its duration.");
    }

    /**
     * Tests retrieving tasks by date with a mix of different task types.
     * Verifies correct filtering of tasks based on date criteria.
     */
    @Test
    public void testGetTasksByDate_mixedTasks() {
        // Arrange
        // ToDoTask - should not be included
        Task todo = new ToDoTask("Read a book");
        taskList.addTask(todo);

        // DeadlineTask - matches date
        LocalDateTime deadline = LocalDateTime.of(2025, 10, 30, 23, 59);
        DeadlineTask deadlineTask = new DeadlineTask("Submit report", deadline);
        taskList.addTask(deadlineTask);

        // EventTask - spans the date
        LocalDateTime start = LocalDateTime.of(2025, 10, 30, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 30, 12, 0);
        EventTask eventTask = new EventTask("Team Meeting", start, end);
        taskList.addTask(eventTask);

        // EventTask - does not include the date
        LocalDateTime start2 = LocalDateTime.of(2025, 11, 1, 9, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 11, 1, 11, 0);
        EventTask eventTask2 = new EventTask("Workshop", start2, end2);
        taskList.addTask(eventTask2);

        LocalDateTime targetDate = LocalDateTime.of(2025, 10, 30, 0, 0, 0);

        // Act
        List<Task> tasksOnDate = taskList.getTasksByDate(targetDate);

        // Assert
        assertEquals(2, tasksOnDate.size(), "Should return DeadlineTask and EventTask that match the date.");
        assertTrue(tasksOnDate.contains(deadlineTask), "DeadlineTask should be included.");
        assertTrue(tasksOnDate.contains(eventTask), "EventTask should be included.");
        assertFalse(tasksOnDate.contains(todo), "ToDoTask should not be included.");
        assertFalse(tasksOnDate.contains(eventTask2), "EventTask not matching the date should not be included.");
    }

    /**
     * Tests the string representation of an empty task list.
     * Verifies correct message for empty list.
     */
    @Test
    public void testToStringEmptyTaskList() {
        // Arrange & Act
        String result = taskList.toString();

        // Assert
        assertEquals("You have no saved tasks.", result, "toString should indicate no saved tasks.");
    }

    /**
     * Tests the string representation of a task list containing tasks.
     * Verifies correct formatting of task list output.
     */
    @Test
    public void testToStringWithTasks() {
        // Arrange
        Task todo = new ToDoTask("Read a book");
        Task deadline = new DeadlineTask("Submit report", LocalDateTime.of(2025, 10, 30, 23, 59));
        Task event = new EventTask("Team Meeting", LocalDateTime.of(2025, 10, 30, 10, 0),
                LocalDateTime.of(2025, 10, 30, 12, 0));
        taskList.addTask(todo);
        taskList.addTask(deadline);
        taskList.addTask(event);

        String expectedOutput = """
                Your saved tasks are:
                1. [T][ ] Read a book
                2. [D][ ] Submit report (deadline: 2025-10-30T23:59:00)
                3. [E][ ] Team Meeting (from: 2025-10-30T10:00:00 to: 2025-10-30T12:00:00)""";

        // Act
        String result = taskList.toString();

        // Assert
        assertEquals(expectedOutput, result, "toString should list all tasks with correct formatting.");
    }

    /**
     * Tests adding duplicate tasks to the task list.
     * Verifies that duplicate tasks are allowed and handled correctly.
     */
    @Test
    public void testAddDuplicateTasks() {
        // Arrange
        Task task1 = new ToDoTask("Read a book");
        Task task2 = new ToDoTask("Read a book"); // Duplicate description
        taskList.addTask(task1);

        // Act
        taskList.addTask(task2);

        // Assert
        assertEquals(2, taskList.getSize(), "TaskList should allow duplicate tasks.");
        try {
            assertEquals(task1, taskList.getTask(0), "First task should be task1.");
            assertEquals(task2, taskList.getTask(1), "Second task should be task2.");
        } catch (SongbirdNonExistentTaskException e) {
            fail("Exception should not be thrown for valid indices.");
        }
    }

    /**
     * Tests marking a task as done.
     * Verifies that the task's status is updated and storage is called.
     */
    @Test
    public void testMarkTaskAsDone() throws SongbirdStorageException {
        // Arrange
        Task task = new ToDoTask("Read a book");
        taskList.addTask(task);
        assertFalse(task.isDone, "Task should initially be not done.");

        // Act
        task.setTaskDone();
        taskList.saveList(); // Save the state after marking done

        // Assert
        assertTrue(task.isDone, "Task should be marked as done.");

        // Verify that storage.save was called twice (add and save)
        verify(mockStorage, times(2)).save(anyList());
    }

    /**
     * Tests marking a task as not done.
     * Verifies that the task's status is updated and storage is called.
     */
    @Test
    public void testMarkTaskAsNotDone() throws SongbirdStorageException {
        // Arrange
        Task task = new ToDoTask("Read a book");
        task.setTaskDone(); // Initially set as done
        taskList.addTask(task);
        assertTrue(task.isDone, "Task should initially be done.");

        // Act
        task.setTaskNotDone();
        taskList.saveList(); // Save the state after marking not done

        // Assert
        assertFalse(task.isDone, "Task should be marked as not done.");

        // Verify that storage.save was called twice (add and save)
        verify(mockStorage, times(2)).save(anyList());
    }

    /**
     * Tests the encapsulation of the getAll method.
     * Verifies that modifications to the returned list don't affect the original list.
     */
    @Test
    public void testGetAllEncapsulation() {
        // Arrange
        Task task = new ToDoTask("Read a book");
        taskList.addTask(task);

        // Act
        List<Task> allTasks = taskList.getAll();

        // Attempt to modify the returned list
        allTasks.add(new ToDoTask("New Task"));

        // Assert
        assertEquals(1, taskList.getSize(),
                "Modifying the returned list should not affect the original TaskList.");
    }

    /**
     * Tests retrieving tasks by keyword when the list is empty.
     * Verifies that an empty list is returned.
     */
    @Test
    public void testGetTasksByKeyword_noTasks() {
        // Act
        List<Task> tasksWithKeyword = taskList.getTasksByKeyword("book");

        // Assert
        assertTrue(tasksWithKeyword.isEmpty(),
                "getTasksByKeyword should return an empty list when there are no tasks.");
    }

    /**
     * Tests retrieving tasks by keyword with no matching tasks.
     * Verifies that an empty list is returned.
     */
    @Test
    public void testGetTasksByKeyword_noMatchingTasks() {
        // Arrange
        taskList.addTask(new ToDoTask("Read a book"));
        taskList.addTask(new ToDoTask("Write code"));

        // Act
        List<Task> tasksWithKeyword = taskList.getTasksByKeyword("exercise");

        // Assert
        assertTrue(tasksWithKeyword.isEmpty(),
                "getTasksByKeyword should return an empty list when there are no matching tasks.");
    }

    /**
     * Tests retrieving tasks by keyword with matching tasks.
     * Verifies that the correct tasks are returned.
     */
    @Test
    public void testGetTasksByKeyword_matchingTasks() {
        // Arrange
        Task task1 = new ToDoTask("Read a book");
        Task task2 = new ToDoTask("Write code");
        Task task3 = new ToDoTask("Read another book");
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        // Act
        List<Task> tasksWithKeyword = taskList.getTasksByKeyword("book");

        // Assert
        assertEquals(2, tasksWithKeyword.size(), "getTasksByKeyword should return two tasks.");
        assertTrue(tasksWithKeyword.contains(task1), "The list should contain the first task.");
        assertTrue(tasksWithKeyword.contains(task3), "The list should contain the third task.");
    }

    /**
     * Tests retrieving tasks by keyword with case-insensitive matching.
     * Verifies that the correct tasks are returned.
     */
    @Test
    public void testGetTasksByKeyword_caseInsensitive() {
        // Arrange
        Task task1 = new ToDoTask("Read a Book");
        Task task2 = new ToDoTask("Write code");
        Task task3 = new ToDoTask("Read another book");
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        // Act
        List<Task> tasksWithKeyword = taskList.getTasksByKeyword("BOOK");

        // Assert
        assertEquals(2, tasksWithKeyword.size(), "getTasksByKeyword should return two tasks.");
        assertTrue(tasksWithKeyword.contains(task1), "The list should contain the first task.");
        assertTrue(tasksWithKeyword.contains(task3), "The list should contain the third task.");
    }
}
