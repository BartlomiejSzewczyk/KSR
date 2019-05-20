package Logic.Features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MostCommonKeyword2 implements IFeature {

    private List<List<String>> listOfKeyWord;
    private List<Integer> cntKeyWord;
    public MostCommonKeyword2(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    @Override
    public double count(List<String> listOfWords) {
        cntKeyWord = new ArrayList<>();
        for(int i = 0; i < listOfKeyWord.get(1).size(); ++i){
            cntKeyWord.add(0);
        }
        if(listOfKeyWord.get(1).size() > 0){
            for (String word : listOfWords) {
                for(int i = 0; i < listOfKeyWord.get(1).size() ; ++i){
                    if(word.equals(listOfKeyWord.get(1).get(i))){
                        cntKeyWord.set(i, cntKeyWord.get(i)+1);
                    }
                }
            }
        }
        double maxCnt =0;
        for(int i = 0; i < cntKeyWord.size(); ++i){
            if(cntKeyWord.get(i) > maxCnt){
                maxCnt = cntKeyWord.get(i);
            }
        }
        return maxCnt/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
