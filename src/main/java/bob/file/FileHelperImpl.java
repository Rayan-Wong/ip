package bob.file;

import bob.models.Deadline;
import bob.models.Event;
import bob.models.Task;
import bob.models.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.Files.*;

public class FileHelperImpl implements FileHelper {
    private final Path FOLDER_DIR = Paths.get("data");
    private final Path FILE_DIR = Paths.get("data/Bob.txt");
    private final char DELIM = 0x1F; // for serialisation
    private FileWriter fw;

    // Serialiser format: C_[Desc]_Time1_Time2
    // where C is type of task, Desc is task description
    // Time1 is first arg of time (eg: /from), Time2 is second arg of time (eg: /to)
    // we use DELIM for delimiter

    private ArrayList<Task> deserialise() throws IOException {
        ArrayList<Task> res = new ArrayList<>();
        Scanner sc = new Scanner(new File(FILE_DIR.toString()));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] args = line.split(Character.toString(DELIM));
            Task newTask;
            if (args[0].equals("T") && args.length == 2) {
                newTask = new ToDo(args[1]);
            } else if (args[1].equals("D") && args.length == 3) {
                newTask = new Deadline(args[1], args[2]);
            } else if (args[2].equals("E") && args.length == 4) {
                newTask = new Event(args[1], args[2], args[3]);
            } else {
                throw new IOException("Error parsing!");
            }
            res.add(newTask);
        }
        return res;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> result;
        try {
            createDirectories(FOLDER_DIR);
            if (exists(FILE_DIR)) {
                result = deserialise();
                System.out.print("Found your sava data and loaded it!");
            } else {
                // Rationale for printing it at repo layer: strictly speaking this log should be seen by devs
                // i.e.: in logs, so we print to represent that
                System.out.println("No save data found, creating new file to store it!");
                createFile(FILE_DIR);
                result = new ArrayList<>();
            }
            fw = new FileWriter(FILE_DIR.toString());
            return result;
        } catch (IOException e) {
            throw e;
        }
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        for (Task task: tasks) {
            if (task instanceof ToDo) {
                ToDo todo = (ToDo) task;
                fw.write("T" + DELIM + todo.getRawDesc());
                fw.write(System.lineSeparator());
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                fw.write(("D" + DELIM + deadline.getRawDesc() + DELIM + deadline.getDeadline()));
            } else if (task instanceof Event) {
                Event event = (Event) task;
                fw.write("E" + DELIM + event.getRawDesc() + DELIM + event.getFrom() + event.getTo());
            } else {
                throw new IOException();
            }
        }
        fw.flush();
    }
}
