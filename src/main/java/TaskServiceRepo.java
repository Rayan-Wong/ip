import java.util.ArrayList;

// Class acts as a "dumb" repo: processing is done at service
public class TaskServiceRepo {
    private final ArrayList<Task> repo;

    public TaskServiceRepo() {
        this.repo = new ArrayList<Task>();
    }

    public void add(Task task) {
        repo.add(task);
    }

    public ArrayList<Task> fetchAll() {
        return new ArrayList<Task>(repo);
    }
}
