import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl implements TaskService{
    @Override
    public void addTask(TaskServiceRepo repo, Task task) {
        repo.add(task);
    }

    @Override
    public List<String> listTasks(TaskServiceRepo repo) {
        ArrayList<Task> tasks = repo.fetchAll();
        List<String> result = tasks.stream().map(Task::getDesc).toList();
        return result;
    }
}
