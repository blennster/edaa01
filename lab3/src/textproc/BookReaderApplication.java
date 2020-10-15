package textproc;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BookReaderApplication {
    public static void main(String[] args) throws FileNotFoundException {
        GeneralWordCounter counter = counterHelper("lab1/nilsholg.txt");
        BookReaderController controller = new BookReaderController(counter);

        controller.addNewFileListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.showDialog(controller.getFrame(), "Select file");
            String path = chooser.getSelectedFile().getAbsolutePath();
            try {
                GeneralWordCounter c = counterHelper(path);
                controller.reload(c);
            } catch (Exception ex) {}
        });
    }

    private static GeneralWordCounter counterHelper(String path) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("lab1/undantagsord.txt"));
        Set<String> exceptions = new HashSet<>();

        while (scan.hasNext()) {
            exceptions.add(scan.next());
        }
        scan.close();

        GeneralWordCounter counter = new GeneralWordCounter(exceptions);

        scan = new Scanner(new File(path));
        scan.findWithinHorizon("\uFEFF", 1);
        scan.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

        while (scan.hasNext()) {
            String word = scan.next().toLowerCase();
            counter.process(word);
        }
        scan.close();
        return counter;
    }
}
