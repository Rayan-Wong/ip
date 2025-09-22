package bob.models;

/**
 * This class represents a deadline, extended from Task
 */
public class Deadline extends Task {
    String deadline;

    /**
     * Creates a new Deadline object
     * @param desc the task description
     * @param deadline the deadline of this deadline
     */
    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    /**
     * @return the deadline description with the deadline
     */
    @Override
    public String getDesc() {
        return super.getDesc() + " (by: " + deadline + ")";
    }

    /**
     * @return the deadline itself
     */
    public String getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString();
    }

    @Override
    public String serialise() {
        return "D" + DELIM + super.serialise();
    }
}
