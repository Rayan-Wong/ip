package bob.models;

public class Task {
    private final String desc;
    private boolean done;
    protected final char DELIM = 0x1F; // for serialisation

    public Task(String desc) {
        done = false;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    // todo: refactor this
    public final String getRawDesc() {
        return this.desc;
    }

    public void setDone(boolean status) {
        done = status;
    }

    public boolean getDone() {
        return done;
    }

    @Override
    public String toString() {
        return "[" + (getDone() ? "X" : " ") + "] " + getDesc();
    }

    public String serialise() {
        return (done ? "D" : "N") + DELIM + desc;
    }
}
