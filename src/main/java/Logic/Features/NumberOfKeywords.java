package Logic.Features;

import java.util.List;
import java.util.Map;

public class NumberOfKeywords implements IFeature {


    @Override
    public double count(List<String> listOfWords) {
        return 0;
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }

    public double count(List<String> listOfWords, List<String> listofKeyWords){
        double howManyKeyWords = 0;
        for (String word : listOfWords) {
            for(int i = 0; i < listofKeyWords.size(); ++i){
                if(word.equals(listofKeyWords.get(i))){
                    ++howManyKeyWords;
                }
            }
        }
        return howManyKeyWords;
    }
}
