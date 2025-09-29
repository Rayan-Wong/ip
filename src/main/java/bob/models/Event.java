package bob.models;

import bob.exceptions.BadArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class represents an Event, extended from Task
 */
public class Event extends Task {
    LocalDateTime from;
    LocalDateTime to;

    /**
     * Creates an Event object with description, from and end times
     * @param desc the description of the Event
     * @param from the start datetime of the event
     * @param to the end datetime of the event
     */
    public Event(String desc, String from, String to) throws BadArgumentException {
        super(desc);
        try {
            this.from = LocalDateTime.parse(from);
            this.to = LocalDateTime.parse(to);
        } catch (DateTimeParseException e) {
            throw new BadArgumentException("Invalid datetime provided!");
        }
    }

    /**
     * @return the event description with the from and end times
     */
    @Override
    public String getDesc() {
        return super.getDesc() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma")) + ")";
    }


    /**
     * @return the event's from time
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * @return the event's end time
     */
    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }

    @Override
    public String serialise() {
        return "E" + DELIM + super.serialise() + DELIM + from.toString() + DELIM + to.toString();
    }
}
