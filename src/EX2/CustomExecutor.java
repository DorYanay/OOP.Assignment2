package EX2;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor extends ThreadPoolExecutor {
    private static int numOfCores = Runtime.getRuntime().availableProcessors();
    private static PriorityBlockingQueue Qt;
    public CustomExecutor() {
        super(numOfCores/2, numOfCores-1, 300, TimeUnit.MILLISECONDS,
                Qt = new PriorityBlockingQueue<>());
    }
}
