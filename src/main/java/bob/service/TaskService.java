package bob.service;

import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.util.List;

/**
 * Encapsulates services to handle task related requests
 */
public interface TaskService {
    /**
     * Adds task to repo
     * @param repo the task's repo
     * @param task the task to be added
     * @throws RepoException if the repo encountered an exception
     */
    void addTask(TaskServiceRepo repo, Task task) throws RepoException;
    /**
     * List all tasks in the repo
     * @param repo the task's repo
     * @return the list of tasks in the repo
     */
    List<Task> listTasks(TaskServiceRepo repo);
    /**
     * Marks a selected task by user's index as done
     * @param repo the task's repo
     * @param index the user supplied index
     * @throws RepoException if the repo encountered an exception
     */
    void completeTask(TaskServiceRepo repo, int index) throws RepoException;
    /**
     * Marks a selected task by user's index as not done
     * @param repo the task's repo
     * @param index the user supplied index
     * @throws RepoException if the repo encountered an exception
     */
    void uncompleteTask(TaskServiceRepo repo, int index) throws RepoException;

    /**
     * Fetches a selected task by user's index
     * @param repo the task's repo
     * @param index the user supplied index
     * @return the user's selected task
     * @throws RepoException if the repo encountered an exception
     */
    Task fetchTask(TaskServiceRepo repo, int index) throws RepoException;
    /**
     * Deletes a selected task by user's index
     * @param repo the task's repo
     * @param index the user supplied index
     * @throws RepoException if the repo encountered an exception
     */
    void deleteTask(TaskServiceRepo repo, int index) throws RepoException;

    /**
     * Saves the state of repo to a file
     * @param repo the task's repo
     * @throws RepoException if the repo encountered an exception
     */
    void saveTasks(TaskServiceRepo repo) throws RepoException;

    /**
     * Fetches all tasks whose description has the user supplied substring
     * @param repo the task's repo
     * @param keyword the user supplied keyword
     * @return a possible empty List of tasks whose description has the substring
     */
    List<Task> findTasks(TaskServiceRepo repo, String keyword);
}
