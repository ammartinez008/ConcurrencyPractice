package main.Parser.Util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by alx on 4/24/16.
 */
public class TextGrabber {
    private String[] words;
    private List<String> lines;


    public TextGrabber() {

    }

    public void parseFile() {
        try {
            Path path = Paths.get("/Users/alx/programming/ConcurrencyPractice/", "test_file");
            lines = Files.readAllLines(path, Charset.defaultCharset());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getLines() {
        return lines;
    }


}
