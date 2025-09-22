package bob.service;

import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

public interface TaskService {
    void addTask(TaskServiceRepo repo, Task task) throws RepoException;
    List<Task> listTasks(TaskServiceRepo repo);
    void completeTask(TaskServiceRepo repo, int index) throws RepoException;
    void uncompleteTask(TaskServiceRepo repo, int index) throws RepoException;
    Task fetchTask(TaskServiceRepo repo, int index) throws RepoException;
    void deleteTask(TaskServiceRepo repo, int index) throws RepoException;
    void saveTasks(TaskServiceRepo repo) throws RepoException;
    List<Task> findTasks(TaskServiceRepo repo, String keyword);
}
