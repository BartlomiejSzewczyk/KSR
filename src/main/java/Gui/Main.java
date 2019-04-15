package Gui;

import Data.DataNode;
import Data.LabelsTypes;
import Data.OwnDataSerializator;
import Data.XmlSerializator;
import Logic.Classificators.FeatureClassificator;
import Logic.Classificators.IClassificator;
import Logic.Classificators.SimilarityClassificator;
import Logic.Extraction.ExtractionManager;
import Logic.Extraction.HighestFrequencyWord;
import Logic.Extraction.TFIDF;
import Logic.Features.*;
import Logic.Metrics.EuclideanMetric;
import Logic.Metrics.IMetric;
import Logic.SimilarityMeasures.ISimilarityMeasure;
import Logic.SimilarityMeasures.NGramMeasure;
import Logic.SimilarityMeasures.NiewiadomskiNGramMeasure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = null;
        try {
            extractionManager = new ExtractionManager(60, new OwnDataSerializator(), "SPORT");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(extractionManager.getLearningData().size());
        System.out.println(extractionManager.getTestingData().size());
//
        System.out.println(extractionManager.getLearningData().get(0).stemmedWords);
        System.out.println(extractionManager.getLearningData().get(0).label);
        TFIDF tfidf = new TFIDF();
        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
        extractionManager.createLearningDataWords("hockey");
        tfidf.ChooseMainWordsForCountries(extractionManager, "hockey", 20);
        extractionManager.createLearningDataWords("basketball");
        tfidf.ChooseMainWordsForCountries(extractionManager, "basketball", 20);
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
        List<IFeature> chosenFeatures = Arrays.asList(
                new FirstVowelWordCounter(),
                new LastVowelWordCounter(),
                new NumberOfKeywords1(extractionManager.getKeyWords()),
                new NumberOfKeywords2(extractionManager.getKeyWords()),
//                 new LongWordCounter(),
//                new MediumWordCounter(),
//                new ShortWordCounter(),
                new TextLengthCounter(),
                new UpperCaseWordCounter()

//                new NiewiadomskiNGramMeasure()
        );

        IMetric metric = new EuclideanMetric();
        IClassificator clas = new FeatureClassificator(extractionManager.getLearningData(), chosenFeatures, 3, metric);

        Map<String, Integer> howManyGood = new HashMap<>();
        for(String s : LabelsTypes.chosen)
        {
            howManyGood.put(s, 0);
        }

        Map<String, Integer> howManyBad = new HashMap<>();
        for(String s : LabelsTypes.chosen)
        {
            howManyBad.put(s, 0);
        }
        int k = 1;
        for(DataNode node : extractionManager.getTestingData())
        {
            String shouldBe = node.label;
            String classified = clas.classify(node);
            if(shouldBe.equals(classified))
            {
                howManyGood.put(shouldBe, howManyGood.get(shouldBe)+1);
            }
            else
            {
                howManyBad.put(shouldBe, howManyBad.get(shouldBe)+1);
            }
            System.out.println(shouldBe + "    " + classified);
        }
        System.out.println("good: ");
        for(Map.Entry e : howManyGood.entrySet())
            System.out.println(e.getKey() + "    " + e.getValue());
        System.out.println("bad: ");
        for(Map.Entry e : howManyBad.entrySet())
            System.out.println(e.getKey() + "    " + e.getValue());

//        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
//        extractionManager.createLearningDataWords("canada");
//        highestFrequencyWord.ChooseWords(extractionManager, 20, "canada");
    }
}