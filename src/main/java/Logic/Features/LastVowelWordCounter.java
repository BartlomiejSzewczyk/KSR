package Logic.Features;

import java.util.List;
import java.util.Map;

public class LastVowelWordCounter implements IFeature {
    @Override
    public double count(List<String> listOfWords) {
        double howManyVowelWord = 0;
        for (String word : listOfWords) {
            if (word.charAt(word.length()-1) == 'a' || word.charAt(word.length()-1) ==  'e' || word.charAt(word.length()-1) ==  'i' ||
                    word.charAt(word.length()-1) ==  'o' || word.charAt(word.length()-1) ==  'u' || word.charAt(word.length()-1) == 'y') {
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
