package main.Parser.Util;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by alx on 4/30/16.
 */
public class SynchronizedWorker implements Runnable, ThreadWorker {
    private String line;
    private HashMap<String, Integer> wordMap;
    private AtomicBoolean interrupted;

    public SynchronizedWorker(String line, HashMap<String,Integer> wordMap) {
        this.line = line;
        this.wordMap = wordMap;
        this.interrupted = new AtomicBoolean(false);
    }

    public void parse() {
        String[] words = line.split(" ");
        for(String w: words) {
            w = w.toLowerCase();
            updateMap(w);
        }
       // break out of loop
        interrupted.set(true);
    }


    public void updateMap(String key) {
        key = key.replaceAll("[-+.^:,\"\'();]", "");
        key = key.toLowerCase();
        synchronized (wordMap) {
            if (wordMap.containsKey(key)) {
                int currentCount = wordMap.get(key);
                wordMap.put(key, currentCount + 1);
            } else
                wordMap.put(key, 1);
        }
    }

    @Override
    public void run() {
       while(!interrupted.get()) {
            parse();
        }
    }

    public void cancel() {
        System.out.println("got cancel");
        interrupted.set(true);
    }
}
