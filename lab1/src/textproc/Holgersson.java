package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
		long t0 = System.nanoTime();
		Scanner scan = new Scanner(new File("lab1/undantagsord.txt"));
		Set<String> exceptions = new HashSet<>();

		while (scan.hasNext()) {
			exceptions.add(scan.next());
		}
		
		ArrayList<TextProcessor> procs = new ArrayList<>();
		procs.add(new SingleWordCounter("nils"));
		procs.add(new SingleWordCounter("norge"));
		procs.add(new MultiWordCounter(REGIONS));
		procs.add(new GeneralWordCounter(exceptions));

		Scanner s = new Scanner(new File("lab1/nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			for (TextProcessor p: procs)
				p.process(word);
		}

		s.close();

		for (TextProcessor p: procs)
			p.report();

		System.out.println("Det tog: " + (System.nanoTime() - t0) / 1000000.0 + "ms för programmet.");
	}
}