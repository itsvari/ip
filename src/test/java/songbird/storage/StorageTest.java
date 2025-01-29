package songbird.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import songbird.exception.SongbirdStorageException;
import songbird.task.DeadlineTask;
import songbird.task.EventTask;
import songbird.task.Task;
import songbird.task.ToDoTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Storage class in the songbird.storage package.
 * The Storage class is responsible for loading and saving TaskList data to and from persistent storage.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class StorageTest {
    @TempDir
    Path tempDir;

    private Storage storage;
    private Path testFilePath;

    /**
     * Sets up the Storage object before each test using a temporary file.
     * This ensures that each test has its own isolated environment.
     *
     * @throws SongbirdStorageException If there is an error initializing the Storage object.
     */
    @BeforeEach
    public void setUp() throws SongbirdStorageException {
        testFilePath = tempDir.resolve("test_tasklistDB");
        storage = new Storage(testFilePath.toString());
    }

    /**
     * Cleans up the test file after each test to ensure test isolation.
     * Note: With @TempDir, manual cleanup is typically unnecessary as JUnit handles it.
     */
    @AfterEach
    public void tearDown() {
        File file = testFilePath.toFile();
        if (file.exists()) {
            boolean deleted = file.delete();
            assertTrue(deleted, "Failed to delete test file during tearDown: " + testFilePath);
        }
    }

    /**
     * Tests the load() method in the Storage class when no existing file is found.
     * The load() method should return an empty list.
     */
    @Test
    public void testLoad_noExistingFile_returnsEmptyList() {
        File file = testFilePath.toFile();
        if (file.exists()) {
            boolean deleted = file.delete();
            assertTrue(deleted,
                    "Failed to delete existing test file before testLoad_noExistingFile_returnsEmptyList.");
        }

        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty(), "Expected an empty list when no existing file is found.");
    }

    /**
     * Tests saving and loading a non-empty list of tasks.
     * Ensures that all tasks are persisted and loaded correctly.
     */
    @Test
    public void testSaveAndLoad_tasksPersistedCorrectly() throws SongbirdStorageException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new ToDoTask("Test ToDo"));
        tasksToSave.add(new DeadlineTask("Test Deadline",
                LocalDateTime.of(2025, 10, 30, 23, 59)));
        tasksToSave.add(new EventTask("Test Event",
                LocalDateTime.of(2025, 9, 30, 14, 0),
                LocalDateTime.of(2025, 9, 30, 16, 0)));

        storage.save(tasksToSave);

        List<Task> loadedTasks = storage.load();
        assertEquals(tasksToSave.size(), loadedTasks.size(),
                "Expected the same number of tasks after loading.");
        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).toString(), loadedTasks.get(i).toString(),
                    "Expected the same task description after loading.");
        }
    }

    /**
     * Tests saving and loading an empty task list.
     * Ensures that saving an empty list results in an empty list upon loading.
     */
    @Test
    public void testSaveAndLoad_emptyTaskList_returnsEmptyList() throws SongbirdStorageException {
        List<Task> emptyTasks = new ArrayList<>();
        storage.save(emptyTasks);

        List<Task> loadedTasks = storage.load();
        assertTrue(loadedTasks.isEmpty(),
                "Expected an empty list after saving and loading an empty task list.");
    }

    /**
     * Tests loading from a corrupted file.
     * The load() method should handle the corrupted file gracefully by returning an empty list.
     */
    @Test
    public void testLoad_corruptedFile_returnsEmptyList() throws IOException {
        // Create a corrupted file by writing invalid data
        File file = testFilePath.toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("corrupted data that is not serialized Task objects");
        }

        List<Task> loadedTasks = storage.load();
        assertTrue(loadedTasks.isEmpty(), "Expected an empty list when loading from a corrupted file.");
    }

    /**
     * Tests saving and loading tasks with special characters in their descriptions.
     * Ensures that special characters are preserved correctly.
     */
    @Test
    public void testSaveAndLoad_tasksWithSpecialCharacters() throws SongbirdStorageException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new ToDoTask("Task with emojis 😊🚀"));
        tasksToSave.add(new DeadlineTask("Task with newline\nand tab\tcharacters",
                LocalDateTime.of(2025, 12, 25, 12, 0)));
        tasksToSave.add(new EventTask("Task with Unicode characters: 測試, тест, اختبار",
                LocalDateTime.of(2025, 11, 1, 9, 0),
                LocalDateTime.of(2025, 11, 1, 17, 0)));

        storage.save(tasksToSave);

        List<Task> loadedTasks = storage.load();
        assertEquals(tasksToSave.size(), loadedTasks.size(),
                "Expected the same number of tasks after loading.");

        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).toString(), loadedTasks.get(i).toString(),
                    "Expected the same task description with special characters after loading.");
        }
    }

    /**
     * Tests saving and loading tasks with different isDone states.
     * Ensures that the completion status of tasks is preserved.
     */
    @Test
    public void testSaveAndLoad_tasksWithDifferentIsDoneStates() throws SongbirdStorageException {
        List<Task> tasksToSave = new ArrayList<>();
        ToDoTask todo = new ToDoTask("Incomplete ToDo");
        ToDoTask todoDone = new ToDoTask("Completed ToDo");
        todoDone.setTaskDone();

        DeadlineTask deadline = new DeadlineTask("Incomplete Deadline",
                LocalDateTime.of(2025, 10, 30, 23, 59));
        DeadlineTask deadlineDone = new DeadlineTask("Completed Deadline",
                LocalDateTime.of(2025, 12, 31, 23, 59));
        deadlineDone.setTaskDone();

        tasksToSave.add(todo);
        tasksToSave.add(todoDone);
        tasksToSave.add(deadline);
        tasksToSave.add(deadlineDone);

        storage.save(tasksToSave);

        List<Task> loadedTasks = storage.load();
        assertEquals(tasksToSave.size(), loadedTasks.size(),
                "Expected the same number of tasks after loading.");

        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).toString(), loadedTasks.get(i).toString(),
                    "Expected the same task description and isDone state after loading.");
        }
    }

    /**
     * Tests saving and loading multiple times to ensure data integrity over multiple cycles.
     */
    @Test
    public void testMultipleSaveAndLoadCycles() throws SongbirdStorageException {
        List<Task> tasksCycle1 = new ArrayList<>();
        tasksCycle1.add(new ToDoTask("First Cycle ToDo"));
        storage.save(tasksCycle1);

        List<Task> loadedCycle1 = storage.load();
        assertEquals(tasksCycle1.size(), loadedCycle1.size(),
                "Cycle 1: Expected the same number of tasks after loading.");
        assertEquals(tasksCycle1.get(0).toString(), loadedCycle1.get(0).toString(),
                "Cycle 1: Expected the same task description after loading.");

        List<Task> tasksCycle2 = new ArrayList<>(loadedCycle1);
        tasksCycle2.add(new DeadlineTask("Second Cycle Deadline",
                LocalDateTime.of(2026, 1, 1, 0, 0)));
        storage.save(tasksCycle2);

        List<Task> loadedCycle2 = storage.load();
        assertEquals(tasksCycle2.size(), loadedCycle2.size(),
                "Cycle 2: Expected the same number of tasks after loading.");
        assertEquals(tasksCycle2.get(1).toString(), loadedCycle2.get(1).toString(),
                "Cycle 2: Expected the same second task description after loading.");
    }

    /**
     * Tests saving to a read-only file to simulate a save failure.
     * Expects a SongbirdStorageException to be thrown.
     */
    @Test
    public void testSave_errorDuringSave_throwsSongbirdStorageException() throws SongbirdStorageException {
        // Initialize with a valid path
        Storage faultyStorage = new Storage(testFilePath.toString());

        File faultyFile = testFilePath.toFile();

        try {
            // Ensure the file exists
            if (!faultyFile.exists()) {
                boolean created = faultyFile.createNewFile();
                assertTrue(created, "Failed to create test file.");
            }

            // Make the file read-only to simulate a save failure
            boolean setReadOnly = faultyFile.setReadOnly();
            assertTrue(setReadOnly, "Failed to set the test file to read-only.");

            // Attempt to save tasks, expecting a SongbirdStorageException
            List<Task> tasksToSave = new ArrayList<>();
            tasksToSave.add(new ToDoTask("Test Task"));

            assertThrows(SongbirdStorageException.class, () -> faultyStorage.save(tasksToSave),
                    "Expected a SongbirdStorageException to be thrown when saving to a read-only file.");
        } catch (IOException e) {
            fail("IOException occurred while setting up the test: " + e.getMessage());
        } finally {
            // Reset file permissions to allow cleanup
            if (faultyFile.exists()) {
                boolean setWritable = faultyFile.setWritable(true);
                if (!setWritable) {
                    System.err.println("Warning: Failed to reset file permissions for " + faultyFile.getPath());
                }
            }
        }
    }

    /**
     * Tests loading when the Storage object is initialized with a directory instead of a file.
     * Expects load() to return an empty list and handle the scenario gracefully.
     */
    @Test
    public void testLoad_whenFilePathIsDirectory_returnsEmptyList() throws SongbirdStorageException {
        // Create a temporary directory within tempDir
        Path directoryPath = tempDir.resolve("test_directory");
        File directory = directoryPath.toFile();
        boolean dirsCreated = directory.mkdirs();
        assertTrue(dirsCreated, "Failed to create test directory for testLoad_whenFilePathIsDirectory_returnsEmptyList.");

        Storage directoryStorage = new Storage(directoryPath.toString());

        List<Task> tasks = directoryStorage.load();
        assertTrue(tasks.isEmpty(), "Expected an empty list when the file path is a directory.");

        // No need to delete the directory manually; @TempDir handles cleanup
    }
}