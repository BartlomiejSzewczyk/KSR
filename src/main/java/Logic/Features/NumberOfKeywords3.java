package Logic.Features;

import java.util.List;
import java.util.Map;

public class NumberOfKeywords3 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public NumberOfKeywords3(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        double howManyKeyWords = 0;
        if(listOfKeyWord.get(2).size() > 0){
            for (String word : listOfWords) {
                for(int i = 0; i < listOfKeyWord.get(2).size() ; ++i){
                    if(word.equals(listOfKeyWord.get(2).get(i))){
                        ++howManyKeyWords;
                    }
                }
            }
        }
        return howManyKeyWords/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
