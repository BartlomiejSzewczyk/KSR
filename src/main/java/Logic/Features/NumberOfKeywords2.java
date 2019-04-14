package Logic.Features;

import java.util.List;
import java.util.Map;

public class NumberOfKeywords2 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public NumberOfKeywords2(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        double howManyKeyWords = 0;
        if(listOfKeyWord.get(1).size() > 0){
            for (String word : listOfWords) {
                for(int i = 0; i < listOfKeyWord.get(1).size() ; ++i){
                    if(word.equals(listOfKeyWord.get(1).get(i))){
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
