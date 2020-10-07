package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BookReaderApplication {
    public static void main(String[] args) throws FileNotFoundException {
        GeneralWordCounter counter = counterHelper();
        BookReaderController controller = new BookReaderController(counter);

    }

    private static GeneralWordCounter counterHelper() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("lab1/undantagsord.txt"));
        Set<String> exceptions = new HashSet<>();

        while (scan.hasNext()) {
            exceptions.add(scan.next());
        }

        GeneralWordCounter counter = new GeneralWordCounter(exceptions);

        Scanner s = new Scanner(new File("lab1/nilsholg.txt"));
        s.findWithinHorizon("\uFEFF", 1);
        s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

        while (s.hasNext()) {
            String word = s.next().toLowerCase();
            counter.process(word);
        }
        return counter;
    }
}
