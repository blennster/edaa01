package textproc;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MultiWordCounter implements TextProcessor {
    private Map<String, Integer> map = new TreeMap<>();

    public MultiWordCounter(String[] words) {
        for (String word: words)
            map.put(word, 0);
    }

    @Override
    public void process(String w) {
        if (map.containsKey(w)) {
            map.replace(w, map.get(w) + 1);
        }
    }

    @Override
    public void report() {
        System.out.println("MultiWordCounter:");
        StringBuilder s = new StringBuilder();
        for (String w: map.keySet()) {
            s.append(String.format("%s: %d", w, map.get(w)));
            s.append("\n");
        }
        System.out.print(s.toString());
    }
}
