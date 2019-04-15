package Logic.Features;

import java.util.List;
import java.util.Map;

public class LongWordCounter implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double howManyLongWords = 0;
        for (String word : listOfWords) {
            if (word.length() > 7) {
                howManyLongWords++;
            }
        }
        return howManyLongWords/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
