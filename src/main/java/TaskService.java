import java.util.List;

public interface TaskService {
    void addTask(TaskServiceRepo repo, Task task);
    List<String> listTasks(TaskServiceRepo repo);
}
