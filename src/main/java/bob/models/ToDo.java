package bob.models;

/**
 * This class represents a ToDo, extended from Task
 */
public class ToDo extends Task {
    /**
     * Creates a ToDo class
     * @param desc the ToDo description
     */
    public ToDo(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String serialise() {
        return "T" + DELIM + super.serialise();
    }
}

