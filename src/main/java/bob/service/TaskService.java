package bob.service;

import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

public interface TaskService {
    void addTask(TaskServiceRepo repo, Task task);
    List<Task> listTasks(TaskServiceRepo repo);
    void completeTask(TaskServiceRepo repo, int index);
    void uncompleteTask(TaskServiceRepo repo, int index);
    Task fetchTask(TaskServiceRepo repo, int index);
    void deleteTask(TaskServiceRepo repo, int index);
}
