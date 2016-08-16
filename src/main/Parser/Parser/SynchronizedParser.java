package main.Parser.Parser;

import main.Parser.ThreadPool.ParserThreadPool;
import main.Parser.Util.SynchronizedWorker;
import main.Parser.Util.TextGrabber;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/*******************************************************
 * Uses synchronization methods to parse the given text
 * implements the Parser interface
 ********************************************************/
public class SynchronizedParser implements Parser {
    private static final long MAXTIME = 10;

    private HashMap<String, Integer> wordMap;
    private List<String> lines;
    private TextGrabber textGrabber;
    private static final int NTHREADS = Runtime.getRuntime().availableProcessors();
    private ParserThreadPool executorService;
    private BlockingQueue<Runnable> queue;

    private HashMap<SynchronizedWorker, Future<?>> workers;

    public SynchronizedParser() {
        this.textGrabber = new TextGrabber();
        this.wordMap = new HashMap<>();
        this.workers = new HashMap<>();
        this.queue = new LinkedBlockingQueue<>();
        this.executorService = new ParserThreadPool(NTHREADS, NTHREADS + 1, MAXTIME, TimeUnit.SECONDS, queue);
    }


	/**************************************
     * parses a text using threads that are managed
     * by an executor service. In this case, we use futures to store
     * the result of the worker threads
     ***************************************/
    public void parse() {
        textGrabber.parseFile();
        lines = textGrabber.getLines();

        for(String line: lines) {
            SynchronizedWorker worker = new SynchronizedWorker(line, wordMap);
            Future<?> future = executorService.submit(worker);
            workers.put(worker, future);
        }

        try {
            for(SynchronizedWorker worker: workers.keySet()) {
                try {
                    Future<?> future = workers.get(worker);
                    future.get();
                } catch (ExecutionException e) {
                    worker.cancel();
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }
    }


    // prints map of the word count
    public void printMap() {
        for(String key: wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    public long getTaskCount() {
        return executorService.getTaskCount();
    }

    public long getTime() {
        return executorService.getTime();
    }

    //prints key (word), mainly used for parsing
    public void printKey(String key) {
        System.out.println(key + ": " + wordMap.get(key));
    }
}
