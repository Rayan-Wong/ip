package bob.repository;

import bob.exceptions.BadFileException;
import bob.exceptions.BadIndexException;
import bob.file.FileHelper;
import bob.file.FileHelperImpl;
import bob.models.Task;

import java.io.IOException;
import java.util.ArrayList;

// Class acts as a "dumb" repo: processing is done at Service
public class TaskServiceRepo {
    private final ArrayList<Task> repo;
    private final FileHelper fh;

    public TaskServiceRepo() throws BadFileException {
        try {
            fh = new FileHelperImpl();
            repo = fh.load();
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    private int validateIndex(int index) throws BadIndexException {
        if (index <= 0 || index > repo.size()) {
            // rationale: Let the adapter layer print the message to see
            throw new BadIndexException("Invalid index: " + index);
        }
        return index - 1;
    }

    public void add(Task task) throws BadFileException {
        try {
            repo.add(task);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    public ArrayList<Task> fetchAll() {
        return new ArrayList<>(repo);
    }

    public void mark(int index, boolean status) throws BadIndexException, BadFileException {
        try {
            index = validateIndex(index);
            repo.get(index).setDone(status);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    public Task fetch(int index) throws BadIndexException {
        index = validateIndex(index);
        return repo.get(index);
    }

    public void remove(int index) throws BadIndexException, BadFileException {
        try {
            index = validateIndex(index);
            repo.remove(index);
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException(e.getMessage());
        }
    }

    public int getLength() {
        return repo.size();
    }

    public void saveState() throws BadFileException {
        try {
            fh.save(repo);
        } catch (IOException e) {
            throw new BadFileException((e.getMessage()));
        }
    }
}
