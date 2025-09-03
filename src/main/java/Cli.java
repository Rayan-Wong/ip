import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

class Cli {
    public static void printSuccess(Task task, TaskServiceRepo repo) {
        int length = repo.getLength();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + length + (length == 1 ? " task " : " tasks ") + "in the list.");
    }

    public static boolean checkCommand(String[] cmd) {
        if (cmd.length <= 1) {
            System.out.println("No task name specified!");
            return false;
        }
        return true;
    }

    public static int indexFinder(String[] cmd, String target) {
        int index = Arrays.asList(cmd).indexOf(target);
        return index;
    }

    public static void getCommand(TaskServiceRepo repo, TaskService service) {
        Scanner sc = new Scanner(System.in);
        // Continuously loop CLI until "bye" command is entered
        while (true) {
            // strip excess spaces
            String[] cmd = sc.nextLine().replaceAll("\\s+", " ").split(" ");
            switch (cmd[0]) {
            case "bye":
                return;

            case "todo":
                if (checkCommand(cmd)) {
                    String todo_name = Arrays.stream(cmd, 1, cmd.length).collect(Collectors.joining(" "));
                    ToDo todo = new ToDo(todo_name);
                    service.addTask(repo, todo);
                    printSuccess(todo, repo);
                }
                break;

            case "deadline":
                if (checkCommand(cmd)) {
                    int byIndex = indexFinder(cmd, "/by");
                    if (byIndex == -1) {
                        System.out.println("Please specify a deadline!");
                        break;
                    }
                    String deadline_name = Arrays.stream(cmd, 1, byIndex).collect(Collectors.joining(" "));
                    String due_date = Arrays.stream(cmd, byIndex + 1, cmd.length).collect(Collectors.joining(" "));
                    Deadline deadline = new Deadline(deadline_name, due_date);
                    service.addTask(repo, deadline);
                    printSuccess(deadline, repo);
                    break;
                }
                break;

            case "event":
                if (checkCommand(cmd)) {
                    int fromIndex = indexFinder(cmd, "/from");
                    if (fromIndex == -1) {
                        System.out.println("Please specify a start date");
                        break;
                    }
                    int toIndex = indexFinder(cmd, "/to");
                    if (toIndex == -1) {
                        System.out.println("Please specify an end date");
                        break;
                    }
                    String event_name = Arrays.stream(cmd, 1, fromIndex).collect(Collectors.joining(" "));
                    String from = Arrays.stream(cmd, fromIndex + 1, toIndex).collect(Collectors.joining(" "));
                    String to = Arrays.stream(cmd, toIndex + 1, cmd.length).collect(Collectors.joining(" "));
                    Event event = new Event(event_name, from, to);
                    service.addTask(repo, event);
                    printSuccess(event, repo);
                    break;
                }
                break;

            case "list":
                List<Task> tasks = service.listTasks(repo);
                int count = 1;
                for (Task task: tasks) {
                    System.out.println(count + "." + task);
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
                // add task functionality
                System.out.println("Sorry, please type a valid input!");
            }
        }
    }
}
