package bob.adapter;

import bob.exceptions.BadArgumentException;
import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.models.Deadline;
import bob.models.Event;
import bob.models.Task;
import bob.models.ToDo;
import bob.service.TaskService;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

public class Cli {
    public static final String TODO_SYNTAX = "todo <task name>";
    public static final String DEADLINE_SYNTAX = "deadline <deadline name> /by <deadline>";
    public static final String EVENT_SYNTAX = "event <event name> /from <from date> /to <to date>";
    public static final String MARK_SYNTAX = "mark <index>";
    public static final String UNMARK_SYNTAX = "unmark <index>";
    public static final String DELETE_SYNTAX = "delete <index>";
    public static final String FIND_SYNTAX = "find <str>";

    public static final String BY_DEADLINE = "Please specify a deadline with /by <deadline>!";
    public static final String START_EVENT = "Please specify a start date with /from <start date>!";
    public static final String END_EVENT = "Please specify an end date with /to <end_date>!";

    public static void printAddSuccess(Task task, TaskServiceRepo repo) {
        int length = repo.getLength();

        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + length + (length == 1 ? " task " : " tasks ") + "in the list.");
    }

    public static void printDeleteSuccess(Task task, TaskServiceRepo repo) {
        int length = repo.getLength();

        System.out.println("Got it. I've deleted this task:");
        System.out.println(task);
        System.out.println("Now you have " + length + (length == 1 ? " task " : " tasks ") + "in the list.");
    }

    public static boolean checkCommand(String[] cmd, String correctSyntax) {
        if (cmd.length <= 1) {
            System.out.println("Invalid syntax!");
            System.out.println("Correct Syntax: " + correctSyntax);
            return false;
        }
        return true;
    }

    public static int indexFinder(String[] cmd, String target, int endIndex, String specifiedSyntax) throws BadArgumentException {
        int index = Arrays.asList(cmd).indexOf(target);
        if (index == -1 || endIndex - index == 1) {
            throw new BadArgumentException(specifiedSyntax);
        }
        return index;
    }

    public static String join(String[] cmd, int startInclusive, int endExclusive) {
        return Arrays.stream(cmd, startInclusive, endExclusive).collect(Collectors.joining(" "));
    }

    public static void getCommand(TaskServiceRepo repo, TaskService service) {
        Scanner sc = new Scanner(System.in);
        // Continuously loop CLI until "bye" command is entered
        while (true) {
            // strip excess spaces
            String[] cmd = sc.nextLine().replaceAll("\\s+", " ").split(" ");
            switch (cmd[0]) {
            case "bye":
                try {
                    service.saveTasks(repo);
                } catch (RepoException e) {
                    System.out.println(e.getMessage());
                }
                return;

            case "todo":
                if (checkCommand(cmd, TODO_SYNTAX)) {
                    try {
                        String todo_name = join(cmd, 1, cmd.length);
                        ToDo todo = new ToDo(todo_name);
                        service.addTask(repo, todo);
                        printAddSuccess(todo, repo);
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "deadline":
                if (checkCommand(cmd, DEADLINE_SYNTAX)) {
                    try {
                        int byIndex = indexFinder(cmd, "/by", cmd.length, BY_DEADLINE);

                        String deadline_name = join(cmd, 1, byIndex);
                        String due_date = join(cmd, byIndex + 1, cmd.length);

                        Deadline deadline = new Deadline(deadline_name, due_date);
                        service.addTask(repo, deadline);
                        printAddSuccess(deadline, repo);
                    } catch (BadArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "event":
                if (checkCommand(cmd, EVENT_SYNTAX)) {
                    try {
                        int toIndex = indexFinder(cmd, "/to", cmd.length, END_EVENT);
                        int fromIndex = indexFinder(cmd, "/from", toIndex, START_EVENT);

                        String event_name = join(cmd, 1, fromIndex);
                        String from = join(cmd, fromIndex + 1, toIndex);
                        String to = join(cmd, toIndex + 1, cmd.length);
                        Event event = new Event(event_name, from, to);

                        service.addTask(repo, event);
                        printAddSuccess(event, repo);
                    } catch (BadArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "list":
                List<Task> tasks = service.listTasks(repo);
                if (tasks.isEmpty()) {
                    System.out.println("You currently have no tasks!");
                } else {
                    int count = 1;
                    for (Task task: tasks) {
                        System.out.println(count + "." + task);
                        count += 1;
                    }
                }
                break;

            case "mark":
                if (checkCommand(cmd, MARK_SYNTAX)) {
                    int index = Integer.parseInt(cmd[1]);
                    try {
                        service.completeTask(repo, index);
                        Task task = service.fetchTask(repo, index);
                        System.out.println("I've marked the following task as done:");
                        System.out.println(task);
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "unmark":
                if (checkCommand(cmd, UNMARK_SYNTAX)) {
                    int index = Integer.parseInt(cmd[1]);
                    try {
                        service.uncompleteTask(repo, index);
                        Task task = service.fetchTask(repo, index);
                        System.out.println("I've unmarked the following task as done:");
                        System.out.println(task);
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "delete":
                if (checkCommand(cmd, DELETE_SYNTAX)) {
                    int index = Integer.parseInt(cmd[1]);
                    try {
                        Task task = service.fetchTask(repo, index);
                        service.deleteTask(repo, index);
                        printDeleteSuccess(task, repo);
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case "find":
                if (checkCommand(cmd, FIND_SYNTAX)) {
                    String keyword = join(cmd, 1, cmd.length);
                    List<Task> results = service.findTasks(repo, keyword);
                    if (results.isEmpty()) {
                        System.out.println("No matching tasks found!");
                    } else {
                        System.out.println("Found these matching tasks!");
                        for (Task task:results) {
                            System.out.println(task);
                        }
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
