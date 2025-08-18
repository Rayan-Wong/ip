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
                    List<String> tasks = service.listTasks(repo);
                    int count = 1;
                    for (String taskName: tasks) {
                        System.out.print(count);
                        System.out.print(". ");
                        System.out.println(taskName);
                        count += 1;
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
