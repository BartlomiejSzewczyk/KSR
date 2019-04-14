package Logic.Features;

import java.util.List;
import java.util.Map;

public class NumberOfKeywords4 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public NumberOfKeywords4(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        double howManyKeyWords = 0;
        if(listOfKeyWord.get(3).size() > 0){
            for (String word : listOfWords) {
                for(int i = 0; i < listOfKeyWord.get(3).size() ; ++i){
                    if(word.equals(listOfKeyWord.get(3).get(i))){
                        ++howManyKeyWords;
                    }
                }
            }
        }
        return howManyKeyWords;
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
