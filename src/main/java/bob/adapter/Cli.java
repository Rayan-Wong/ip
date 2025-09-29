package bob.adapter;

import bob.exceptions.BadArgumentException;
import bob.exceptions.RepoException;
import bob.repository.TaskServiceRepo;
import bob.models.Deadline;
import bob.models.Event;
import bob.models.Task;
import bob.models.ToDo;
import bob.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

/**
 * This class is responsible for parsing user commands and then dispatching appropriate service methods
 */
public class Cli {
    public static final String TODO_SYNTAX = "todo <task name>";
    public static final String DEADLINE_SYNTAX = "deadline <deadline name> /by <deadline in yyyy-mm-dd>";
    public static final String EVENT_SYNTAX = "event <event name> /from <from date in yyyy-mm-dd HH:mm> /to <to date in yyyy-mm-dd HH:mm>";
    public static final String MARK_SYNTAX = "mark <index>";
    public static final String UNMARK_SYNTAX = "unmark <index>";
    public static final String DELETE_SYNTAX = "delete <index>";
    public static final String FIND_SYNTAX = "find <str>";
    public static final String FIND_BY_DATE_SYNTAX = "findbydate <date in yyyy-mm-dd>";

    public static final String BY_DEADLINE = "Please specify a deadline with /by <deadline in yyyy-mm-dd>!";
    public static final String START_EVENT = "Please specify a start date with /from <from date in yyyy-mm-dd HH:mm>!";
    public static final String END_EVENT = "Please specify an end date with /to <to date in yyyy-mm-dd HH:mm>!";

    /**
     * Prints a successful task addition
     * @param task the task (or its subclass)
     * @param repo the repo storing it
     */
    public static void printAddSuccess(Task task, TaskServiceRepo repo) {
        int length = repo.getLength();

        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + length + (length == 1 ? " task " : " tasks ") + "in the list.");
    }

    /**
     * Prints a successful task deletion
     * @param task the task (or its subclass)
     * @param repo the repo storing it
     */
    public static void printDeleteSuccess(Task task, TaskServiceRepo repo) {
        int length = repo.getLength();

        System.out.println("Got it. I've deleted this task:");
        System.out.println(task);
        System.out.println("Now you have " + length + (length == 1 ? " task " : " tasks ") + "in the list.");
    }


    /**
     * Checks if there is at least one arg in the command
     * Note that respective services must further check for their own constraints
     * @param cmd the String array which is split by space character
     * @param correctSyntax the correct syntax of the command
     * @return whether the command is valid or not
     */
    public static boolean checkCommand(String[] cmd, String correctSyntax) {
        if (cmd.length <= 1) {
            System.out.println("Invalid syntax!");
            System.out.println("Correct Syntax: " + correctSyntax);
            return false;
        }
        return true;
    }

    /**
     * Checks if the syntax part to be parsed is in the command
     * @param cmd the String array which is split by space character
     * @param target the syntax part being checked
     * @param endIndex the ending index of the substring (exclusive)
     * @param specifiedSyntax the correct syntax of the command
     * @return the index where the command syntax is foumd
     * @throws BadArgumentException if the syntax is invalid
     */
    public static int indexFinder(String[] cmd, String target, int endIndex, String specifiedSyntax) throws BadArgumentException {
        int index = Arrays.asList(cmd).indexOf(target);
        if (index == -1 || endIndex - index == 1) {
            throw new BadArgumentException(specifiedSyntax);
        }
        return index;
    }


    /**
     * Returns a String from joining a split cmd (sub)array
     * @param cmd the command split into an array
     * @param startInclusive the starting index, inclusive
     * @param endExclusive the end index, exclusive
     * @return the command string
     */
    public static String join(String[] cmd, int startInclusive, int endExclusive) {
        return Arrays.stream(cmd, startInclusive, endExclusive).collect(Collectors.joining(" "));
    }

    /**
     * Parses commands, then dispatches appropriate service function
     * @param repo the task repo
     * @param service the task service
     */
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
                        String due_date = cmd[byIndex + 1];

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
                        String from = cmd[fromIndex + 1] + "T" + cmd[fromIndex + 2];
                        String to = cmd[toIndex + 1] + "T" + cmd[toIndex + 2];
                        Event event = new Event(event_name, from, to);

                        service.addTask(repo, event);
                        printAddSuccess(event, repo);
                    } catch (BadArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid syntax! Correct syntax: " + EVENT_SYNTAX);
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

            case "findbydate":
                if (checkCommand(cmd, FIND_BY_DATE_SYNTAX)) {
                    try {
                        LocalDate date = LocalDate.parse(cmd[1]);
                        List<Task> results = service.findTasksWithDate(repo, date);
                        ArrayList<Deadline> deadlines = new ArrayList<>();
                        ArrayList<Event> events = new ArrayList<>();
                        for (Task task: results) {
                            if (task instanceof Deadline) {
                                deadlines.add((Deadline) task);
                            } else if (task instanceof Event) {
                                events.add((Event) task);
                            }
                        }
                        if (deadlines.isEmpty()) {
                            System.out.println("No deadlines for " +
                                    date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " found!");
                        } else {
                            System.out.println("The following tasks were found due " + date);
                            for (Deadline deadline: deadlines) {
                                System.out.println(deadline);
                            }
                        }
                        if (events.isEmpty()) {
                            System.out.println("No events within " +
                                    date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " found!");
                        } else {
                            System.out.println("The following events were found within " + date);
                            for (Event event: events) {
                                System.out.println(event);
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid syntax! Correct syntax: " + FIND_BY_DATE_SYNTAX);
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
