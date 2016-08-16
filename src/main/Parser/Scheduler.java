package main.Parser;

import main.Parser.Parser.Parser;
import main.Parser.Parser.LinearParser;
import main.Parser.Parser.SynchronizedParser;

/*********************************
 * Main method. Runs the parser.
 ********************************/
public class Scheduler {

   private static Parser parser;

    public static void main(String[] args) {
        runSynchronized();
    }


    public static void runLinear() {
        parser = new LinearParser();
        long start = System.currentTimeMillis();
        parser.parse();
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("Linear method: " + diff);
        parser.printKey("the");
    }

    public static void runSynchronized() {
        long start, end, diff;
        parser = new SynchronizedParser();
        start = System.currentTimeMillis();
        parser.parse();
        end = System.currentTimeMillis();
        diff = end - start;
        System.out.println("threaded method: " + diff);
        parser.printKey("the");
    }

}
