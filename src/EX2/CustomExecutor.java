package EX2;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomExecutor extends ThreadPoolExecutor {
    private PriorityBlockingQueue<Task> Qt;
    private ThreadPoolExecutor exec;
    public CustomExecutor () {


    }
}
