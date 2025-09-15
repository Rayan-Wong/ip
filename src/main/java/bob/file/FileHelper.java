package bob.file;

import bob.models.Task;

import java.io.IOException;
import java.util.ArrayList;

public interface FileHelper {
    ArrayList<Task> load() throws IOException;
    void save(ArrayList<Task> state) throws IOException;
}
