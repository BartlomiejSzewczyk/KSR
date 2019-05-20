package Logic.Features;

import java.util.List;
import java.util.Map;

public class ShortWordCounter implements IFeature {


    private List<List<String>> listOfKeyWord;
    public ShortWordCounter(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        double howManyShortWords = 0;
        for (String word : listOfWords) {
            if (word.length() <= 3) {
                howManyShortWords++;
            }
        }
        return howManyShortWords/(double)listOfWords.size();

        /*double howManyShortWords = 0;
        for (String word : listOfWords) {
            if (word.length() <= 3) {
                for(int i = 0; i < listOfKeyWord.get(0).size(); ++i){
                    if(word.equals(listOfKeyWord.get(0).get(i))){
                        howManyShortWords++;
                    }
                }
            }
        }
        return howManyShortWords/(double)listOfWords.size();*/
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
