package bob.models;

public class ToDo extends Task {
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

