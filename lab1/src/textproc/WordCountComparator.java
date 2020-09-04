package textproc;

import java.util.Comparator;
import java.util.Map;

public class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        int cmp = o2.getValue() - o1.getValue();
        // Detta sätt sorterar i stigande ordning.
        //int cmp = o1.getValue() - o2.getValue();

        if (cmp == 0)
            return o1.getKey().compareTo(o2.getKey());
        else
            return cmp;
    }
}
