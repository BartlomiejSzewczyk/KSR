package Logic.Features;

import java.util.List;
import java.util.Map;

public class Keywords753 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public Keywords753(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        int cnt = 0;
        double howManyKeyWords = 0;
        if(listOfKeyWord.get(2).size() > 0){
            for (String word : listOfWords) {
                if(cnt > (3*listOfWords.size()/4)){
                    for(int i = 0; i < listOfKeyWord.get(2).size() ; ++i){
                        if(word.equals(listOfKeyWord.get(2).get(i))){
                            ++howManyKeyWords;
                        }
                    }
                }
                ++cnt;
            }
        }
        return howManyKeyWords/((double)listOfWords.size()/4);
    }


    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
