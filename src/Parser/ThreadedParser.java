package Parser;


import Util.TextGrabber;
import Util.Worker;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by alx on 4/24/16.
 */
public class ThreadedParser implements Parser {
    private ConcurrentHashMap<String, Integer> wordMap;
    private List<String> lines;
    private TextGrabber textGrabber;
    private static final int NTHREADS = 4;
    private final ExecutorService executorService
            = Executors.newFixedThreadPool(NTHREADS);

    public ThreadedParser() {
        this.textGrabber = new TextGrabber();
        this.wordMap = new ConcurrentHashMap<String, Integer>();
    }

    public void parse() {
        textGrabber.parseFile();
        lines = textGrabber.getLines();


        for(String line: lines) {
            Worker worker = new Worker(line, wordMap);
            executorService.execute(worker);
        }


        try {
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }
    }


    public void printMap() {
        for(String key: wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    public void printKey(String key) {
        System.out.println(key + ": " + wordMap.get(key));
    }
}
