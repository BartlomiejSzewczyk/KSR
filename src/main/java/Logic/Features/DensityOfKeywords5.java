package Logic.Features;

import java.util.List;
import java.util.Map;

public class DensityOfKeywords5 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public DensityOfKeywords5(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        double howManyKeyWords = 0;
        if(listOfKeyWord.get(4).size() > 0){
            for (String word : listOfWords) {
                for(int i = 0; i < listOfKeyWord.get(4).size() ; ++i){
                    if(word.equals(listOfKeyWord.get(4).get(i))){
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
