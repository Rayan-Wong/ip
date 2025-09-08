package bob;

import java.util.List;

public interface TaskService {
    void addTask(TaskServiceRepo repo, Task task);
    List<Task> listTasks(TaskServiceRepo repo);
    void completeTask(TaskServiceRepo repo, int index);
    void uncompleteTask(TaskServiceRepo repo, int index);
    String fetchTask(TaskServiceRepo repo, int index);
}
