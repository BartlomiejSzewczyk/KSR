package Logic.Features;

import java.util.List;
import java.util.Map;

public class ShortWordCounter implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double howManyShortWords = 0;
        for (String word : listOfWords) {
            if (word.length() <= 4) {
                howManyShortWords++;
            }
        }
        return howManyShortWords;
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
