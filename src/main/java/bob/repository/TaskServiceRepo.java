package bob.repository;

import bob.exceptions.BadIndexException;
import bob.models.Task;

import java.util.ArrayList;

// Class acts as a "dumb" repo: processing is done at Service
public class TaskServiceRepo {
    private final ArrayList<Task> repo;

    public TaskServiceRepo() {
        this.repo = new ArrayList<Task>();
    }

    private int validateIndex(int index) throws BadIndexException {
        if (index <= 0 || index > repo.size()) {
            // rationale: Let the adapter layer print the message to see
            throw new BadIndexException("Invalid index: " + index);
        }
        return index - 1;
    }

    public void add(Task task) {
        repo.add(task);
    }

    public ArrayList<Task> fetchAll() {
        return new ArrayList<Task>(repo);
    }

    public void mark(int index, boolean status) throws BadIndexException {
        index = validateIndex(index);
        repo.get(index).setDone(status);
    }

    public Task fetchTask(int index) throws BadIndexException {
        index = validateIndex(index);
        return repo.get(index);
    }

    public void removeTask(int index) throws BadIndexException {
        index = validateIndex(index);
        repo.remove(index);
    }

    public int getLength() {
        return repo.size();
    }
}
