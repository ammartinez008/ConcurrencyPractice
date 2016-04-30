import Parser.Parser;
import Parser.LinearParser;
import Parser.ThreadedParser;
import Parser.SynchronizedParser;

/**
 * Created by alx on 4/24/16.
 */
public class Controller {

   private static Parser parser;

    public static void main(String[] args) {
        parser = new LinearParser();
        long start = System.currentTimeMillis();
        parser.parse();
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("Linear method: " + diff);
        parser.printKey("the");

        parser = new SynchronizedParser();
        start = System.currentTimeMillis();
        parser.parse();
        end = System.currentTimeMillis();
        diff = end - start;
        System.out.println("threaded method: " + diff);
        parser.printKey("the");



    }


}
