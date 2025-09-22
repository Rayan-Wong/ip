package bob.models;

/**
 * Base class for all tasks.
 */
public class Task {
    private final String desc;
    private boolean done;
    protected final char DELIM = 0x1F; // for serialisation

    /**
     * Base constructor for child class. All subclasses call this and also set their own specific fields
     * @param desc the description of the task
     */
    public Task(String desc) {
        done = false;
        this.desc = desc;
    }

    /**
     * @return the task's description only
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Set's a task status
     * @param status the status to set the task to
     */
    public void setDone(boolean status) {
        done = status;
    }

    /**
     * @return the status of the task
     */
    public boolean getDone() {
        return done;
    }

    @Override
    public String toString() {
        return "[" + (getDone() ? "X" : " ") + "] " + getDesc();
    }

    /**
     * @return the serialised form of the task to save to file. Child classes extend by adding
     * task type and any other info
     */
    public String serialise() {
        return (done ? "D" : "N") + DELIM + desc;
    }
}
