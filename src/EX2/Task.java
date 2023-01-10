package EX2;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask<T> implements Callable<T>, Comparable<Task<T>>  {
    private TaskType type;
    private Callable<T> cable;
    private Task(Callable<T> cable) {
        this(TaskType.OTHER, cable);
    }
    private Task(TaskType type, Callable<T> cable) {
        super(cable);
        this.cable = cable;
        this.type = type;
    }
    public TaskType getType() {
    return type;
    }
    public void setType(TaskType tasktype) {
    this.type=type;
    }

    public Callable<T> getCallable() {
        return cable;
    }

    public void setCable(Callable<T> cable) {
        this.cable = cable;
    }

    public boolean equals(Task<T> type) {
        if(this.compareTo(type)==0) {
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(type, cable);
    }
    @Override
    public T call() throws Exception {
        return cable.call();
    }
    @Override
    public int compareTo(Task other) {
        if(this.type.getPriorityValue()>other.type.getPriorityValue())
            return 1;
        if(this.type.getPriorityValue()<other.type.getPriorityValue())
            return -1;
        return 0;
    }
    public static <T>Task<T> createTask(Callable<T> cable) {
        return new Task<T>(cable);
    }
    public static <T>Task<T> createTask(Callable<T> cable , TaskType type) {
        return new Task<T>(type,cable);
    }

}
