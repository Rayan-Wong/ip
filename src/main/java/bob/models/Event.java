package bob.models;

public class Event extends Task {
    String from;
    String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }
}
