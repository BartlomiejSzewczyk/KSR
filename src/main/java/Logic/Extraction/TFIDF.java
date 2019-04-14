package Logic.Extraction;

import java.util.*;

public class TFIDF{

    public List<String> getListMainWords() {
        return listMainWords;
    }

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
        return tf(doc, term) * idf(docs, term);

    }

    public void ChooseMainWordsForCountries(ExtractionManager extractionManager, String country, int numberOfWords){

        /*Map<String, Double> mapCountriesWords = new HashMap<>();
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
        for(int j = 4; j < (4+numberOfWords); ++j){
            for (Map.Entry<String, Double> entry : mapCountriesWords.entrySet()) {
                if(entry.getValue().equals(values.get(values.size()-j))){
                    listMainWords.add(entry.getKey());
                }
            }
        }*/
        listMainWords.clear();
        Map<String, Double> mapCountriesWords = new HashMap<String, Double>();
        for(int i = 0; i < extractionManager.getLearningData().size(); ++i){
            if(extractionManager.getLearningData().get(i).place.equals(country)){
                for(int j = 0; j < extractionManager.getLearningData().get(i).stemmedWords.size(); ++j){
                    double tempCoefficient = tfIdf(extractionManager.getLearningData().get(i).stemmedWords, extractionManager.getLearningDataWords(), extractionManager.getLearningData().get(i).stemmedWords.get(j));
                    if(mapCountriesWords.containsKey(extractionManager.getLearningData().get(i).stemmedWords.get(j))){
                        if(tempCoefficient < mapCountriesWords.get(extractionManager.getLearningData().get(i).stemmedWords.get(j))){
                            mapCountriesWords.put(extractionManager.getLearningData().get(i).stemmedWords.get(j), tempCoefficient);
                        }
                    }
                    mapCountriesWords.put(extractionManager.getLearningData().get(i).stemmedWords.get(j), tempCoefficient);
                }
            }
        }
        sortMap(mapCountriesWords, listMainWords, numberOfWords);
        extractionManager.CreateKeyWordsList(listMainWords);
    }

    public <String, Double extends Comparable<Double>> Map <String, Double> sortMap(final Map<String, Double> mapCountriesWords, List<String> listMainWords, int numberOfWords)
    {
        Comparator<String> valueComparator = new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                int compare = mapCountriesWords.get(o1).compareTo(mapCountriesWords.get(o2));
                if(compare == 0)
                {
                    return 1;
                }
                else
                {
                    return compare;
                }
            }
        };
        Map<String, Double> sortedByValues = new TreeMap<String, Double>(Collections.reverseOrder(valueComparator));
        sortedByValues.putAll(mapCountriesWords);
        for (Map.Entry<String, Double> entry : sortedByValues.entrySet()) {
            if(mapCountriesWords.size() > numberOfWords){
                mapCountriesWords.remove(entry.getKey());
            }
        }
        for (Map.Entry<String, Double> entry : mapCountriesWords.entrySet()) {
            listMainWords.add(entry.getKey());
        }
        return sortedByValues;
    }
}
