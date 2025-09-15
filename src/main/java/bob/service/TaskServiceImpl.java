package bob.service;

import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

public class TaskServiceImpl implements TaskService{
    @Override
    public void addTask(TaskServiceRepo repo, Task task) {
        repo.add(task);
    }

    @Override
    public List<Task> listTasks(TaskServiceRepo repo) {
        List<Task> tasks = repo.fetchAll().stream().toList();
        return tasks;
    }

    @Override
    public void completeTask(TaskServiceRepo repo, int index) {
        repo.mark(index, true);
    }

    @Override
    public void uncompleteTask(TaskServiceRepo repo, int index) {
        repo.mark(index, false);
    }

    @Override
    public Task fetchTask(TaskServiceRepo repo, int index) {
        return repo.fetchTask(index);
    }

    @Override
    public void deleteTask(TaskServiceRepo repo, int index) {
        repo.removeTask(index);
    }
}
