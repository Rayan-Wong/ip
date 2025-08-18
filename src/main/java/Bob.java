public class Bob {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Bob");
        System.out.println("What can I do for you?");
        TaskServiceRepo repo = new TaskServiceRepo();
        TaskService service = new TaskServiceImpl();
        Cli.getCommand(repo, service);
        System.out.println("Bye. Hope to see you again soon!");
    }
}
