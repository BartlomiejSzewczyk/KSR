package Logic.Features;

import java.util.List;
import java.util.Map;

public class MediumWordCounter implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double howManyMediumWords = 0;
        for (String word : listOfWords) {
            if (word.length() > 4 && word.length() <= 7) {
                howManyMediumWords++;
            }
        }
        return howManyMediumWords;
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
