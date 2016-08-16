package main.Parser.Parser;

import main.Parser.Util.TextGrabber;

import java.util.HashMap;
import java.util.List;

/*****************************************************
 * Linear Parser
 * Single threaded parser. uses a hashmap
 * to store are word analysis from the text
 * This class is mainly used to compare are analysis
 * time with multi threaded parsers
 ******************************************************/
public class LinearParser implements Parser {
    private List<String> lines;
    private TextGrabber textGrabber;
    private HashMap<String, Integer> wordMap;

    public LinearParser() {
        this.textGrabber = new TextGrabber();
        this.wordMap = new HashMap<String, Integer>();
    }

    public void parse() {
        textGrabber.parseFile();
        lines = textGrabber.getLines();
        int currentCount = 0;

        for(String line: lines) {
            String[] words = line.split(" ");
            for(String w: words) {
                w = w.replaceAll("[-+.^:,\"\'();]","");
                w = w.toLowerCase();
                if(wordMap.containsKey(w)) {
                    currentCount = wordMap.get(w);
                    wordMap.put(w,currentCount + 1);
                }
                else
                    wordMap.put(w, 1);
            }
        }
    }

    public void printMap() {
        for(String key: wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    public void printKey(String key) {
        System.out.println(wordMap.get(key));
    }
}
