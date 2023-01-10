package EX2;

import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.Callable;

public class CustomExecutor extends ThreadPoolExecutor {
    private static int numOfCores = Runtime.getRuntime().availableProcessors();
    private static PriorityBlockingQueue Qt;
    private int max = 0;
    public CustomExecutor() {
        super(numOfCores/2, numOfCores-1, 300, TimeUnit.MILLISECONDS,
                Qt = new PriorityBlockingQueue<>());
    }
    public <T> Future<T> submit(Task<T> task) {
        if (task == null || task.getCallable() == null) throw new NullPointerException();
        execute(task);
        return task;
    }
    public <T> Future<T> submit(Callable<T> cable) {
        Task<T> task = Task.createTask(cable);
        return submit(task);
    }
    public <T> Future<T> submit(Callable<T> cable , TaskType type) {
        Task<T> task = Task.createTask(cable,type);
        return submit(task);
    }

    public int getCurrentMax() {
        return max;

    }

    public void gracefullyTerminate() {
        super.shutdown();
    }
}
