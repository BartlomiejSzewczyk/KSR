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
import Logic.Metrics.ChebyshevMetric;
import Logic.Metrics.EuclideanMetric;
import Logic.Metrics.IMetric;
import Logic.Metrics.ManhattanMetric;
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
            extractionManager = new ExtractionManager(60, new XmlSerializator(), "PLACES");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        System.out.println(extractionManager.getLearningData().size());
//        System.out.println(extractionManager.getTestingData().size());
////
//        System.out.println(extractionManager.getLearningData().get(0).stemmedWords);
//        System.out.println(extractionManager.getLearningData().get(0).label);

//
//        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "usa");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "uk");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "canada");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "france");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "japan");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "west-germany");


        TFIDF tfidf = new TFIDF();
        extractionManager.createLearningDataWords("usa");
        tfidf.ChooseMainWordsForCountries(extractionManager, "usa", 6);
        extractionManager.createLearningDataWords("uk");
        tfidf.ChooseMainWordsForCountries(extractionManager, "uk", 6);
        extractionManager.createLearningDataWords("canada");
        tfidf.ChooseMainWordsForCountries(extractionManager, "canada", 6);
        extractionManager.createLearningDataWords("france");
        tfidf.ChooseMainWordsForCountries(extractionManager, "france", 6);
        extractionManager.createLearningDataWords("japan");
        tfidf.ChooseMainWordsForCountries(extractionManager, "japan", 6);
        extractionManager.createLearningDataWords("west-germany");
        tfidf.ChooseMainWordsForCountries(extractionManager, "west-germany", 6);


        List<IFeature> chosenFeatures = Arrays.asList(
                new FirstVowelWordCounter(),
                new LastVowelWordCounter(),
                new NumberOfKeywords1(extractionManager.getKeyWords()),
                new NumberOfKeywords2(extractionManager.getKeyWords()),
                new NumberOfKeywords3(extractionManager.getKeyWords()),
                new NumberOfKeywords4(extractionManager.getKeyWords()),
                new NumberOfKeywords5(extractionManager.getKeyWords()),
                new NumberOfKeywords6(extractionManager.getKeyWords()),
                 new LongWordCounter(),
                new MediumWordCounter(),
                new ShortWordCounter(),
                new TextLengthCounter(),
                new UpperCaseWordCounter()

//                new NiewiadomskiNGramMeasure()
        );


        IMetric metric = new EuclideanMetric();
        int kk = 0;
        for(int j = 0; j < 5; ++j){
            if(j == 0){
                kk = 1;
            }
            if(j == 1){
                kk = 3;
            }
            if(j == 2){
                kk = 6;
            }
            if(j == 3){
                kk = 10;
            }
            if(j == 4){
                kk = 20;
            }
            for(int i = 0; i < 3; ++i){
                if(i == 0){
                    metric = new EuclideanMetric();
                }
                else if(i ==1){
                    metric = new ChebyshevMetric();

                }else if(i ==2){
                    metric = new ManhattanMetric();

                }
                IClassificator clas = new FeatureClassificator(extractionManager.getLearningData(), chosenFeatures, kk, metric);

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
//            System.out.println(shouldBe + "    " + classified);
                }
                System.out.println("good: ");
                int a = 0;
                int b = 0;
                for(Map.Entry e : howManyGood.entrySet()){
                    System.out.println(e.getKey() + "    " + e.getValue());
                    a += (int)e.getValue();
                }
                System.out.println("bad: ");
                for(Map.Entry e : howManyBad.entrySet()){
                    System.out.println(e.getKey() + "    " + e.getValue());
                    b += (int)e.getValue();
                }
                System.out.println("Procent: " + (double)a/(double)(a+b));
            }
        }
//        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
//        extractionManager.createLearningDataWords("canada");
//        highestFrequencyWord.ChooseWords(extractionManager, 20, "canada");
    }
}