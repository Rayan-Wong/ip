package bob;

import bob.adapter.Cli;
import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.service.TaskService;
import bob.service.TaskServiceImpl;

/**
 * This class is responsible for wiring up dependencies together and initialising them
 */
public class Bob {
    public static void main(String[] args) {
        try {
            System.out.println("Hello! I'm Bob");
            System.out.println("What can I do for you?");
            TaskServiceRepo repo = new TaskServiceRepo();
            TaskService service = new TaskServiceImpl();
            Cli.getCommand(repo, service);
            System.out.println("Bye. Hope to see you again soon!");
        } catch (RepoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
