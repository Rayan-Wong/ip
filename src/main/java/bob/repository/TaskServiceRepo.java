package bob.repository;

import bob.exceptions.BadFileException;
import bob.exceptions.BadIndexException;
import bob.file.FileHelper;
import bob.file.FileHelperImpl;
import bob.models.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to store tasks, fetch them, load and save from file
 */
public class TaskServiceRepo {
    private final ArrayList<Task> repo;
    private final FileHelper fh;

    /**
     * Creates a new TakServiceRepo() instance with its accompanying FileHelper instance
     * @throws BadFileException if a FileHelper could not be instantiated
     */
    public TaskServiceRepo() throws BadFileException {
        try {
            fh = new FileHelperImpl();
            repo = fh.load();
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    /**
     * Validates if index is within range. If it is, returns zero-indexed version from user's one-indexed input
     * @param index the user input
     * @return the zero-indexed input
     * @throws BadIndexException if the input is invalid
     */
    private int validateIndex(int index) throws BadIndexException {
        if (index <= 0 || index > repo.size()) {
            // rationale: Let the adapter layer print the message to see
            throw new BadIndexException("Invalid index: " + index);
        }
        return index - 1;
    }

    /**
     * Adds task to repo and saves state to file
     * @param task the task to be added
     * @throws BadFileException if something went wrong with saving state to file
     */
    public void add(Task task) throws BadFileException {
        try {
            repo.add(task);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    /**
     * Fetches all tasks in repo
     * @return all tasks in an ArrayList
     */
    public ArrayList<Task> fetchAll() {
        return new ArrayList<>(repo);
    }

    /**
     * Marks selected task as done or not done
     * @param index the task's one-indexed index
     * @param status the status to update the task to
     * @throws BadIndexException if user input is invalid
     * @throws BadFileException is state could not be saved to file
     */
    public void mark(int index, boolean status) throws BadIndexException, BadFileException {
        try {
            index = validateIndex(index);
            repo.get(index).setDone(status);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    /**
     * Fetches selected task from user given index
     * @param index the user given index
     * @return the task
     * @throws BadIndexException if the index is not valid
     */
    public Task fetch(int index) throws BadIndexException {
        index = validateIndex(index);
        return repo.get(index);
    }

    /**
     * Deletes selected task from user given index, then saves state to file
     * @param index the user given index
     * @throws BadIndexException if the index is not valid
     * @throws BadFileException if the state could not be saved
     */
    public void remove(int index) throws BadIndexException, BadFileException {
        try {
            index = validateIndex(index);
            repo.remove(index);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    /**
     * Get the current amount of tasks
     * @return the amount of tasks
     */
    public int getLength() {
        return repo.size();
    }

    /**
     * Direct call to save state, mainly used for exiting
     * @throws BadFileException if state could not be saved
     */
    public void saveState() throws BadFileException {
        try {
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException((e.getMessage()));
        }
    }

    /**
     * Find all tasks whose description has the substring specified
     * @param keyword the user specified substring
     * @return a possibly empty Arraylist of tasks with specified substring in its description
     */
    public ArrayList<Task> findAll(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task: repo) {
            if (task.toString().contains(keyword)) {
                result.add(task);
            }
        }
        return result;
    }
}
