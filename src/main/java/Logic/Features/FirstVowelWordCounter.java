package Logic.Features;

import java.util.List;
import java.util.Map;

public class FirstVowelWordCounter implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double howManyVowelWord = 0;
        for (String word : listOfWords) {
            if (word.charAt(0) == 'a' || word.charAt(0) ==  'e' || word.charAt(0) ==  'i' ||
                    word.charAt(0) ==  'o' || word.charAt(0) ==  'u' || word.charAt(0) == 'y') {
                howManyVowelWord++;
            }
        }
        return howManyVowelWord/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
