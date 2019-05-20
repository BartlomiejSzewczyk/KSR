package Logic.Features;

import java.util.List;
import java.util.Map;

public class Upper4 implements IFeature {

    private List<List<String>> listOfKeyWord;
    public Upper4(List<List<String>> listOfKeyWord){
        this.listOfKeyWord = listOfKeyWord;
    }

    public double count(List<String> listOfWords) {

        double howManyUpperCase = 0;
        for (String word : listOfWords) {
            if (Character.isUpperCase(word.charAt(0))) {
                for(int i = 0; i < listOfKeyWord.get(3).size(); ++i){
                    if(word.equals(listOfKeyWord.get(3))){
                        howManyUpperCase++;
                    }
                }
            }
        }

        return (double)howManyUpperCase/(double)listOfWords.size();
    }

    @Override
    public Map<String, Integer> count(Map<List<String>, String> data) {
        return null;
    }
}
