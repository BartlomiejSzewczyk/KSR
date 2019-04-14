package Logic.Extraction;

import java.util.*;

public class HighestFrequencyWord {

    public List<String> listMainWords;

    public HighestFrequencyWord(){
        listMainWords = new ArrayList<>();
    }

    public void ChooseWords(ExtractionManager extractionManager, int numberOfWords, String country){
        Map<String, Double> mapCountriesWords = new HashMap<>();
        for(int i = 0; i < extractionManager.getLearningData().size(); ++i){
            if(extractionManager.getLearningData().get(i).place.equals(country)){
                for(int j = 0; j < extractionManager.getLearningData().get(i).stemmedWords.size(); ++j){
                    if(mapCountriesWords.containsKey(extractionManager.getLearningData().get(i).stemmedWords.get(j))){
                        mapCountriesWords.put(extractionManager.getLearningData().get(i).stemmedWords.get(j), mapCountriesWords.get(extractionManager.getLearningData().get(i).stemmedWords.get(j)) +1);
                    }
                    else{
                        mapCountriesWords.put(extractionManager.getLearningData().get(i).stemmedWords.get(j), 1.0);
                    }
                }
            }
        }
        List<Double> values = new ArrayList<>(mapCountriesWords.values());
        Collections.sort(values);
        for(int j = 3; j < (3+numberOfWords); ++j){
            for (Map.Entry<String, Double> entry : mapCountriesWords.entrySet()) {
                if(entry.getValue().equals(values.get(values.size()-j))){
                    if(!listMainWords.contains(entry.getKey())){
                        listMainWords.add(entry.getKey());
                    }
                }
            }
        }
    }
}
