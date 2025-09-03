import java.util.ArrayList;

// Class acts as a "dumb" repo: processing is done at service
public class TaskServiceRepo {
    private final ArrayList<Task> repo;

    public TaskServiceRepo() {
        this.repo = new ArrayList<Task>();
    }

    private int validateIndex(int index) {
        if (index <= 0 || index > repo.size()) {
            throw new IndexOutOfBoundsException();
        }
        return index - 1;
    }

    public void add(Task task) {
        repo.add(task);
    }

    public ArrayList<Task> fetchAll() {
        return new ArrayList<Task>(repo);
    }

    public void mark(int index, boolean status) {
        index = validateIndex(index);
        repo.get(index).setDone(status);
    }

    public Task fetchTask(int index) {
        index = validateIndex(index);
        return repo.get(index);
    }

    public int getLength() {
        return repo.size();
    }
}
