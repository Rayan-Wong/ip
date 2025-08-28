import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

class Cli {
    public static void getCommand(TaskServiceRepo repo, TaskService service) {
        Scanner sc = new Scanner(System.in);
        // Continuously loop CLI until "bye" command is entered
        while (true) {
            // strip excess spaces
            String[] cmd = sc.nextLine().replaceAll("\\s+", " ").split(" ");
            switch (cmd[0]) {
            case "bye":
                return;

            case "add":
                if (cmd.length <= 1) {
                    System.out.println("No task name specified!");
                } else {
                    String task_name = Arrays.stream(cmd, 1, cmd.length).collect(Collectors.joining(" "));
                    Task task = new Task(task_name);
                    service.addTask(repo, task);
                    System.out.println(task_name);
                }
                break;

            case "list":
                List<Task> tasks = service.listTasks(repo);
                int count = 1;
                for (Task task: tasks) {
                    System.out.println(count + ". [" + (task.getDone() ? "X" : "") + "] " + task.getDesc());
                    count += 1;
                }
                break;

            case "mark":
                if (cmd.length <= 1) {
                    System.out.println("No task index specified!");
                } else {
                    int index = Integer.parseInt(cmd[1]);
                    try {
                        service.completeTask(repo, index);
                        String task = service.fetchTask(repo, index);
                        System.out.println("I've marked the following task as done: " + task);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index: " + index);
                        continue;
                    }
                }
                break;

            case "unmark":
                if (cmd.length <= 1) {
                    System.out.println("No task index specified!");
                } else {
                    int index = Integer.parseInt(cmd[1]);
                    try {
                        service.uncompleteTask(repo, index);
                        String task = service.fetchTask(repo, index);
                        System.out.println("I've unmarked the following task as done: " + task);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index: " + index);
                        continue;
                    }
                }
                break;

            default:
                String text = String.join(" ", cmd);
                print(text);
            }
        }
    }

    private static void print(String text) {
        System.out.println(text);
    }
}
