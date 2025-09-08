package bob.models;

public class Task {
    private final String desc;
    private boolean done;

    public Task(String desc) {
        done = false;
        this.desc = desc;
    }

    public String getDesc() {
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
}
