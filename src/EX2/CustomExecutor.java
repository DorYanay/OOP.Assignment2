package EX2;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.Callable;

public class CustomExecutor extends ThreadPoolExecutor {
    private static int numOfCores = Runtime.getRuntime().availableProcessors();
    private static PriorityBlockingQueue Qt;
    private int max = 0;
    private Task Executed;

    public CustomExecutor() {
        super(numOfCores / 2, numOfCores - 1, 300, TimeUnit.MILLISECONDS,
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

    public <T> Future<T> submit(Callable<T> cable, TaskType type) {
        Task<T> task = Task.createTask(cable, type);
        return submit(task);
    }

//    @Override
//    protected void afterExecute(Runnable r, Throwable t) {
//        super.afterExecute(r, t);
//        if (t == null) {
//
//        }
//    }

    public int getCurrentMax() {
        return max;

    }

    public void gracefullyTerminate() {
        try {
            super.shutdown();
            super.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

    }
}
