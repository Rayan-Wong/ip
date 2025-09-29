package bob.models;

import bob.exceptions.BadArgumentException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class represents a deadline, extended from Task
 */
public class Deadline extends Task {
    LocalDate deadline;

    /**
     * Creates a new Deadline object
     * @param desc the task description
     * @param deadline the deadline of this deadline
     */
    public Deadline(String desc, String deadline) throws BadArgumentException {
        super(desc);
        try {
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new BadArgumentException("Invalid date syntax!");
        }
    }

    /**
     * @return the deadline description with the deadline
     */
    @Override
    public String getDesc() {
        return super.getDesc() + " (by: " + deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * @return the deadline itself
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString();
    }

    @Override
    public String serialise() {
        return "D" + DELIM + super.serialise() + DELIM + deadline;
    }
}
