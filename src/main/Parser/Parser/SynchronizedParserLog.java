package main.Parser.Parser;

import main.Parser.Util.SynchronizedWorker;
import main.Parser.Util.TextGrabber;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by alx on 4/30/16.
 */
public class SynchronizedParserLog implements Parser {
    private HashMap<String, Integer> wordMap;
    private List<String> lines;
    private TextGrabber textGrabber;
    private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
    private ExecutorService executorService
            = Executors.newFixedThreadPool(NTHREADS);
    private HashMap<SynchronizedWorker, Future<?>> workers;

    public SynchronizedParserLog() {
        this.textGrabber = new TextGrabber();
        this.wordMap = new HashMap<>();
        this.workers = new HashMap<>();
    }



    @Override
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

    @Override
    public void printMap() {
        for(String key: wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    @Override
    public void printKey(String key) {
        System.out.println(key + ": " + wordMap.get(key));
    }
}
