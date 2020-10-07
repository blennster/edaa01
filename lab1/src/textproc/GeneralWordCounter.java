package textproc;

import java.util.*;

public class GeneralWordCounter implements TextProcessor{
    private Map<String, Integer> words = new HashMap<>();
    private Set<String> exceptions;

    public GeneralWordCounter(Set<String> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public void process(String w) {
        if (!exceptions.contains(w)) {
            if (words.containsKey(w))
                words.replace(w, words.get(w) + 1);
            else
                words.put(w, 1);
        }
    }

    @Override
    public void report() {
        System.out.println("GeneralWordCounter:");
        Set<Map.Entry<String, Integer>> wordSet = words.entrySet();
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
        wordList.sort(new WordCountComparator());

        final int WORDS_TO_PRINT = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < WORDS_TO_PRINT; i++) {
            sb.append(String.format("%s: %d\n", wordList.get(i).getKey(), wordList.get(i).getValue()));
        }
        /*
        for (String w: words.keySet()) {
            int i = words.get(w);
            if (i >= 200) {
                sb.append(String.format("%s: %d\n", w, i));
            }
        }*/
        System.out.print(sb.toString());
    }

    public List<Map.Entry<String, Integer>> getWordList() {
        return new ArrayList<>(words.entrySet());
    }
}
