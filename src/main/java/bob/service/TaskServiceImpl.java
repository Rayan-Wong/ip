package bob.service;

import bob.exceptions.BadFileException;
import bob.exceptions.BadIndexException;
import bob.models.Deadline;
import bob.models.Event;
import bob.repository.TaskServiceRepo;
import bob.models.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Task fetchTask(TaskServiceRepo repo, int index) throws BadIndexException {
        return repo.fetch(index);
    }

    @Override
    public void deleteTask(TaskServiceRepo repo, int index) throws BadIndexException, BadFileException {
        repo.remove(index);
    }

    @Override
    public void saveTasks(TaskServiceRepo repo) throws BadFileException {
        repo.saveState();
    }

    @Override
    public List<Task> findTasks(TaskServiceRepo repo, String keyword) {
        return repo.findAll(keyword).stream().toList();
    }

    @Override
    public List<Task> findTasksWithDate(TaskServiceRepo repo, LocalDate date) {
        List<Task> tasks = repo.fetchAll();
        ArrayList<Task> results = new ArrayList<>();
        for (Task task: tasks) {
            if (task instanceof Deadline && ((Deadline) task).getDeadline().equals(date)) {
                results.add(task);
            } else if (task instanceof Event) {
                // Business logic: We say the date is within an event's duration if the day itself falls
                // anytime between from and end datetime of event, inclusive
                LocalDateTime queryDateTime = date.atStartOfDay();
                Event eventTask = (Event) task;
                // isBefore() and isAfter() are exclusive but we want inclusive
                boolean before = (eventTask.getFrom().isBefore(queryDateTime) ||
                        (eventTask.getFrom().isEqual(queryDateTime)));
                boolean after = (eventTask.getTo().isAfter(queryDateTime) ||
                        (eventTask.getTo().isEqual(queryDateTime)));
                if (before && after) {
                    results.add(task);
                }
            }
        }
        return results;
    }
}
