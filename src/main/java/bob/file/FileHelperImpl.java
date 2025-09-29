package bob.file;

import bob.exceptions.BadArgumentException;
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

    /**
     * Serialiser format: C_[Desc]_Time1_Time2
     * where C is type of task,
     * Desc is task description,
     * Time1 is first arg of time (eg: /from),
     * Time2 is second arg of time (eg: /to).
     * We use DELIM for delimiter
     * @return the state deserialised
     * @throws IOException if it could not parse (corrupted data.txt etc.)
     */
    private ArrayList<Task> deserialise() throws IOException {
        ArrayList<Task> res = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_DIR.toString()))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] args = line.split(Character.toString(DELIM));
                Task newTask;
                // todo: refactor this, it's very hardcoded
                try {
                    if (args[0].equals("T") && args.length == 3) {
                        newTask = new ToDo(args[2]);
                    } else if (args[0].equals("D") && args.length == 4) {
                        newTask = new Deadline(args[2], args[3]);
                    } else if (args[0].equals("E") && args.length == 5) {
                        newTask = new Event(args[2], args[3], args[4]);
                    } else {
                        throw new IOException("Error parsing, creating new save file!");
                    }
                } catch (BadArgumentException e) {
                    throw new IOException("Error parsing, creating new save file!");
                }
                boolean done = args[1].equals("D");
                newTask.setDone(done);
                res.add(newTask);
            }
        }
        return res;
    }

    /**
     * Loads data from file, if it doesn't exist, create new empty file
     * @return state obtained from file
     * @throws IOException if it had issues parsing from deserialise(), making it create a new file
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> result = new ArrayList<>();
        try {
            createDirectories(FOLDER_DIR);
            if (exists(FILE_DIR)) {
                result = deserialise();
                System.out.println("Found your sava data and loaded it!");
            } else {
                // Rationale for printing it at repo layer: strictly speaking this log should be seen by devs
                // i.e.: in logs, so we print to represent that
                System.out.println("No save data found, creating new file to store it!");
                createFile(FILE_DIR);
                result = new ArrayList<>();
            }
        } catch (IOException e) {
            delete(FILE_DIR);
            createFile(FILE_DIR);
        }
        return result;
    }

    /**
     * Saves state to file
     * @param tasks the current state
     * @throws IOException if it could not parse a task
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_DIR.toString(), false)) {
            for (Task task: tasks) {
                if (task instanceof ToDo) {
                    ToDo todo = (ToDo) task;
                    fw.write(todo.serialise());
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    fw.write(deadline.serialise());
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    fw.write(event.serialise());
                } else {
                    throw new IOException();
                }
                fw.write(System.lineSeparator());
            }
        }
    }
}
