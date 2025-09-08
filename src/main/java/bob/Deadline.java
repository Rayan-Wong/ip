package bob;

public class Deadline extends Task {
    String deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " (by: " + deadline + ")";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
