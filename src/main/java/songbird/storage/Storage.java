package songbird.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import songbird.exception.SongbirdStorageException;
import songbird.task.Task;
import songbird.ui.Ui;

/**
 * Handles the loading and saving of TaskList data to and from persistent storage.
 * Uses Java's built-in SerDes to store the task objects.
 */
public class Storage {
    private final File databaseFile;

    /**
     * Constructs a new Storage object with the given file path.
     * If the file path does not exist, the directory will be created.
     * If the directory cannot be created, a SongbirdStorageException is thrown.
     *
     * @param filePath The file path to the storage file
     * @throws SongbirdStorageException If the directory cannot be created
     */
    public Storage(String filePath) throws SongbirdStorageException {
        File directory = new File(filePath).getParentFile();
        this.databaseFile = new File(filePath);

        // create directory if it doesn't exist
        if (!directory.exists() && !directory.mkdirs()) {
            throw new SongbirdStorageException("Failed to create directory: " + directory.getPath());
        }
    }

    /**
     * Loads the list of tasks from persistent storage.
     * If the file doesn't exist, returns an empty list.
     * If there are any errors reading from the file, returns an empty list.
     *
     * @return List of tasks loaded from persistent storage
     */
    @SuppressWarnings("unchecked")
    public List<Task> load() {
        if (!databaseFile.exists()) {
            Ui.respond("No existing task list found. Creating new empty task list...");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(databaseFile))) {
            return (List<Task>) ois.readObject();
        } catch (IOException e) {
            Ui.error("Error reading from file: " + e.getMessage());
            Ui.respond("Creating new empty task list...");
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            Ui.error("Error loading task data: " + e.getMessage());
            Ui.respond("Creating new empty task list...");
            return new ArrayList<>();
        }
    }

    /**
     * Saves the list of tasks to persistent storage.
     * This will overwrite any existing data in the file.
     * If there are any errors writing to the file, a SongbirdStorageException is thrown.
     *
     * @param tasks The list of tasks to save to persistent storage
     * @throws SongbirdStorageException If there are any errors writing to the file
     */
    public void save(List<Task> tasks) throws SongbirdStorageException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(databaseFile))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            throw new SongbirdStorageException("Error saving to file: " + e.getMessage());
        }
    }
}
