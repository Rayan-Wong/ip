package bob.service;

import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

public interface TaskService {
    void addTask(TaskServiceRepo repo, Task task);
    List<Task> listTasks(TaskServiceRepo repo);
    void completeTask(TaskServiceRepo repo, int index) throws RepoException;
    void uncompleteTask(TaskServiceRepo repo, int index) throws RepoException;
    String fetchTask(TaskServiceRepo repo, int index) throws RepoException;
}
