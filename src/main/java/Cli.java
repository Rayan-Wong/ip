import java.util.Arrays;
import java.util.Scanner;

class Cli {
    public static void getCommand() {
        boolean done = false;
        Scanner sc = new Scanner(System.in);
        // Continuously loop CLI until "bye" command is entered
        while (!done) {
            String cmd = sc.nextLine();
            String[] res = cmd.split(" ");
            switch (res[0]) {
                case "bye":
                    done = true;
                    break;
                default:
                    String text = String.join(" ", res);
                    print(text);
            }
        }
    }

    private static void print(String text) {
        System.out.println(text);
    }
}
