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

    public void markDone(boolean status) {
        done = status;
    }

    public boolean getDone() {
        return done;
    }
}
