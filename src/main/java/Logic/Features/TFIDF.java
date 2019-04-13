package Logic.Features;

import Logic.Extraction.ExtractionManager;

import java.util.*;

public class TFIDF {

    public List<String> listMainWords;

    public TFIDF(){
        listMainWords = new ArrayList<>();
    }

    public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }


    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }


    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        double t1 = tf(doc, term);
        double t2 = idf(docs, term);
        return tf(doc, term) * idf(docs, term);

    }

    public void ChooseMainWordsForCountries(ExtractionManager extractionManager, String country, int numberOfWords){
        Map<String, Double> mapCountriesWords = new HashMap<>();
        for(int i = 0; i < extractionManager.getLearningData().size(); ++i){
            if(extractionManager.getLearningData().get(i).place.equals(country)){
                for(int j = 0; j < extractionManager.getLearningData().get(i).words.size(); ++j){
                    if(mapCountriesWords.containsKey(extractionManager.getLearningData().get(i).words.get(j))){
                        mapCountriesWords.put(extractionManager.getLearningData().get(i).words.get(j), mapCountriesWords.get(extractionManager.getLearningData().get(i).words.get(j)) +1);
                    }
                    else{
                        mapCountriesWords.put(extractionManager.getLearningData().get(i).words.get(j), 1.0);
                    }
                }
            }
        }
        List<Double> values = new ArrayList<>(mapCountriesWords.values());
        Collections.sort(values);
        for(int j = 4; j < (4+numberOfWords); ++j){
            for (Map.Entry<String, Double> entry : mapCountriesWords.entrySet()) {
                if(entry.getValue().equals(values.get(values.size()-j))){
                    listMainWords.add(entry.getKey());
                }
            }
        }
    }

}
