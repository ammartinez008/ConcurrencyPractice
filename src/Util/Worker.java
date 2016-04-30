package Util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alx on 4/29/16.
 */
public class Worker implements Runnable{
    private String line;
    private ConcurrentHashMap<String, Integer> wordMap;

    public Worker(String line, ConcurrentHashMap<String,Integer> wordMap) {
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
        key = key.replaceAll("[-+.^:,\"\'();]","");
        key = key.toLowerCase();
        if(wordMap.containsKey(key)) {
            int currentCount = wordMap.get(key);
            wordMap.put(key,currentCount + 1);
        }
        else
            wordMap.put(key, 1);
    }

    @Override
    public void run() {
        parse();
    }
}
