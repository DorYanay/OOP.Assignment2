package EX2;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.Callable;

public class CustomExecutor extends ThreadPoolExecutor {
    private static int numOfCores = Runtime.getRuntime().availableProcessors();
    private static PriorityBlockingQueue Qt;
    private static int max = 0;
    private static int mid = 0;
    private static int low = 0;

    public CustomExecutor() {
        super(numOfCores / 2, numOfCores - 1, 300, TimeUnit.MILLISECONDS,
                Qt = new PriorityBlockingQueue<>());
    }

    public <T> Future<T> submit(Task<T> task) {
        String priority = task.getType().toString();
        if (priority == "Computational Task") {
            max++;
        } else if (priority == "IO-Bound Task") {
            mid++;
        } else if (priority == "Unknown Task") {
            low++;
        }
        if (task == null || task.getCallable() == null) throw new NullPointerException();
        execute(task);
        return task;
    }

    public <T> Future<T> submit(Callable<T> cable) {
        Task<T> task = Task.createTask(cable);
        return submit(task);
    }

    public <T> Future<T> submit(Callable<T> cable, TaskType type) {
        Task<T> task = Task.createTask(cable, type);
        return submit(task);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        notifyThread();
    }

    public int getCurrentMax() {
        if (max > 0) {
            return 1;
        } else if (mid > 0) {
            return 2;
        } else if(low>0) {
            return 3;
        }
        return 0;
    }

    public void notifyThread() {
        int priority = getCurrentMax();
        if (priority == 1) {
            max--;
        } else if (priority == 2) {
            mid--;
        } else if (priority == 3) {
            low--;
        }
    }

    public void gracefullyTerminate() {
        try {
            super.awaitTermination(1000, TimeUnit.MILLISECONDS);
            super.shutdown();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

    }
}
