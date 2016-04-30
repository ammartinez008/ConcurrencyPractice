package Util;

import java.util.HashMap;

/**
 * Created by alx on 4/30/16.
 */
public class SynchronizedWorker implements Runnable {
    private String line;
    private HashMap<String, Integer> wordMap;

    public SynchronizedWorker(String line, HashMap<String,Integer> wordMap) {
        this.line = line;
        this.wordMap = wordMap;
    }

    public void parse() {
        String[] words = line.split(" ");
        for(String w: words) {
            w = w.toLowerCase();
            updateMap(w);
        }
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
        parse();
    }

}
