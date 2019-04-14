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
        tfidf.ChooseMainWordsForCountries(extractionManager, "canada", 20);
        tfidf.ChooseMainWordsForCountries(extractionManager, "usa", 20);
        tfidf.ChooseMainWordsForCountries(extractionManager, "uk", 20);
        tfidf.ChooseMainWordsForCountries(extractionManager, "france", 20);
        tfidf.ChooseMainWordsForCountries(extractionManager, "japan", 20);
        tfidf.ChooseMainWordsForCountries(extractionManager, "west-germany", 20);
    }
}
