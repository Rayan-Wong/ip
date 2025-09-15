package bob.service;

import bob.exceptions.BadFileException;
import bob.exceptions.BadIndexException;
import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

public class TaskServiceImpl implements TaskService{
    @Override
    public void addTask(TaskServiceRepo repo, Task task) throws BadFileException {
        repo.add(task);
    }

    @Override
    public List<Task> listTasks(TaskServiceRepo repo) {
        List<Task> tasks = repo.fetchAll().stream().toList();
        return tasks;
    }

    @Override
    public void completeTask(TaskServiceRepo repo, int index) throws BadIndexException, BadFileException {
        repo.mark(index, true);
    }

    @Override
    public void uncompleteTask(TaskServiceRepo repo, int index) throws BadIndexException, BadFileException {
        repo.mark(index, false);
    }

    @Override
    public String fetchTask(TaskServiceRepo repo, int index) throws BadIndexException {
        return repo.fetchTask(index).getDesc();
    }
}
