package main.Parser.ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Created by alx on 5/16/16.
 */
public class ParserThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final Logger log = Logger.getLogger("ParserThreadPool");
    private final AtomicLong tasks = new AtomicLong();
    private final AtomicLong timeConsumed = new AtomicLong();

    public ParserThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        tasks.set(0);
        timeConsumed.set(0);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
        log.fine(String.format("Thread %s: start %s", t, r));
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try{
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            tasks.incrementAndGet();
            timeConsumed.addAndGet(taskTime);
            log.fine(String.format("Thread %s: end %s, time=$dns",t,r,taskTime));
        } finally {
            super.afterExecute(r, t);
        }

    }

    @Override
    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time=%dns",
                    timeConsumed.get() / tasks.get()));
        } finally {
            super.terminated();
        }
    }

}
