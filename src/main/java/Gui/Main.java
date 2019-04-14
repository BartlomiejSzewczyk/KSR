package Gui;

import Logic.Extraction.ExtractionManager;
import Logic.Extraction.HighestFrequencyWord;
import Logic.Extraction.TFIDF;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = new ExtractionManager(60);
        System.out.println(extractionManager.getLearningData().size());
        System.out.println(extractionManager.getTestingData().size());

        System.out.println(extractionManager.getLearningData().get(0).words);
        System.out.println(extractionManager.getLearningData().get(0).place);
        TFIDF tfidf = new TFIDF();
        /*extractionManager.createLearningDataWords("uk");
        tfidf.ChooseMainWordsForCountries(extractionManager, "uk", 20);
        extractionManager.createLearningDataWords("france");
        tfidf.ChooseMainWordsForCountries(extractionManager, "france", 20);
        extractionManager.createLearningDataWords("canada");
        tfidf.ChooseMainWordsForCountries(extractionManager, "canada", 20);
        extractionManager.createLearningDataWords("usa");
        tfidf.ChooseMainWordsForCountries(extractionManager, "usa", 20);
        extractionManager.createLearningDataWords("japan");
        tfidf.ChooseMainWordsForCountries(extractionManager, "japan", 20);
        extractionManager.createLearningDataWords("west-germany");
        tfidf.ChooseMainWordsForCountries(extractionManager, "west-germany", 20);*/
        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
        extractionManager.createLearningDataWords("canada");
        highestFrequencyWord.ChooseWords(extractionManager, 20, "canada");
    }
}
