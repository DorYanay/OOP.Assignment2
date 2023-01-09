package EX2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask<T> implements Callable<T>, Comparable<Task>  {
    private TaskType tasktype;
    private Callable<T> cable;

    public Task(Callable<T> callable) {
        super(callable);
    }


    public TaskType getType() {
    return tasktype;
    }
    public void setType(TaskType tasktype) {
    this.tasktype=tasktype;
    }

    @Override
    public T call() throws Exception {
        return cable.call();
    }

    @Override
    public int compareTo(Task o) {
        return 1;
    }
}
