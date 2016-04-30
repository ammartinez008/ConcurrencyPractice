package Parser;

import Util.SynchronizedWorker;
import Util.TextGrabber;
import Util.Worker;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by alx on 4/30/16.
 */
public class SynchronizedParser implements Parser {
    private HashMap<String, Integer> wordMap;
    private List<String> lines;
    private TextGrabber textGrabber;
    private static final int NTHREADS = 8;
    private final ExecutorService executorService
            = Executors.newFixedThreadPool(NTHREADS);

    public SynchronizedParser() {
        this.textGrabber = new TextGrabber();
        this.wordMap = new HashMap<String, Integer>();
    }

    @Override
    public void parse() {
        textGrabber.parseFile();
        lines = textGrabber.getLines();

        for(String line: lines) {
            SynchronizedWorker worker = new SynchronizedWorker(line, wordMap);
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
