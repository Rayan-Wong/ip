package bob.models;

/**
 * This class represents an Event, extended from Task
 */
public class Event extends Task {
    String from;
    String to;

    /**
     * Creates an Event object with description, from and end times
     * @param desc the description of the Event
     * @param from the start datetime of the event
     * @param to the end datetime of the event
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    /**
     * @return the event description with the from and end times
     */
    @Override
    public String getDesc() {
        return super.getDesc() + " (from: " + from + " to: " + to + ")";
    }


    /**
     * @return the event's from time
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the event's end time
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }

    @Override
    public String serialise() {
        return "E" + DELIM + super.serialise();
    }
}
