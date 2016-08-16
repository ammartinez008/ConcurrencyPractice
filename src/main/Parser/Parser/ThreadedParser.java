package main.Parser.Parser;


import main.Parser.Util.TextGrabber;
import main.Parser.Util.ConcurrentWorker;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/****************************************************************
 * Uses Concurrent libraries to parse and analyze the given text
 * ConcurrentHashMap stores the words and the word count
 *****************************************************************/
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

    /****************************************************
     * parses the file retrieved by Textgrabber object
     * uses ExecutorService to store the parsing results from
     * each thread
     *******************************************************/
    public void parse() {
        textGrabber.parseFile();
        lines = textGrabber.getLines();

        for(String line: lines) {
            ConcurrentWorker worker = new ConcurrentWorker(line, wordMap);
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

    // prints word map, mainly used for testing
    public void printMap() {
        for(String key: wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    // prints the key of the map. In this case, the key is a word from the text
    public void printKey(String key) {
        System.out.println(key + ": " + wordMap.get(key));
    }
}
