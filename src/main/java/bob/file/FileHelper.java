package bob.file;

import bob.models.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents an interface between repo and file for file-saving capability
 */
public interface FileHelper {
    /**
     * Loads data from file
     * @return the loaded data from file
     * @throws IOException if file could not be found or error with file
     */
    ArrayList<Task> load() throws IOException;

    /**
     * Saves data to file
     * @param state the current state
     * @throws IOException if file could not be found or error with file
     */
    void save(ArrayList<Task> state) throws IOException;
}
