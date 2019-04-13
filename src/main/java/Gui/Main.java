package Gui;

import Logic.Extraction.ExtractionManager;
import Logic.Features.TFIDF;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = new ExtractionManager(60);
        System.out.println(extractionManager.getLearningData().size());
        System.out.println(extractionManager.getTestingData().size());

        System.out.println(extractionManager.getLearningData().get(0).words);
        System.out.println(extractionManager.getLearningData().get(0).place);
        TFIDF tfidf = new TFIDF();
        double temp = 0;
        for(int i = 0; i < extractionManager.getTestingData().get(3).words.size(); ++i){
            temp = tfidf.tfIdf(extractionManager.getTestingData().get(3).words, extractionManager.getLearningDataWords(), extractionManager.getTestingData().get(3).words.get(i));
        }
        tfidf.ChooseMainWordsForCountries(extractionManager, "usa", 4);
        tfidf.ChooseMainWordsForCountries(extractionManager, "uk", 4);
        tfidf.ChooseMainWordsForCountries(extractionManager, "canada", 4);
        tfidf.ChooseMainWordsForCountries(extractionManager, "japan", 4);
        tfidf.ChooseMainWordsForCountries(extractionManager, "france", 4);
        tfidf.ChooseMainWordsForCountries(extractionManager, "west-germany", 4);
    }
}
