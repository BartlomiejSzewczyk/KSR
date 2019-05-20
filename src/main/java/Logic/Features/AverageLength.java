package Logic.Features;

import java.util.List;
import java.util.Map;

public class AverageLength implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double length = 0;
        for (String word : listOfWords) {
            length = length + word.length();
        }
        return length/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
